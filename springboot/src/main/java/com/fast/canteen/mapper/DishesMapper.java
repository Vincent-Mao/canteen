package com.fast.canteen.mapper;

import java.util.List;

import com.fast.canteen.domain.vo.DishesVO;
import org.apache.ibatis.annotations.Mapper;
import com.fast.canteen.domain.Dishes;

/**
 * 菜品Mapper接口
 *
 * @author fast
 * @date 2025-11-19
 */
@Mapper
public interface DishesMapper
{
    /**
     * 查询菜品
     *
     * @param dishesId 菜品主键
     * @return 菜品
     */
    public Dishes selectDishesByDishesId(String dishesId);

    /**
     * 查询菜品列表
     *
     * @param dishes 菜品
     * @return 菜品集合
     */
    public List<Dishes> selectDishesList(Dishes dishes);

    /**
     * 新增菜品
     *
     * @param dishes 菜品
     * @return 结果
     */
    public int insertDishes(Dishes dishes);

    /**
     * 修改菜品
     *
     * @param dishes 菜品
     * @return 结果
     */
    public int updateDishes(Dishes dishes);

    /**
     * 删除菜品
     *
     * @param dishesId 菜品主键
     * @return 结果
     */
    public int deleteDishesByDishesId(String dishesId);

    /**
     * 批量删除菜品
     *
     * @param dishesIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDishesByDishesIds(String[] dishesIds);

    /**
     * 查询所有菜品
     * @return
     */
    List<DishesVO> selectAllDishes();
}
