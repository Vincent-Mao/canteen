package com.fast.canteen.controller;

import com.fast.canteen.domain.Order;
import com.fast.canteen.domain.vo.HomeCountVO;
import com.fast.canteen.domain.vo.PieVO;
import com.fast.canteen.domain.vo.SalasChartVO;
import com.fast.canteen.service.IOrderService;
import com.fast.system.domain.SysUser;
import com.fast.system.general.core.controller.BaseController;
import com.fast.system.general.core.domain.AjaxResult;
import com.fast.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home/page")
public class HomePageController extends BaseController {
    @Autowired
    private ISysUserService userService;

    @Autowired
    private IOrderService orderService;

    /**
     * 查询顶部数据统计
     */
    @GetMapping("/selectHomeCount")
    public AjaxResult selectHomeCount() {
        HomeCountVO homeCountVO = new HomeCountVO();

        //用户数
        homeCountVO.setUserCount(userService.selectUserList(new SysUser()).size());

        //订单数
        homeCountVO.setOrderCount(orderService.selectOrderList(new Order()).size());

        //总收入
        homeCountVO.setIncome(orderService.selectOrderTotalAmount());

        //已完成数
        Order order = new Order();
        order.setStatus("已完成");
        int completedCount = orderService.selectOrderList(order).size();
        homeCountVO.setCompletedCount(completedCount);

        return success(homeCountVO);
    }

    /**
     * 查询销售趋势数据
     */
    @GetMapping("/selectSalesChart")
    public AjaxResult selectSalesChart() {
        SalasChartVO salasChartVO = orderService.selectWeeklySalesData();
        return success(salasChartVO);
    }

    /**
     * 查询订单状态分布数据
     */
    @GetMapping("/selectOrderStatusChart")
    public AjaxResult selectOrderStatusChart() {
        List<PieVO> pieList = orderService.selectOrderStatusChart();
        return success(pieList);
    }

}
