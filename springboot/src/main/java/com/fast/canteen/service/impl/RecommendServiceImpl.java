package com.fast.canteen.service.impl;

import java.util.List;

import com.fast.system.general.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fast.canteen.mapper.RecommendMapper;
import com.fast.canteen.domain.Recommend;
import com.fast.canteen.service.IRecommendService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

/**
 * 营养推荐Service业务层处理
 *
 * @author fast
 * @date 2025-11-23
 */
@Service
public class RecommendServiceImpl implements IRecommendService {
    @Autowired
    private RecommendMapper recommendMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询营养推荐
     *
     * @param recommendId 营养推荐主键
     * @return 营养推荐
     */
    @Override
    public Recommend selectRecommendByRecommendId(String recommendId) {
        return recommendMapper.selectRecommendByRecommendId(recommendId);
    }

    /**
     * 查询营养推荐列表
     *
     * @param recommend 营养推荐
     * @return 营养推荐
     */
    @Override
    public List<Recommend> selectRecommendList(Recommend recommend) {
        return recommendMapper.selectRecommendList(recommend);
    }

    /**
     * 新增营养推荐
     *
     * @param recommend 营养推荐
     * @return 结果
     */
    @Override
    public int insertRecommend(Recommend recommend) {
        recommend.setRecommendId(IdUtils.fastSimpleUUID());
        return recommendMapper.insertRecommend(recommend);
    }

    /**
     * 批量新增营养推荐
     *
     * @param recommends 营养推荐List
     * @return 结果
     */
    @Override
    public int batchInsertRecommend(List<Recommend> recommends) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(recommends)) {
            try {
                for (int i = 0; i < recommends.size(); i++) {
                    int row = recommendMapper.insertRecommend(recommends.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i > 0 && i % 100 == 0) || i == recommends.size() - 1;
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
     * 修改营养推荐
     *
     * @param recommend 营养推荐
     * @return 结果
     */
    @Override
    public int updateRecommend(Recommend recommend) {
        return recommendMapper.updateRecommend(recommend);
    }

    /**
     * 批量删除营养推荐
     *
     * @param recommendIds 需要删除的营养推荐主键
     * @return 结果
     */
    @Override
    public int deleteRecommendByRecommendIds(String[] recommendIds) {
        return recommendMapper.deleteRecommendByRecommendIds(recommendIds);
    }

    /**
     * 删除营养推荐信息
     *
     * @param recommendId 营养推荐主键
     * @return 结果
     */
    @Override
    public int deleteRecommendByRecommendId(String recommendId) {
        return recommendMapper.deleteRecommendByRecommendId(recommendId);
    }
}
