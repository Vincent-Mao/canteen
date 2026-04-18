package com.fast.canteen.service;

import java.util.List;
import com.fast.canteen.domain.Category;
import com.fast.canteen.domain.vo.CategoryVO;

/**
 * 菜品分类Service接口
 *
 * @author fast
 * @date 2025-11-19
 */
public interface ICategoryService
{
    /**
     * 查询菜品分类
     *
     * @param categoryId 菜品分类主键
     * @return 菜品分类
     */
    public Category selectCategoryByCategoryId(String categoryId);

    /**
     * 查询菜品分类列表
     *
     * @param category 菜品分类
     * @return 菜品分类集合
     */
    public List<Category> selectCategoryList(Category category);

    /**
     * 新增菜品分类
     *
     * @param category 菜品分类
     * @return 结果
     */
    public int insertCategory(Category category);

    /**
     * 批量新增菜品分类
     *
     * @param categorys 菜品分类List
     * @return 结果
     */
    public int batchInsertCategory(List<Category> categorys);

    /**
     * 修改菜品分类
     *
     * @param category 菜品分类
     * @return 结果
     */
    public int updateCategory(Category category);

    /**
     * 批量删除菜品分类
     *
     * @param categoryIds 需要删除的菜品分类主键集合
     * @return 结果
     */
    public int deleteCategoryByCategoryIds(String[] categoryIds);

    /**
     * 删除菜品分类信息
     *
     * @param categoryId 菜品分类主键
     * @return 结果
     */
    public int deleteCategoryByCategoryId(String categoryId);

    /**
     * 查询所有分类
     * @return
     */
    List<CategoryVO> selectAllCategory();
}
