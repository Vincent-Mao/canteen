package com.fast.canteen.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fast.canteen.domain.Tip;

/**
 * 健康小贴士Mapper接口
 *
 * @author fast
 * @date 2025-11-23
 */
@Mapper
public interface TipMapper
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
     * 修改健康小贴士
     *
     * @param tip 健康小贴士
     * @return 结果
     */
    public int updateTip(Tip tip);

    /**
     * 删除健康小贴士
     *
     * @param tipId 健康小贴士主键
     * @return 结果
     */
    public int deleteTipByTipId(String tipId);

    /**
     * 批量删除健康小贴士
     *
     * @param tipIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTipByTipIds(String[] tipIds);
}
