package com.fast.canteen.service.impl;

import java.util.List;

import com.fast.system.general.utils.DateUtils;
import com.fast.system.general.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fast.canteen.mapper.FeedbackMapper;
import com.fast.canteen.domain.Feedback;
import com.fast.canteen.service.IFeedbackService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

import static com.fast.system.general.utils.SecurityUtils.getUserId;

/**
 * 意见反馈Service业务层处理
 *
 * @author fast
 * @date 2025-11-23
 */
@Service
public class FeedbackServiceImpl implements IFeedbackService {
    @Autowired
    private FeedbackMapper feedbackMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询意见反馈
     *
     * @param feedbackId 意见反馈主键
     * @return 意见反馈
     */
    @Override
    public Feedback selectFeedbackByFeedbackId(String feedbackId) {
        return feedbackMapper.selectFeedbackByFeedbackId(feedbackId);
    }

    /**
     * 查询意见反馈列表
     *
     * @param feedback 意见反馈
     * @return 意见反馈
     */
    @Override
    public List<Feedback> selectFeedbackList(Feedback feedback) {
        return feedbackMapper.selectFeedbackList(feedback);
    }

    /**
     * 新增意见反馈
     *
     * @param feedback 意见反馈
     * @return 结果
     */
    @Override
    public int insertFeedback(Feedback feedback) {
        feedback.setFeedbackId(IdUtils.fastSimpleUUID());
        feedback.setUserId(getUserId());
        feedback.setCreateTime(DateUtils.getNowDate());
        return feedbackMapper.insertFeedback(feedback);
    }

    /**
     * 批量新增意见反馈
     *
     * @param feedbacks 意见反馈List
     * @return 结果
     */
    @Override
    public int batchInsertFeedback(List<Feedback> feedbacks) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(feedbacks)) {
            try {
                for (int i = 0; i < feedbacks.size(); i++) {
                    int row = feedbackMapper.insertFeedback(feedbacks.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i > 0 && i % 100 == 0) || i == feedbacks.size() - 1;
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
     * 修改意见反馈
     *
     * @param feedback 意见反馈
     * @return 结果
     */
    @Override
    public int updateFeedback(Feedback feedback) {
        return feedbackMapper.updateFeedback(feedback);
    }

    /**
     * 批量删除意见反馈
     *
     * @param feedbackIds 需要删除的意见反馈主键
     * @return 结果
     */
    @Override
    public int deleteFeedbackByFeedbackIds(String[] feedbackIds) {
        return feedbackMapper.deleteFeedbackByFeedbackIds(feedbackIds);
    }

    /**
     * 删除意见反馈信息
     *
     * @param feedbackId 意见反馈主键
     * @return 结果
     */
    @Override
    public int deleteFeedbackByFeedbackId(String feedbackId) {
        return feedbackMapper.deleteFeedbackByFeedbackId(feedbackId);
    }
}
