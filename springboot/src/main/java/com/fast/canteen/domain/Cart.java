package com.fast.canteen.domain;

import com.fast.system.general.annotation.Excel;
import com.fast.system.general.core.domain.BaseEntity;
import lombok.*;

import java.math.BigDecimal;

/**
 * 购物车对象 cart
 *
 * @author fast
 * @date 2025-11-20
 */
@Data
public class Cart extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 购物车ID */
    private String cartId;

    /** 菜品ID */
    @Excel(name = "菜品ID")
    private String dishesId;

    /** 数量 */
    @Excel(name = "数量")
    private Long quantity;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 菜品名称 */
    private String name;

    /** 图片 */
    private String image;

    /** 描述 */
    private String description;

    /** 价格 */
    private BigDecimal price;

    /** 用户名 */
    private String userName;

}
