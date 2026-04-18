package com.fast.canteen.domain;

import com.fast.system.general.annotation.Excel;
import com.fast.system.general.core.domain.BaseEntity;
import lombok.*;

import java.math.BigDecimal;

/**
 * 收藏对象 collect
 *
 * @author fast
 * @date 2025-11-20
 */
@Data
public class Collect extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 收藏ID */
    private String collectId;

    /** 菜品ID */
    private String dishesId;

    /** 收藏用户ID */
    private Long userId;

    //菜品名称
    @Excel(name = "菜名名称")
    private String dishesName;

    //图片
    @Excel(name = "图片", cellType = Excel.ColumnType.IMAGE, height = 70)
    private String image;

    //描述
    @Excel(name = "描述", width = 40)
    private String description;

    //价格
    @Excel(name = "价格")
    private BigDecimal price;

    //收藏用户
    @Excel(name = "收藏用户")
    private String userName;


}
