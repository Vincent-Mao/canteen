package com.fast.canteen.controller;

import java.math.BigDecimal;
import java.util.List;

import com.fast.system.domain.SysUser;
import com.fast.system.service.ISysUserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fast.system.general.core.controller.BaseController;
import com.fast.system.general.core.domain.AjaxResult;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;
import com.fast.canteen.domain.Order;
import com.fast.canteen.service.IOrderService;
import com.fast.system.general.utils.poi.ExcelUtil;
import com.fast.system.general.core.page.TableDataInfo;

/**
 * 订单Controller
 *
 * @author fast
 * @date 2025-11-21
 */
@RestController
@RequestMapping("/canteen/order")
public class OrderController extends BaseController {
    @Autowired
    private IOrderService orderService;

    @Autowired
    private ISysUserService userService;

    /**
     * 查询订单列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Order order) {
        startPage();
        List<Order> list = orderService.selectOrderList(order);
        return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, Order order) {
        List<Order> list = orderService.selectOrderList(order);
        ExcelUtil<Order> util = new ExcelUtil<Order>(Order. class);
        util.exportExcel(response, list, "订单数据");
    }

    /**
     * 下载模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<Order> util = new ExcelUtil<Order>(Order. class);
        util.importTemplateExcel(response, "订单数据");
    }

    /**
     * 导入数据
     */
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<Order> util = new ExcelUtil<Order>(Order. class);
        InputStream inputStream = file.getInputStream();
        List<Order> list = util.importExcel(inputStream);
        inputStream.close();
        int count = orderService.batchInsertOrder(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }

    /**
     * 获取订单详细信息
     */
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") String orderId) {
        return success(orderService.selectOrderByOrderId(orderId));
    }

    /**
     * 新增订单
     */
    @PostMapping
    public AjaxResult add(@RequestBody Order order) {
        return toAjax(orderService.insertOrder(order));
    }

    /**
     * 修改订单
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Order order) {
        return toAjax(orderService.updateOrder(order));
    }

    /**
     * 删除订单
     */
    @DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable String[] orderIds) {
        return toAjax(orderService.deleteOrderByOrderIds(orderIds));
    }

    /**
     * 查询用户个人的订单列表
     */
    @GetMapping("/selectMyOrder")
    public AjaxResult selectMyOrder(Order order) {
        order.setUserId(getUserId());
        List<Order> list = orderService.selectOrderList(order);
        return success(list);
    }

    /**
     * 支付订单
     */
    @PutMapping("/payment/{orderId}")
    public AjaxResult payment(@PathVariable String orderId) {
        //查询用户支付前的饭卡余额
        BigDecimal oldBalance = userService.selectUserById(getUserId()).getBalance();

        //用户要支付的金额
        BigDecimal totalPrice = orderService.selectOrderByOrderId(orderId).getTotalPrice();

        //对比金额是否小于余额
        if (oldBalance.compareTo(totalPrice) < 0) {
            throw new RuntimeException("余额不足, 当前余额:" + oldBalance + "元,需要支付:" + totalPrice + "元");
        }

        //余额充足, 进行扣费
        BigDecimal newBalance = oldBalance.subtract(totalPrice);

        //更新用户余额
        SysUser user = new SysUser();
        user.setUserId(getUserId());
        user.setBalance(newBalance);
        userService.updateUser(user);

        //将订单状态修改为制作中
        Order order = orderService.selectOrderByOrderId(orderId);

        Order updOrder = new Order();
        updOrder.setOrderId(orderId);
        updOrder.setStatus("制作中");
        updOrder.setOrderOdList(order.getOrderOdList());

        return toAjax(orderService.updateOrder(updOrder));
    }

}
