package com.fast.canteen.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.fast.canteen.domain.vo.PieVO;
import org.apache.ibatis.annotations.Mapper;
import com.fast.canteen.domain.Order;
import com.fast.canteen.domain.OrderOd;

/**
 * 订单Mapper接口
 *
 * @author fast
 * @date 2025-11-21
 */
@Mapper
public interface OrderMapper
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
     * 修改订单
     *
     * @param order 订单
     * @return 结果
     */
    public int updateOrder(Order order);

    /**
     * 删除订单
     *
     * @param orderId 订单主键
     * @return 结果
     */
    public int deleteOrderByOrderId(String orderId);

    /**
     * 批量删除订单
     *
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderByOrderIds(String[] orderIds);

    /**
     * 批量删除订单菜品
     *
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderOdByOrderIds(String[] orderIds);

    /**
     * 批量新增订单菜品
     *
     * @param orderOdList 订单菜品列表
     * @return 结果
     */
    public int batchOrderOd(List<OrderOd> orderOdList);


    /**
     * 通过订单主键删除订单菜品信息
     *
     * @param orderId 订单ID
     * @return 结果
     */
    public int deleteOrderOdByOrderId(String orderId);

    /**
     * 查询订单总金额
     * @return
     */
    BigDecimal selectOrderTotalAmount();

    /**
     * 查询指定日期的销售金额
     * @param date
     * @return
     */
    BigDecimal selectSalesAmountByDate(String date);

    /**
     * 查询订单状态分布数据
     * @return
     */
    List<PieVO> selectOrderStatusChart();
}
