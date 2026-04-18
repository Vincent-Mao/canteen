package com.fast.canteen.service;

import java.math.BigDecimal;
import java.util.List;
import com.fast.canteen.domain.Order;
import com.fast.canteen.domain.vo.PieVO;
import com.fast.canteen.domain.vo.SalasChartVO;

/**
 * 订单Service接口
 *
 * @author fast
 * @date 2025-11-21
 */
public interface IOrderService
{
    /**
     * 查询订单
     *
     * @param orderId 订单主键
     * @return 订单
     */
    public Order selectOrderByOrderId(String orderId);

    /**
     * 查询订单列表
     *
     * @param order 订单
     * @return 订单集合
     */
    public List<Order> selectOrderList(Order order);

    /**
     * 新增订单
     *
     * @param order 订单
     * @return 结果
     */
    public int insertOrder(Order order);

    /**
     * 批量新增订单
     *
     * @param orders 订单List
     * @return 结果
     */
    public int batchInsertOrder(List<Order> orders);

    /**
     * 修改订单
     *
     * @param order 订单
     * @return 结果
     */
    public int updateOrder(Order order);

    /**
     * 批量删除订单
     *
     * @param orderIds 需要删除的订单主键集合
     * @return 结果
     */
    public int deleteOrderByOrderIds(String[] orderIds);

    /**
     * 删除订单信息
     *
     * @param orderId 订单主键
     * @return 结果
     */
    public int deleteOrderByOrderId(String orderId);

    /**
     * 查询订单总金额
     * @return
     */
    BigDecimal selectOrderTotalAmount();

    /**
     * 查询近一周的销售数据
     * @return
     */
    SalasChartVO selectWeeklySalesData();

    /**
     * 查询订单状态分布数据
     * @return
     */
    List<PieVO> selectOrderStatusChart();
}
