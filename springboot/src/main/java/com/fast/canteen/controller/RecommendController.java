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
import com.fast.canteen.domain.Recommend;
import com.fast.canteen.service.IRecommendService;
import com.fast.system.general.utils.poi.ExcelUtil;
import com.fast.system.general.core.page.TableDataInfo;

/**
 * 营养推荐Controller
 *
 * @author fast
 * @date 2025-11-23
 */
@RestController
@RequestMapping("/canteen/recommend")
public class RecommendController extends BaseController {
    @Autowired
    private IRecommendService recommendService;

    /**
     * 查询营养推荐列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Recommend recommend) {
        startPage();
        List<Recommend> list = recommendService.selectRecommendList(recommend);
        return getDataTable(list);
    }

    /**
     * 导出营养推荐列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, Recommend recommend) {
        List<Recommend> list = recommendService.selectRecommendList(recommend);
        ExcelUtil<Recommend> util = new ExcelUtil<Recommend>(Recommend. class);
        util.exportExcel(response, list, "营养推荐数据");
    }

    /**
     * 下载模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<Recommend> util = new ExcelUtil<Recommend>(Recommend. class);
        util.importTemplateExcel(response, "营养推荐数据");
    }

    /**
     * 导入数据
     */
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<Recommend> util = new ExcelUtil<Recommend>(Recommend. class);
        InputStream inputStream = file.getInputStream();
        List<Recommend> list = util.importExcel(inputStream);
        inputStream.close();
        int count = recommendService.batchInsertRecommend(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }

    /**
     * 获取营养推荐详细信息
     */
    @GetMapping(value = "/{recommendId}")
    public AjaxResult getInfo(@PathVariable("recommendId") String recommendId) {
        return success(recommendService.selectRecommendByRecommendId(recommendId));
    }

    /**
     * 新增营养推荐
     */
    @PostMapping
    public AjaxResult add(@RequestBody Recommend recommend) {
        return toAjax(recommendService.insertRecommend(recommend));
    }

    /**
     * 修改营养推荐
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Recommend recommend) {
        return toAjax(recommendService.updateRecommend(recommend));
    }

    /**
     * 删除营养推荐
     */
    @DeleteMapping("/{recommendIds}")
    public AjaxResult remove(@PathVariable String[] recommendIds) {
        return toAjax(recommendService.deleteRecommendByRecommendIds(recommendIds));
    }
}
