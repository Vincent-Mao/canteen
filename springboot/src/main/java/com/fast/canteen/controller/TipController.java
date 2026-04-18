package com.fast.canteen.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fast.system.general.core.controller.BaseController;
import com.fast.system.general.core.domain.AjaxResult;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;
import com.fast.canteen.domain.Tip;
import com.fast.canteen.service.ITipService;
import com.fast.system.general.utils.poi.ExcelUtil;
import com.fast.system.general.core.page.TableDataInfo;

/**
 * 健康小贴士Controller
 *
 * @author fast
 * @date 2025-11-23
 */
@RestController
@RequestMapping("/canteen/tip")
public class TipController extends BaseController {
    @Autowired
    private ITipService tipService;

    /**
     * 查询健康小贴士列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Tip tip) {
        startPage();
        List<Tip> list = tipService.selectTipList(tip);
        return getDataTable(list);
    }

    /**
     * 导出健康小贴士列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, Tip tip) {
        List<Tip> list = tipService.selectTipList(tip);
        ExcelUtil<Tip> util = new ExcelUtil<Tip>(Tip. class);
        util.exportExcel(response, list, "健康小贴士数据");
    }

    /**
     * 下载模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<Tip> util = new ExcelUtil<Tip>(Tip. class);
        util.importTemplateExcel(response, "健康小贴士数据");
    }

    /**
     * 导入数据
     */
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<Tip> util = new ExcelUtil<Tip>(Tip. class);
        InputStream inputStream = file.getInputStream();
        List<Tip> list = util.importExcel(inputStream);
        inputStream.close();
        int count = tipService.batchInsertTip(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }

    /**
     * 获取健康小贴士详细信息
     */
    @GetMapping(value = "/{tipId}")
    public AjaxResult getInfo(@PathVariable("tipId") String tipId) {
        return success(tipService.selectTipByTipId(tipId));
    }

    /**
     * 新增健康小贴士
     */
    @PostMapping
    public AjaxResult add(@RequestBody Tip tip) {
        return toAjax(tipService.insertTip(tip));
    }

    /**
     * 修改健康小贴士
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Tip tip) {
        return toAjax(tipService.updateTip(tip));
    }

    /**
     * 删除健康小贴士
     */
    @DeleteMapping("/{tipIds}")
    public AjaxResult remove(@PathVariable String[] tipIds) {
        return toAjax(tipService.deleteTipByTipIds(tipIds));
    }
}
