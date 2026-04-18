package com.fast.canteen.domain;

import java.math.BigDecimal;
import com.fast.system.general.annotation.Excel;
import com.fast.system.general.core.domain.BaseEntity;
import lombok.*;
/**
 * 订单菜品对象 order_od
 *
 * @author fast
 * @date 2025-11-21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderOd extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单菜品ID */
    private Long odId;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderId;

    /** 菜品名称 */
    @Excel(name = "菜品名称")
    private String name;

    /** 菜品图片 */
    @Excel(name = "菜品图片")
    private String image;

    /** 数量 */
    @Excel(name = "数量")
    private Long quantity;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal price;


}
