package com.fast.canteen.domain;

import com.fast.system.general.annotation.Excel;
import com.fast.system.general.core.domain.BaseEntity;
import lombok.*;

/**
 * 营养推荐对象 recommend
 *
 * @author fast
 * @date 2025-11-23
 */
@Data
public class Recommend extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 营养推荐ID */
    private String recommendId;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    /** 图片 */
    @Excel(name = "图片")
    private String image;

    /** 大卡 */
    @Excel(name = "大卡")
    private Long calories;


}
