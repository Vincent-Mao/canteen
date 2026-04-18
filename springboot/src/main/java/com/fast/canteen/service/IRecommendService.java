package com.fast.canteen.service;

import java.util.List;
import com.fast.canteen.domain.Recommend;

/**
 * 营养推荐Service接口
 *
 * @author fast
 * @date 2025-11-23
 */
public interface IRecommendService
{
    /**
     * 查询营养推荐
     *
     * @param recommendId 营养推荐主键
     * @return 营养推荐
     */
    public Recommend selectRecommendByRecommendId(String recommendId);

    /**
     * 查询营养推荐列表
     *
     * @param recommend 营养推荐
     * @return 营养推荐集合
     */
    public List<Recommend> selectRecommendList(Recommend recommend);

    /**
     * 新增营养推荐
     *
     * @param recommend 营养推荐
     * @return 结果
     */
    public int insertRecommend(Recommend recommend);

    /**
     * 批量新增营养推荐
     *
     * @param recommends 营养推荐List
     * @return 结果
     */
    public int batchInsertRecommend(List<Recommend> recommends);

    /**
     * 修改营养推荐
     *
     * @param recommend 营养推荐
     * @return 结果
     */
    public int updateRecommend(Recommend recommend);

    /**
     * 批量删除营养推荐
     *
     * @param recommendIds 需要删除的营养推荐主键集合
     * @return 结果
     */
    public int deleteRecommendByRecommendIds(String[] recommendIds);

    /**
     * 删除营养推荐信息
     *
     * @param recommendId 营养推荐主键
     * @return 结果
     */
    public int deleteRecommendByRecommendId(String recommendId);
}
