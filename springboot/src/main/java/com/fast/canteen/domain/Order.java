package com.fast.canteen.domain;

import java.math.BigDecimal;
import java.util.List;
import com.fast.system.general.annotation.Excel;
import com.fast.system.general.core.domain.BaseEntity;
import lombok.*;

/**
 * 订单对象 order
 *
 * @author fast
 * @date 2025-11-21
 */
@Data
public class Order extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderId;

    /** 总价 */
    @Excel(name = "总价")
    private BigDecimal totalPrice;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    //下单用户
    private String userName;

    /** 订单菜品信息 */
    private List<OrderOd> orderOdList;


}
