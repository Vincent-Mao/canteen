package com.fast.canteen.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fast.canteen.domain.Feedback;

/**
 * 意见反馈Mapper接口
 *
 * @author fast
 * @date 2025-11-23
 */
@Mapper
public interface FeedbackMapper
{
    /**
     * 查询意见反馈
     *
     * @param feedbackId 意见反馈主键
     * @return 意见反馈
     */
    public Feedback selectFeedbackByFeedbackId(String feedbackId);

    /**
     * 查询意见反馈列表
     *
     * @param feedback 意见反馈
     * @return 意见反馈集合
     */
    public List<Feedback> selectFeedbackList(Feedback feedback);

    /**
     * 新增意见反馈
     *
     * @param feedback 意见反馈
     * @return 结果
     */
    public int insertFeedback(Feedback feedback);

    /**
     * 修改意见反馈
     *
     * @param feedback 意见反馈
     * @return 结果
     */
    public int updateFeedback(Feedback feedback);

    /**
     * 删除意见反馈
     *
     * @param feedbackId 意见反馈主键
     * @return 结果
     */
    public int deleteFeedbackByFeedbackId(String feedbackId);

    /**
     * 批量删除意见反馈
     *
     * @param feedbackIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFeedbackByFeedbackIds(String[] feedbackIds);
}
