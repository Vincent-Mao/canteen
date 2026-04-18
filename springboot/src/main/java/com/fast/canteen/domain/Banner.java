package com.fast.canteen.domain;

import com.fast.system.general.annotation.Excel;
import com.fast.system.general.core.domain.BaseEntity;
import lombok.*;

import java.math.BigDecimal;

/**
 * 轮播图对象 banner
 *
 * @author fast
 * @date 2025-11-23
 */
@Data
public class Banner extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 轮播图ID */
    private String bannerId;

    /** 菜品ID */
    @Excel(name = "菜品ID")
    private String dishesId;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    //菜品名称
    private String name;

    //描述
    private String description;

    //单价
    private BigDecimal price;

    //图片
    private String image;


}
