package com.fast.canteen.service.impl;

import java.util.List;

import com.fast.system.general.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fast.canteen.mapper.TipMapper;
import com.fast.canteen.domain.Tip;
import com.fast.canteen.service.ITipService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

/**
 * 健康小贴士Service业务层处理
 *
 * @author fast
 * @date 2025-11-23
 */
@Service
public class TipServiceImpl implements ITipService {
    @Autowired
    private TipMapper tipMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询健康小贴士
     *
     * @param tipId 健康小贴士主键
     * @return 健康小贴士
     */
    @Override
    public Tip selectTipByTipId(String tipId) {
        return tipMapper.selectTipByTipId(tipId);
    }

    /**
     * 查询健康小贴士列表
     *
     * @param tip 健康小贴士
     * @return 健康小贴士
     */
    @Override
    public List<Tip> selectTipList(Tip tip) {
        return tipMapper.selectTipList(tip);
    }

    /**
     * 新增健康小贴士
     *
     * @param tip 健康小贴士
     * @return 结果
     */
    @Override
    public int insertTip(Tip tip) {
        tip.setTipId(IdUtils.fastSimpleUUID());
        return tipMapper.insertTip(tip);
    }

    /**
     * 批量新增健康小贴士
     *
     * @param tips 健康小贴士List
     * @return 结果
     */
    @Override
    public int batchInsertTip(List<Tip> tips) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(tips)) {
            try {
                for (int i = 0; i < tips.size(); i++) {
                    int row = tipMapper.insertTip(tips.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i > 0 && i % 100 == 0) || i == tips.size() - 1;
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
     * 修改健康小贴士
     *
     * @param tip 健康小贴士
     * @return 结果
     */
    @Override
    public int updateTip(Tip tip) {
        return tipMapper.updateTip(tip);
    }

    /**
     * 批量删除健康小贴士
     *
     * @param tipIds 需要删除的健康小贴士主键
     * @return 结果
     */
    @Override
    public int deleteTipByTipIds(String[] tipIds) {
        return tipMapper.deleteTipByTipIds(tipIds);
    }

    /**
     * 删除健康小贴士信息
     *
     * @param tipId 健康小贴士主键
     * @return 结果
     */
    @Override
    public int deleteTipByTipId(String tipId) {
        return tipMapper.deleteTipByTipId(tipId);
    }
}
