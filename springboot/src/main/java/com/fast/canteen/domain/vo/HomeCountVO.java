package com.fast.canteen.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 后台首页统计数据VO
 */
@Data
public class HomeCountVO {
    //用户数
    private Integer userCount;
    //订单数
    private Integer orderCount;
    //总收入
    private BigDecimal income;
    //已完成数
    private Integer completedCount;
}
