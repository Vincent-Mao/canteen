package com.fast.canteen.service.impl;

import java.util.List;

import com.fast.canteen.domain.vo.DishesVO;
import com.fast.system.general.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fast.canteen.mapper.DishesMapper;
import com.fast.canteen.domain.Dishes;
import com.fast.canteen.service.IDishesService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

/**
 * 菜品Service业务层处理
 *
 * @author fast
 * @date 2025-11-19
 */
@Service
public class DishesServiceImpl implements IDishesService {
    @Autowired
    private DishesMapper dishesMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询菜品
     *
     * @param dishesId 菜品主键
     * @return 菜品
     */
    @Override
    public Dishes selectDishesByDishesId(String dishesId) {
        return dishesMapper.selectDishesByDishesId(dishesId);
    }

    /**
     * 查询菜品列表
     *
     * @param dishes 菜品
     * @return 菜品
     */
    @Override
    public List<Dishes> selectDishesList(Dishes dishes) {
        return dishesMapper.selectDishesList(dishes);
    }

    /**
     * 新增菜品
     *
     * @param dishes 菜品
     * @return 结果
     */
    @Override
    public int insertDishes(Dishes dishes) {
        //生成一个UUID并插入至菜品对象中
        dishes.setDishesId(IdUtils.fastSimpleUUID());
        return dishesMapper.insertDishes(dishes);
    }

    /**
     * 批量新增菜品
     *
     * @param dishess 菜品List
     * @return 结果
     */
    @Override
    public int batchInsertDishes(List<Dishes> dishess) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(dishess)) {
            try {
                for (int i = 0; i < dishess.size(); i++) {
                    int row = dishesMapper.insertDishes(dishess.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i > 0 && i % 100 == 0) || i == dishess.size() - 1;
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
     * 修改菜品
     *
     * @param dishes 菜品
     * @return 结果
     */
    @Override
    public int updateDishes(Dishes dishes) {
        return dishesMapper.updateDishes(dishes);
    }

    /**
     * 批量删除菜品
     *
     * @param dishesIds 需要删除的菜品主键
     * @return 结果
     */
    @Override
    public int deleteDishesByDishesIds(String[] dishesIds) {
        return dishesMapper.deleteDishesByDishesIds(dishesIds);
    }

    /**
     * 删除菜品信息
     *
     * @param dishesId 菜品主键
     * @return 结果
     */
    @Override
    public int deleteDishesByDishesId(String dishesId) {
        return dishesMapper.deleteDishesByDishesId(dishesId);
    }

    /**
     * 查询所有菜品
     * @return
     */
    @Override
    public List<DishesVO> selectAllDishes() {
        return dishesMapper.selectAllDishes();
    }
}
