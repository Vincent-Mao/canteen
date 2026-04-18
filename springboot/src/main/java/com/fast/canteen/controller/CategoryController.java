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
import com.fast.canteen.domain.Category;
import com.fast.canteen.service.ICategoryService;
import com.fast.system.general.utils.poi.ExcelUtil;
import com.fast.system.general.core.page.TableDataInfo;

/**
 * 菜品分类Controller
 *
 * @author fast
 * @date 2025-11-19
 */
@RestController
@RequestMapping("/canteen/category")
public class CategoryController extends BaseController {
    @Autowired
    private ICategoryService categoryService;

    /**
     * 查询菜品分类列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Category category) {
        startPage();
        List<Category> list = categoryService.selectCategoryList(category);
        return getDataTable(list);
    }

    /**
     * 导出菜品分类列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, Category category) {
        List<Category> list = categoryService.selectCategoryList(category);
        ExcelUtil<Category> util = new ExcelUtil<Category>(Category. class);
        util.exportExcel(response, list, "菜品分类数据");
    }

    /**
     * 下载模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<Category> util = new ExcelUtil<Category>(Category. class);
        util.importTemplateExcel(response, "菜品分类数据");
    }

    /**
     * 导入数据
     */
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<Category> util = new ExcelUtil<Category>(Category. class);
        InputStream inputStream = file.getInputStream();
        List<Category> list = util.importExcel(inputStream);
        inputStream.close();
        int count = categoryService.batchInsertCategory(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }

    /**
     * 获取菜品分类详细信息
     */
    @GetMapping(value = "/{categoryId}")
    public AjaxResult getInfo(@PathVariable("categoryId") String categoryId) {
        return success(categoryService.selectCategoryByCategoryId(categoryId));
    }

    /**
     * 新增菜品分类
     */
    @PostMapping
    public AjaxResult add(@RequestBody Category category) {
        return toAjax(categoryService.insertCategory(category));
    }

    /**
     * 修改菜品分类
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Category category) {
        return toAjax(categoryService.updateCategory(category));
    }

    /**
     * 删除菜品分类
     */
    @DeleteMapping("/{categoryIds}")
    public AjaxResult remove(@PathVariable String[] categoryIds) {
        return toAjax(categoryService.deleteCategoryByCategoryIds(categoryIds));
    }

    /**
     * 查询所有分类
     */
    @GetMapping("/selectAllCategory")
    public AjaxResult selectAllCategory() {
        return success(categoryService.selectAllCategory());
    }

}
