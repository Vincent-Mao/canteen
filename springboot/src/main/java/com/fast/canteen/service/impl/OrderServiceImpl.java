package com.fast.canteen.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fast.canteen.domain.vo.PieVO;
import com.fast.canteen.domain.vo.SalasChartVO;
import com.fast.system.general.utils.DateUtils;
import com.fast.system.general.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import com.fast.system.general.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.fast.canteen.domain.OrderOd;
import com.fast.canteen.mapper.OrderMapper;
import com.fast.canteen.domain.Order;
import com.fast.canteen.service.IOrderService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

import static com.fast.system.general.utils.SecurityUtils.getUserId;

/**
 * 订单Service业务层处理
 *
 * @author fast
 * @date 2025-11-21
 */
@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询订单
     *
     * @param orderId 订单主键
     * @return 订单
     */
    @Override
    public Order selectOrderByOrderId(String orderId) {
        return orderMapper.selectOrderByOrderId(orderId);
    }

    /**
     * 查询订单列表
     *
     * @param order 订单
     * @return 订单
     */
    @Override
    public List<Order> selectOrderList(Order order) {
        return orderMapper.selectOrderList(order);
    }

    /**
     * 新增订单
     *
     * @param order 订单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertOrder(Order order) {
        //生成订单号(OR + 年月日时分秒 + 用户ID)

        //获取当前日期时间
        LocalDateTime now = LocalDateTime.now();
        //定义格式器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        //转换为字符串
        String formatDateTime = now.format(formatter);
        //插入订单号到对象中
        order.setOrderId("OR" + formatDateTime + getUserId());

        order.setUserId(getUserId());
        order.setCreateTime(DateUtils.getNowDate());
        int rows = orderMapper.insertOrder(order);
        insertOrderOd(order);
        return rows;
    }

    /**
     * 批量新增订单
     *
     * @param orders 订单List
     * @return 结果
     */
    @Override
    public int batchInsertOrder(List<Order> orders) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(orders)) {
            try {
                for (int i = 0; i < orders.size(); i++) {
                    int row = orderMapper.insertOrder(orders.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i > 0 && i % 100 == 0) || i == orders.size() - 1;
                    if (bool) {
                        sqlSession.commit();
                        sqlSession.clearCache();
                    }
                    count = i + 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
                // 没有提交的数据可以回滚
                sqlSession.rollback();
            } finally {
                sqlSession.close();
                return count;
            }
        }
        return count;
    }

    /**
     * 修改订单
     *
     * @param order 订单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateOrder(Order order) {
        orderMapper.deleteOrderOdByOrderId(order.getOrderId());
        insertOrderOd(order);
        return orderMapper.updateOrder(order);
    }

    /**
     * 批量删除订单
     *
     * @param orderIds 需要删除的订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteOrderByOrderIds(String[] orderIds) {
        orderMapper.deleteOrderOdByOrderIds(orderIds);
        return orderMapper.deleteOrderByOrderIds(orderIds);
    }

    /**
     * 删除订单信息
     *
     * @param orderId 订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteOrderByOrderId(String orderId) {
        orderMapper.deleteOrderOdByOrderId(orderId);
        return orderMapper.deleteOrderByOrderId(orderId);
    }

    /**
     * 查询订单总金额
     * @return
     */
    @Override
    public BigDecimal selectOrderTotalAmount() {
        return orderMapper.selectOrderTotalAmount();
    }

    /**
     * 查询近一周的销售数据
     * @return
     */
    @Override
    public SalasChartVO selectWeeklySalesData() {
        //初始化返回的视图对象
        SalasChartVO salasChartVO = new SalasChartVO();

        //创建最近一周的日期数组
        String[] weekDays = new String[7];
        BigDecimal[] salesData = new BigDecimal[7];

        //获取当前日期作为查询的结束日期
        LocalDate today = LocalDate.now();

        //循环生成最近7天的数据
        for (int i = 6; i >= 0; i--) {
            //计算当前循环对应的日期(今天减去i天)
            LocalDate date = today.minusDays(i);
            //将日期格式化为"月-日"的格式
            weekDays[6 - i] = date.format(DateTimeFormatter.ofPattern("MM-dd"));
            //查询指定日期的销售金额
            salesData[6 - i] = orderMapper.selectSalesAmountByDate(date.toString());
            //如果查询结果为空(当天没有销售额), 则设置为0
            if (salesData[6 - i] == null) {
                salesData[6 - i] = BigDecimal.ZERO;
            }
        }

        //将处理好的数据插入至对象中
        salasChartVO.setWeekData(weekDays);
        salasChartVO.setSalesCount(salesData);

        return salasChartVO;
    }

    /**
     * 查询订单状态分布数据
     * @return
     */
    @Override
    public List<PieVO> selectOrderStatusChart() {
        return orderMapper.selectOrderStatusChart();
    }

    /**
     * 新增订单菜品信息
     *
     * @param order 订单对象
     */
    public void insertOrderOd(Order order) {
        List<OrderOd> orderOdList = order.getOrderOdList();
        String orderId = order.getOrderId();
        if (StringUtils.isNotNull(orderOdList)) {
            List<OrderOd> list = new ArrayList<OrderOd>();
            for (OrderOd orderOd : orderOdList) {
                orderOd.setOrderId(orderId);
                list.add(orderOd);
            }
            if (list.size() > 0) {
                orderMapper.batchOrderOd(list);
            }
        }
    }
}
