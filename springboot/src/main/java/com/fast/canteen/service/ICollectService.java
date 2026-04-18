package com.fast.canteen.service;

import java.util.List;
import com.fast.canteen.domain.Collect;

/**
 * 收藏Service接口
 *
 * @author fast
 * @date 2025-11-20
 */
public interface ICollectService
{
    /**
     * 查询收藏
     *
     * @param collectId 收藏主键
     * @return 收藏
     */
    public Collect selectCollectByCollectId(String collectId);

    /**
     * 查询收藏列表
     *
     * @param collect 收藏
     * @return 收藏集合
     */
    public List<Collect> selectCollectList(Collect collect);

    /**
     * 新增收藏
     *
     * @param collect 收藏
     * @return 结果
     */
    public int insertCollect(Collect collect);

    /**
     * 批量新增收藏
     *
     * @param collects 收藏List
     * @return 结果
     */
    public int batchInsertCollect(List<Collect> collects);

    /**
     * 修改收藏
     *
     * @param collect 收藏
     * @return 结果
     */
    public int updateCollect(Collect collect);

    /**
     * 批量删除收藏
     *
     * @param collectIds 需要删除的收藏主键集合
     * @return 结果
     */
    public int deleteCollectByCollectIds(String[] collectIds);

    /**
     * 删除收藏信息
     *
     * @param collectId 收藏主键
     * @return 结果
     */
    public int deleteCollectByCollectId(String collectId);

    /**
     * 根据菜品ID和用户ID查询收藏ID
     * @param dishesId
     * @param userId
     * @return
     */
    String selectCollectIdByDishesIdAndUserId(String dishesId, Long userId);
}
