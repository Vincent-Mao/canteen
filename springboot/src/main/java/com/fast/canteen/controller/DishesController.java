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
import com.fast.canteen.domain.Dishes;
import com.fast.canteen.service.IDishesService;
import com.fast.system.general.utils.poi.ExcelUtil;
import com.fast.system.general.core.page.TableDataInfo;

/**
 * 菜品Controller
 *
 * @author fast
 * @date 2025-11-19
 */
@RestController
@RequestMapping("/canteen/dishes")
public class DishesController extends BaseController {
    @Autowired
    private IDishesService dishesService;

    /**
     * 查询菜品列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Dishes dishes) {
        startPage();
        List<Dishes> list = dishesService.selectDishesList(dishes);
        return getDataTable(list);
    }

    /**
     * 导出菜品列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, Dishes dishes) {
        List<Dishes> list = dishesService.selectDishesList(dishes);
        ExcelUtil<Dishes> util = new ExcelUtil<Dishes>(Dishes. class);
        util.exportExcel(response, list, "菜品数据");
    }

    /**
     * 下载模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<Dishes> util = new ExcelUtil<Dishes>(Dishes. class);
        util.importTemplateExcel(response, "菜品数据");
    }

    /**
     * 导入数据
     */
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<Dishes> util = new ExcelUtil<Dishes>(Dishes. class);
        InputStream inputStream = file.getInputStream();
        List<Dishes> list = util.importExcel(inputStream);
        inputStream.close();
        int count = dishesService.batchInsertDishes(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }

    /**
     * 获取菜品详细信息
     */
    @GetMapping(value = "/{dishesId}")
    public AjaxResult getInfo(@PathVariable("dishesId") String dishesId) {
        return success(dishesService.selectDishesByDishesId(dishesId));
    }

    /**
     * 新增菜品
     */
    @PostMapping
    public AjaxResult add(@RequestBody Dishes dishes) {
        return toAjax(dishesService.insertDishes(dishes));
    }

    /**
     * 修改菜品
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Dishes dishes) {
        return toAjax(dishesService.updateDishes(dishes));
    }

    /**
     * 删除菜品
     */
    @DeleteMapping("/{dishesIds}")
    public AjaxResult remove(@PathVariable String[] dishesIds) {
        return toAjax(dishesService.deleteDishesByDishesIds(dishesIds));
    }

    /**
     * 查询所有菜品
     */
    @GetMapping("/selectAllDishes")
    public AjaxResult selectAllDishes() {
        return success(dishesService.selectAllDishes());
    }

}
