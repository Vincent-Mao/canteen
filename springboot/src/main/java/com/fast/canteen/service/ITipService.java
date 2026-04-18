package com.fast.canteen.service;

import java.util.List;
import com.fast.canteen.domain.Tip;

/**
 * 健康小贴士Service接口
 *
 * @author fast
 * @date 2025-11-23
 */
public interface ITipService
{
    /**
     * 查询健康小贴士
     *
     * @param tipId 健康小贴士主键
     * @return 健康小贴士
     */
    public Tip selectTipByTipId(String tipId);

    /**
     * 查询健康小贴士列表
     *
     * @param tip 健康小贴士
     * @return 健康小贴士集合
     */
    public List<Tip> selectTipList(Tip tip);

    /**
     * 新增健康小贴士
     *
     * @param tip 健康小贴士
     * @return 结果
     */
    public int insertTip(Tip tip);

    /**
     * 批量新增健康小贴士
     *
     * @param tips 健康小贴士List
     * @return 结果
     */
    public int batchInsertTip(List<Tip> tips);

    /**
     * 修改健康小贴士
     *
     * @param tip 健康小贴士
     * @return 结果
     */
    public int updateTip(Tip tip);

    /**
     * 批量删除健康小贴士
     *
     * @param tipIds 需要删除的健康小贴士主键集合
     * @return 结果
     */
    public int deleteTipByTipIds(String[] tipIds);

    /**
     * 删除健康小贴士信息
     *
     * @param tipId 健康小贴士主键
     * @return 结果
     */
    public int deleteTipByTipId(String tipId);
}
