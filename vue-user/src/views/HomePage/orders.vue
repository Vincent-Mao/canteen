<template>
    <div class="orders-page">
        <div class="orders-container">
            <!-- 状态筛选 -->
            <el-tabs v-model="selectedStatus" @tab-change="handleTabChange">
                <el-tab-pane label="全部订单" name="all"/>
                <el-tab-pane label="待支付" name="待支付"/>
                <el-tab-pane label="制作中" name="制作中"/>
                <el-tab-pane label="待取餐" name="待取餐"/>
                <el-tab-pane label="已完成" name="已完成"/>
                <el-tab-pane label="已取消" name="已取消"/>
            </el-tabs>

            <!-- 订单列表 -->
            <div>
                <div class="order-item"
                     v-for="order in orderList"
                     :key="order.orderId"
                >
                    <div class="order-header">
                        <div class="order-id">订单号: {{ order.orderId }}</div>
                        <div class="order-status">
                            <dict-tag :options="order_status" :value="order.status"/>
                        </div>
                    </div>

                    <div class="order-content">
                        <div class="order-dishes">
                            <div class="dish-item"
                                 v-for="dish in order.orderOdList"
                                 :key="dish"
                            >
                                <el-image :src="baseUrl + dish.image" class="dish-image" fit="cover"/>
                                <div class="dish-info">
                                    <div class="dish-name">{{ dish.name }}</div>
                                    <div class="dish-quantity">×{{ dish.quantity }}</div>
                                </div>
                            </div>
                        </div>

                        <div class="order-summary">
                            <div class="order-time">{{ order.createTime }}</div>
                            <div class="order-total">共{{ order.orderOdList.length }}种菜品
                                ¥{{ order.totalPrice }}
                            </div>
                        </div>
                    </div>

                    <!-- 订单操作 -->
                    <div class="order-footer">
                        <el-button v-if="order.status === '待支付'" type="primary"
                                   size="small" @click="payOrder(order.orderId)">
                            立即支付
                        </el-button>
                        <el-button v-if="order.status === '待取餐'" type="success"
                                   size="small" @click="confirm(order)">
                            确认取餐
                        </el-button>
                        <el-button v-if="order.status === '待支付'" type="info"
                                   size="small" @click="cancelOrder(order)">
                            取消订单
                        </el-button>
                    </div>

                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import {onMounted, ref} from 'vue'
import {payment, selectMyOrder, updateOrder} from "@/api/canteen/order.js";
import {ElMessage, ElMessageBox} from "element-plus";

