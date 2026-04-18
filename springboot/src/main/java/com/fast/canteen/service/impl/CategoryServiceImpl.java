package com.fast.canteen.service.impl;

import java.util.List;

import com.fast.canteen.domain.vo.CategoryVO;
import com.fast.system.general.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fast.canteen.mapper.CategoryMapper;
import com.fast.canteen.domain.Category;
import com.fast.canteen.service.ICategoryService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

/**
 * 菜品分类Service业务层处理
 *
 * @author fast
 * @date 2025-11-19
 */
@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询菜品分类
     *
     * @param categoryId 菜品分类主键
     * @return 菜品分类
     */
    @Override
    public Category selectCategoryByCategoryId(String categoryId) {
        return categoryMapper.selectCategoryByCategoryId(categoryId);
    }

    /**
     * 查询菜品分类列表
     *
     * @param category 菜品分类
     * @return 菜品分类
     */
    @Override
    public List<Category> selectCategoryList(Category category) {
        return categoryMapper.selectCategoryList(category);
    }

    /**
     * 新增菜品分类
     *
     * @param category 菜品分类
     * @return 结果
     */
    @Override
    public int insertCategory(Category category) {
        //生成一个UUID并插入至菜品分类对象中
        category.setCategoryId(IdUtils.fastSimpleUUID());
        return categoryMapper.insertCategory(category);
    }

    /**
     * 批量新增菜品分类
     *
     * @param categorys 菜品分类List
     * @return 结果
     */
    @Override
    public int batchInsertCategory(List<Category> categorys) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(categorys)) {
            try {
                for (int i = 0; i < categorys.size(); i++) {
                    int row = categoryMapper.insertCategory(categorys.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i > 0 && i % 100 == 0) || i == categorys.size() - 1;
                    if (bool) {
                        sqlSession.commit();
                        sqlSession.clearCache();
                    }
                    count = i + 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
                // 没有提交的数据可以回滚
                sqlSession.rollback();
            } finally {
                sqlSession.close();
                return count;
            }
        }
        return count;
    }

    /**
     * 修改菜品分类
     *
     * @param category 菜品分类
     * @return 结果
     */
    @Override
    public int updateCategory(Category category) {
        return categoryMapper.updateCategory(category);
    }

    /**
     * 批量删除菜品分类
     *
     * @param categoryIds 需要删除的菜品分类主键
     * @return 结果
     */
    @Override
    public int deleteCategoryByCategoryIds(String[] categoryIds) {
        return categoryMapper.deleteCategoryByCategoryIds(categoryIds);
    }

    /**
     * 删除菜品分类信息
     *
     * @param categoryId 菜品分类主键
     * @return 结果
     */
    @Override
    public int deleteCategoryByCategoryId(String categoryId) {
        return categoryMapper.deleteCategoryByCategoryId(categoryId);
    }

    /**
     * 查询所有分类
     * @return
     */
    @Override
    public List<CategoryVO> selectAllCategory() {
        return categoryMapper.selectAllCategory();
    }
}
