package com.fast.canteen.domain;

import com.fast.system.general.annotation.Excel;
import com.fast.system.general.core.domain.BaseEntity;
import lombok.*;

/**
 * 健康小贴士对象 tip
 *
 * @author fast
 * @date 2025-11-23
 */
@Data
public class Tip extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 贴士ID */
    private String tipId;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;


}
