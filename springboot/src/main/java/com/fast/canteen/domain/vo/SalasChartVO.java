package com.fast.canteen.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 销售趋势柱状图VO
 */
@Data
public class SalasChartVO {
    //星期
    private String[] weekData;
    //销售额
    private BigDecimal[] salesCount;
}
