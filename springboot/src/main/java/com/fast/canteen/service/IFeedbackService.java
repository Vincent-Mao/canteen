package com.fast.canteen.service;

import java.util.List;
import com.fast.canteen.domain.Feedback;

/**
 * 意见反馈Service接口
 *
 * @author fast
 * @date 2025-11-23
 */
public interface IFeedbackService
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
     * 批量新增意见反馈
     *
     * @param feedbacks 意见反馈List
     * @return 结果
     */
    public int batchInsertFeedback(List<Feedback> feedbacks);

    /**
     * 修改意见反馈
     *
     * @param feedback 意见反馈
     * @return 结果
     */
    public int updateFeedback(Feedback feedback);

    /**
     * 批量删除意见反馈
     *
     * @param feedbackIds 需要删除的意见反馈主键集合
     * @return 结果
     */
    public int deleteFeedbackByFeedbackIds(String[] feedbackIds);

    /**
     * 删除意见反馈信息
     *
     * @param feedbackId 意见反馈主键
     * @return 结果
     */
    public int deleteFeedbackByFeedbackId(String feedbackId);
}