//确认取餐
const confirm = (order) => {
    ElMessageBox.confirm(
        '确认已取餐吗?',
        '提示',
        {confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning',}
    )
        .then(() => {
            const item = {
                orderId: order.orderId,
                status: '已完成',
                orderOdList: order.orderOdList
            }
            updateOrder(item).then(res => {
                getList()
                ElMessage.success('取餐成功!')
            })
        })
}

//取消订单
const cancelOrder = (order) => {
    ElMessageBox.confirm(
        '确认要取消该订单吗?',
        '提示',
        {confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning',}
    )
        .then(() => {
            const item = {
                orderId: order.orderId,
                status: '已取消',
                orderOdList: order.orderOdList
            }
            updateOrder(item).then(res => {
                getList()
                ElMessage.success('取消成功!')
            })
        })
}

//支付订单
const payOrder = (orderId) => {
    ElMessageBox.confirm(
        '确认要支付此订单吗?',
        '提示',
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(() => {
            payment(orderId).then(res => {
                getList()
                ElMessage.success('支付成功!')
            })
        })
}

//标签页切换
const handleTabChange = (tab) => {
    console.log(selectedStatus)
    if (tab === 'all') {
        query.value.status = null //全部订单, 不需要筛选
        getList()
    } else {
        query.value.status = selectedStatus.value
        getList()
    }
}

//当前选中的订单状态
const selectedStatus = ref('all')

//基础URL
const baseUrl = import.meta.env.VITE_APP_BASE_API

//字典数据
const {proxy} = getCurrentInstance()
const {order_status} = proxy.useDict('order_status')

//订单列表数据
const orderList = ref([])

//查询参数
const query = ref({
    status: null
})

const getList = () => {
    selectMyOrder(query.value).then(res => {
        orderList.value = res.data
    })
}

onMounted(() => {
    getList()
})

</script>

<style scoped>
/* 订单页面主容器样式 */
.orders-page {
    padding: 20px; /* 内边距 */
    max-width: 1200px; /* 最大宽度 */
    margin: 0 auto; /* 水平居中 */
}

/* 页面标题样式 */
.orders-page h1 {
    font-size: 24px; /* 字体大小 */
    color: #333; /* 字体颜色 */
    margin-bottom: 20px; /* 底部外边距 */
}

/* 订单内容容器样式 */
.orders-container {
    background: #fff; /* 白色背景 */
    border-radius: 8px; /* 圆角边框 */
    padding: 20px; /* 内边距 */
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); /* 阴影效果 */
}

/* 深度选择器修改 Element UI 标签页下划线样式 */
:deep(.el-tabs__nav-wrap)::after {
    height: 1px; /* 下划线高度 */
}

/* 单个订单项样式 */
.order-item {
    border: 1px solid #ebeef5; /* 边框颜色 */
    border-radius: 8px; /* 圆角 */
    padding: 15px; /* 内边距 */
    margin-bottom: 20px; /* 底部外边距 */
    transition: all 0.3s; /* 过渡动画 */
}

/* 订单项悬停效果 */
.order-item:hover {
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); /* 悬停阴影 */
}

/* 订单头部样式 */
.order-header {
    display: flex; /* 弹性布局 */
    justify-content: space-between; /* 两端对齐 */
    align-items: center; /* 垂直居中 */
    padding-bottom: 10px; /* 底部内边距 */
    border-bottom: 1px solid #f0f0f0; /* 底部边框 */
    margin-bottom: 15px; /* 底部外边距 */
}

/* 订单号样式 */
.order-id {
    font-size: 14px; /* 字体大小 */
    color: #999; /* 字体颜色 */
}

/* 订单状态样式 */
.order-status {
    font-size: 14px; /* 字体大小 */
    font-weight: 500; /* 字体粗细 */
    padding: 4px 10px; /* 内边距 */
    border-radius: 4px; /* 圆角 */
}

/* 订单内容区域样式 */
.order-content {
    display: flex; /* 弹性布局 */
    justify-content: space-between; /* 两端对齐 */
    margin-bottom: 15px; /* 底部外边距 */
}

/* 订单菜品列表样式 */
.order-dishes {
    display: flex; /* 弹性布局 */
    align-items: center; /* 垂直居中 */
}

/* 单个菜品项样式 */
.dish-item {
    display: flex; /* 弹性布局 */
    align-items: center; /* 垂直居中 */
    margin-right: 15px; /* 右侧外边距 */
}

/* 菜品图片样式 */
.dish-image {
    width: 50px; /* 宽度 */
    height: 50px; /* 高度 */
    border-radius: 4px; /* 圆角 */
    margin-right: 8px; /* 右侧外边距 */
}

/* 菜品名称样式 */
.dish-name {
    font-size: 14px; /* 字体大小 */
    color: #333; /* 字体颜色 */
    max-width: 80px; /* 最大宽度 */
    overflow: hidden; /* 溢出隐藏 */
    text-overflow: ellipsis; /* 文本溢出显示省略号 */
    white-space: nowrap; /* 不换行 */
}

/* 菜品数量样式 */
.dish-quantity {
    font-size: 14px; /* 字体大小 */
    color: #999; /* 字体颜色 */
    margin-left: 5px; /* 左侧外边距 */
}

/* 订单摘要信息样式 */
.order-summary {
    text-align: right; /* 文本右对齐 */
}

/* 订单时间样式 */
.order-time {
    font-size: 14px; /* 字体大小 */
    color: #999; /* 字体颜色 */
    margin-bottom: 5px; /* 底部外边距 */
}

/* 订单总价样式 */
.order-total {
    font-size: 16px; /* 字体大小 */
    color: #333; /* 字体颜色 */
    font-weight: 500; /* 字体粗细 */
}

/* 订单底部操作按钮区域样式 */
.order-footer {
    display: flex; /* 弹性布局 */
    justify-content: flex-end; /* 右对齐 */
    gap: 10px; /* 按钮间距 */
}

/* 无订单时的空状态样式 */
.no-orders {
    text-align: center; /* 文本居中 */
    padding: 50px 0; /* 上下内边距 */
}
</style>
