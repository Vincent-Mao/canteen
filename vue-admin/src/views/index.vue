<template>
    <div class="dashboard">
        <el-row :gutter="20" class="mb20">
            <el-col :span="6">
                <el-card class="stat-card" shadow="hover">
                    <div class="stat-item">
                        <div class="stat-icon" style="background-color: #409EFF20; color: #409EFF;">
                            <user :size="30"/>
                        </div>
                        <div class="stat-info">
                            <div class="stat-value">{{ homeData.userCount }}</div>
                            <div class="stat-label">总用户数</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card class="stat-card" shadow="hover">
                    <div class="stat-item">
                        <div class="stat-icon" style="background-color: #67C23A20; color: #67C23A;">
                            <shopping-bag :size="30"/>
                        </div>
                        <div class="stat-info">
                            <div class="stat-value">{{ homeData.orderCount }}</div>
                            <div class="stat-label">订单数</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card class="stat-card" shadow="hover">
                    <div class="stat-item">
                        <div class="stat-icon" style="background-color: #E6A23C20; color: #E6A23C;">
                            <money :size="30"/>
                        </div>
                        <div class="stat-info">
                            <div class="stat-value">¥{{ formatAmount(homeData.income) }}</div>
                            <div class="stat-label">总收入</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card class="stat-card" shadow="hover">
                    <div class="stat-item">
                        <div class="stat-icon" style="background-color: #90939920; color: #59bd6a;">
                            <Select :size="30"/>
                        </div>
                        <div class="stat-info">
                            <div class="stat-value">{{ homeData.completedCount }}</div>
                            <div class="stat-label">已完成</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
        </el-row>

        <el-row :gutter="20" class="mb20">
            <el-col :span="14">
                <el-card shadow="hover">
                    <template #header>
                        <div class="card-header">
                            <span>销售趋势</span>
                        </div>
                    </template>
                    <div ref="salesChartRef" class="chart-container"></div>
                </el-card>
            </el-col>

            <el-col :span="10">
                <el-card shadow="hover">
                    <template #header>
                        <div class="card-header">
                            <span>订单状态分布</span>
                        </div>
                    </template>
                    <div ref="statusChartRef" class="chart-container"></div>
                </el-card>
            </el-col>
        </el-row>


    </div>
</template>

<script setup>
import {onMounted, ref} from 'vue'
import {Money, ShoppingBag, User} from "@element-plus/icons-vue";
import {selectHomeCount, selectOrderStatusChart, selectSalesChart} from "@/api/canteen/homePage.js";
import * as echarts from 'echarts'

//图表实例
const salesChartRef = ref()
const statusChartRef = ref()

//格式化金额
const formatAmount = (amount) => {
    if (!amount) return '0.00'
    return amount.toLocaleString('zh-CN', {minimumFractionDigits: 2})
}

//顶部统计数据
const homeData = ref({})

//初始化图表
const initCharts = () => {
    //销售趋势图
    selectSalesChart().then(res => {
        const salesChart = echarts.init(salesChartRef.value)
        salesChart.setOption({
            tooltip: {
                trigger: 'axis'
            },
            xAxis: {
                type: 'category',
                data: res.data.weekData
            },
            yAxis: {
                type: 'value'
            },
            series: [{
                data: res.data.salesCount,
                type: 'bar',
                color: '#409EFF'
            }]
        })
    })

    //订单状态分布图
    selectOrderStatusChart().then(res => {
        const statusChart = echarts.init(statusChartRef.value)
        statusChart.setOption({
            tooltip: {
                trigger: 'item'
            },
            legend: {
                top: 'bottom'
            },
            series: [{
                name: '订单状态',
                type: 'pie',
                radius: ['40%', '70%'],
                avoidLabelOverlap: false,
                itemStyle: {
                    borderRadius: 10,
                    borderColor: '#fff',
                    borderWidth: 2
                },
                label: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    label: {
                        show: true,
                        fontSize: '18',
                        fontWeight: 'bold'
                    }
                },
                labelLine: {
                    show: false
                },
                data: res.data
            }]
        })
    })
}

onMounted(() => {
    selectHomeCount().then(res => {
        homeData.value = res.data
    })
    initCharts()
})

</script>

<style scoped>
/* 仪表盘整体样式 - 作用域样式，只对当前组件有效 */
.dashboard {
    padding: 20px; /* 内边距20像素 */
    background-color: #f5f7fa; /* 浅灰色背景 */
}

/* 工具类：底部外边距20像素 */
.mb20 {
    margin-bottom: 20px;
}

/* 统计卡片样式 */
.stat-card {
    border-radius: 8px; /* 圆角边框，8像素半径 */
}

/* 统计项布局 */
.stat-item {
    display: flex; /* 弹性布局 */
    align-items: center; /* 垂直居中对齐 */
}

/* 统计图标容器样式 */
.stat-icon {
    width: 60px; /* 固定宽度60像素 */
    height: 60px; /* 固定高度60像素 */
    border-radius: 8px; /* 圆角边框，8像素半径 */
    display: flex; /* 弹性布局 */
    align-items: center; /* 垂直居中对齐 */
    justify-content: center; /* 水平居中对齐 */
    margin-right: 15px; /* 右侧外边距15像素 */
}

/* 统计信息区域 */
.stat-info {
    /* 预留样式扩展区域 */
}

/* 统计数值样式 */
.stat-value {
    font-size: 24px; /* 字体大小24像素 */
    font-weight: bold; /* 字体加粗 */
    margin-bottom: 5px; /* 底部外边距5像素 */
}

/* 统计标签样式 */
.stat-label {
    font-size: 14px; /* 字体大小14像素 */
    color: #999; /* 浅灰色文字颜色 */
}

/* 卡片头部布局 */
.card-header {
    display: flex; /* 弹性布局 */
    justify-content: space-between; /* 两端对齐 */
    align-items: center; /* 垂直居中对齐 */
    font-weight: bold; /* 字体加粗 */
}

/* 图表容器样式 */
.chart-container {
    width: 100%; /* 宽度100%填充父容器 */
    height: 500px; /* 固定高度500像素 */
}
</style>
