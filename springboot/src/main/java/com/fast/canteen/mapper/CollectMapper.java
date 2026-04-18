package com.fast.canteen.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fast.canteen.domain.Collect;

/**
 * 收藏Mapper接口
 *
 * @author fast
 * @date 2025-11-20
 */
@Mapper
public interface CollectMapper
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
     * 修改收藏
     *
     * @param collect 收藏
     * @return 结果
     */
    public int updateCollect(Collect collect);

    /**
     * 删除收藏
     *
     * @param collectId 收藏主键
     * @return 结果
     */
    public int deleteCollectByCollectId(String collectId);

    /**
     * 批量删除收藏
     *
     * @param collectIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCollectByCollectIds(String[] collectIds);

    /**
     * 根据菜品ID和用户ID查询收藏ID
     * @param dishesId
     * @param userId
     * @return
     */
    String selectCollectIdByDishesIdAndUserId(String dishesId, Long userId);
}
