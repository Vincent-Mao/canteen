package com.fast.canteen.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fast.canteen.domain.Recommend;

/**
 * 营养推荐Mapper接口
 *
 * @author fast
 * @date 2025-11-23
 */
@Mapper
public interface RecommendMapper
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
     * 修改营养推荐
     *
     * @param recommend 营养推荐
     * @return 结果
     */
    public int updateRecommend(Recommend recommend);

    /**
     * 删除营养推荐
     *
     * @param recommendId 营养推荐主键
     * @return 结果
     */
    public int deleteRecommendByRecommendId(String recommendId);

    /**
     * 批量删除营养推荐
     *
     * @param recommendIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRecommendByRecommendIds(String[] recommendIds);
}
