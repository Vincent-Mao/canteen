package com.fast.canteen.mapper;

import java.util.List;

import com.fast.canteen.domain.vo.CategoryVO;
import org.apache.ibatis.annotations.Mapper;
import com.fast.canteen.domain.Category;

/**
 * 菜品分类Mapper接口
 *
 * @author fast
 * @date 2025-11-19
 */
@Mapper
public interface CategoryMapper
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
     * 修改菜品分类
     *
     * @param category 菜品分类
     * @return 结果
     */
    public int updateCategory(Category category);

    /**
     * 删除菜品分类
     *
     * @param categoryId 菜品分类主键
     * @return 结果
     */
    public int deleteCategoryByCategoryId(String categoryId);

    /**
     * 批量删除菜品分类
     *
     * @param categoryIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCategoryByCategoryIds(String[] categoryIds);

    /**
     * 查询所有分类
     * @return
     */
    List<CategoryVO> selectAllCategory();
}
