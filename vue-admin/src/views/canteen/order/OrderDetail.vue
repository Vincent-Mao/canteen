<template>
    <vxe-modal title="订单详情" v-model="open" width="40%" show-maximize showFooter resize>
        <el-descriptions
            :column="2"
            border
        >
            <el-descriptions-item>
                <template #label>
                    订单号
                </template>
                {{ order.orderId }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    总价
                </template>
                {{ order.totalPrice }}元
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    状态
                </template>
                <dict-tag :options="order_status" :value="order.status"/>
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    下单时间
                </template>
                {{ order.createTime }}
            </el-descriptions-item>
        </el-descriptions>
        <br>
        <el-table :data="order.orderOdList" height="30vh">
            <el-table-column label="序号" align="center" type="index" width="50"/>
            <el-table-column label="菜品名称" prop="name">
            </el-table-column>
            <el-table-column label="数量" prop="quantity" width="150">
            </el-table-column>
            <el-table-column label="单价" prop="price" width="150">
            </el-table-column>
        </el-table>

    </vxe-modal>
</template>

<script setup>
import {ref} from 'vue'
import {getOrder} from "@/api/canteen/order.js";

//当前组件的实例
const {proxy} = getCurrentInstance()
//字典数据
const {order_status} = proxy.useDict('order_status')

//对话框是否打开
const open = ref(false)

//订单数据
const order = ref({})

//父组件调用方法
const handleOpen = (orderId) => {
    getOrder(orderId).then(res => {
        order.value = res.data
        open.value = true
    })
}

//暴露方法
defineExpose({
    handleOpen
})
</script>

<style scoped>

</style>
