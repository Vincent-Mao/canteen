package com.fast.canteen.controller;

import java.util.List;

import com.fast.system.general.utils.SecurityUtils;
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
import com.fast.canteen.domain.Collect;
import com.fast.canteen.service.ICollectService;
import com.fast.system.general.utils.poi.ExcelUtil;
import com.fast.system.general.core.page.TableDataInfo;

/**
 * 收藏Controller
 *
 * @author fast
 * @date 2025-11-20
 */
@RestController
@RequestMapping("/canteen/collect")
public class CollectController extends BaseController {
    @Autowired
    private ICollectService collectService;

    /**
     * 查询收藏列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Collect collect) {
        startPage();
        List<Collect> list = collectService.selectCollectList(collect);
        return getDataTable(list);
    }

    /**
     * 导出收藏列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, Collect collect) {
        List<Collect> list = collectService.selectCollectList(collect);
        ExcelUtil<Collect> util = new ExcelUtil<Collect>(Collect. class);
        util.exportExcel(response, list, "收藏数据");
    }

    /**
     * 下载模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<Collect> util = new ExcelUtil<Collect>(Collect. class);
        util.importTemplateExcel(response, "收藏数据");
    }

    /**
     * 导入数据
     */
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<Collect> util = new ExcelUtil<Collect>(Collect. class);
        InputStream inputStream = file.getInputStream();
        List<Collect> list = util.importExcel(inputStream);
        inputStream.close();
        int count = collectService.batchInsertCollect(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }

    /**
     * 获取收藏详细信息
     */
    @GetMapping(value = "/{collectId}")
    public AjaxResult getInfo(@PathVariable("collectId") String collectId) {
        return success(collectService.selectCollectByCollectId(collectId));
    }

    /**
     * 新增收藏
     */
    @PostMapping
    public AjaxResult add(@RequestBody Collect collect) {
        return toAjax(collectService.insertCollect(collect));
    }

    /**
     * 修改收藏
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Collect collect) {
        return toAjax(collectService.updateCollect(collect));
    }

    /**
     * 删除收藏
     */
    @DeleteMapping("/{collectIds}")
    public AjaxResult remove(@PathVariable String[] collectIds) {
        return toAjax(collectService.deleteCollectByCollectIds(collectIds));
    }

    /**
     * 查询当前用户的菜品收藏列表
     */
    @GetMapping("/selectMyCollectDishes")
    public AjaxResult selectMyCollectDishes() {
        //获取当前用户的ID
        Long userId = SecurityUtils.getUserId();
        Collect collect = new Collect();
        collect.setUserId(userId);
        List<Collect> list = collectService.selectCollectList(collect);
        return success(list);
    }

    /**
     * 根据菜品ID和用户ID查询收藏ID, 并且删除该收藏信息
     */
    @GetMapping("/selectCollectIdByDishesIdAndUserId/{dishesId}")
    public AjaxResult selectCollectIdByDishesIdAndUserId(@PathVariable String dishesId) {
        //获取当前用户的ID
        Long userId = SecurityUtils.getUserId();
        String collectId = collectService.selectCollectIdByDishesIdAndUserId(dishesId, userId);

        //删除该收藏
        return toAjax(collectService.deleteCollectByCollectId(collectId));
    }

}
