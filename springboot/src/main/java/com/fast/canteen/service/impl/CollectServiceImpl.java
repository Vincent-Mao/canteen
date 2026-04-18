package com.fast.canteen.service.impl;

import java.util.List;

import com.fast.system.general.utils.DateUtils;
import com.fast.system.general.utils.SecurityUtils;
import com.fast.system.general.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fast.canteen.mapper.CollectMapper;
import com.fast.canteen.domain.Collect;
import com.fast.canteen.service.ICollectService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

/**
 * 收藏Service业务层处理
 *
 * @author fast
 * @date 2025-11-20
 */
@Service
public class CollectServiceImpl implements ICollectService {
    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询收藏
     *
     * @param collectId 收藏主键
     * @return 收藏
     */
    @Override
    public Collect selectCollectByCollectId(String collectId) {
        return collectMapper.selectCollectByCollectId(collectId);
    }

    /**
     * 查询收藏列表
     *
     * @param collect 收藏
     * @return 收藏
     */
    @Override
    public List<Collect> selectCollectList(Collect collect) {
        return collectMapper.selectCollectList(collect);
    }

    /**
     * 新增收藏
     *
     * @param collect 收藏
     * @return 结果
     */
    @Override
    public int insertCollect(Collect collect) {
        collect.setCreateTime(DateUtils.getNowDate());
        //生成一个UUID并插入至收藏对象中
        collect.setCollectId(IdUtils.fastSimpleUUID());
        //获取到当前操作用户的用户ID并插入至对象中
        collect.setUserId(SecurityUtils.getUserId());
        return collectMapper.insertCollect(collect);
    }

    /**
     * 批量新增收藏
     *
     * @param collects 收藏List
     * @return 结果
     */
    @Override
    public int batchInsertCollect(List<Collect> collects) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(collects)) {
            try {
                for (int i = 0; i < collects.size(); i++) {
                    int row = collectMapper.insertCollect(collects.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i > 0 && i % 100 == 0) || i == collects.size() - 1;
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
     * 修改收藏
     *
     * @param collect 收藏
     * @return 结果
     */
    @Override
    public int updateCollect(Collect collect) {
        return collectMapper.updateCollect(collect);
    }

    /**
     * 批量删除收藏
     *
     * @param collectIds 需要删除的收藏主键
     * @return 结果
     */
    @Override
    public int deleteCollectByCollectIds(String[] collectIds) {
        return collectMapper.deleteCollectByCollectIds(collectIds);
    }

    /**
     * 删除收藏信息
     *
     * @param collectId 收藏主键
     * @return 结果
     */
    @Override
    public int deleteCollectByCollectId(String collectId) {
        return collectMapper.deleteCollectByCollectId(collectId);
    }

    /**
     * 根据菜品ID和用户ID查询收藏ID
     * @param dishesId
     * @param userId
     * @return
     */
    @Override
    public String selectCollectIdByDishesIdAndUserId(String dishesId, Long userId) {
        return collectMapper.selectCollectIdByDishesIdAndUserId(dishesId, userId);
    }
}
