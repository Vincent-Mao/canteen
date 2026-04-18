<template>
    <div class="cart-page">


        <!-- 购物车内容区域 -->
        <div v-if="cartList.length > 0">
            <el-table :data="cartList" class="cart-table" @selection-change="handleSelection">
                <el-table-column type="selection" width="55"/>

                <el-table-column label="菜品信息" prop="dishInfo">
                    <template #default="scope">
                        <div class="dish-info">
                            <img :src="baseUrl + scope.row.image" alt="" class="dish-image">
                            <div class="dish-details">
                                <div class="dish-name">{{ scope.row.name }}</div>
                                <div class="dish-description">{{ scope.row.description }}</div>
                            </div>
                        </div>
                    </template>
                </el-table-column>

                <el-table-column label="单价" prop="dish.price" width="200">
                    <template #default="scope">
                        ¥{{ scope.row.price }}
                    </template>
                </el-table-column>

                <el-table-column label="数量" prop="dish.price" width="200">
                    <template #default="scope">
                        <el-input-number v-model="scope.row.quantity"
                                         :min="1"
                                         :max="99"
                                         @change="updateQuantity(scope.row)"
                        />
                    </template>
                </el-table-column>

                <el-table-column label="小计" width="100">
                    <template #default="scope">
                        ¥{{ (scope.row.price * scope.row.quantity).toFixed(2) }}
                    </template>
                </el-table-column>

                <el-table-column label="操作" width="120">
                    <template #default="scope">
                        <el-button type="danger" icon="Delete"
                                   circle size="small" @click="removeCart(scope.row.cartId)"
                        />
                    </template>
                </el-table-column>
            </el-table>

            <!-- 购物车汇总区域 -->
            <div class="cart-summary">
                <div class="summary-content">
                    <div class="summary-item">
                        <span class="total-price">合计: ¥{{ totalPrice }}</span>
                    </div>
                    <el-button type="primary" size="large" class="checkout-btn"
                               @click="checkout" :disabled="selectedItems.length === 0">
                        去结算
                    </el-button>
                </div>
            </div>

        </div>

        <!-- 购物车没有数据 -->
        <div class="empty-cart" v-else>
            <el-empty description="购物车为空">
                <el-button type="primary" @click="router.push('/index/menu')">去点餐</el-button>
            </el-empty>
        </div>

    </div>
</template>

<script setup>
import {onMounted, ref, computed} from 'vue'
import {delCart, selectMyCartList, updateCart} from "@/api/canteen/cart.js";
import {ElMessage, ElMessageBox} from "element-plus";
import {useRouter} from "vue-router";
import {addOrder} from "@/api/canteen/order.js";

//基础URL
const baseUrl = import.meta.env.VITE_APP_BASE_API

//路由实例
const router = useRouter()

//结算
const checkout = () => {
    //获取选中菜品的购物车ID数组
    const cartIds = selectedItems.value.map(item => item.cartId)

    const item = {
        totalPrice: totalPrice.value,
        orderOdList: selectedItems.value
    }

    addOrder(item).then(res => {
        //清除购物车中已点餐的菜品
        delCart(cartIds)
        ElMessage.success('已生成订单')
        //跳转到订单页面
        router.push('/index/orders')
    })

}

//购物车列表数据
const cartList = ref([])

//计算属性: 总价格
const totalPrice = computed(() => {
    return selectedItems.value.reduce((total, item) => {
        return total + (item.price * item.quantity)
    }, 0)
})

//当前选中的菜品项
const selectedItems = ref([])

//处理表格的多选
const handleSelection = (selection) => {
    selectedItems.value = selection
}

//从购物车移出
const removeCart = (cartId) => {
    ElMessageBox.confirm(
        '确定要将该菜品移出购物车吗?',
        '提示',
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(() => {
            delCart(cartId).then(res => {
                getList()
                ElMessage.success('移出成功')
            })
        })
}

//更新商品数量
const updateQuantity = (row) => {
    //构造更新数据
    const item = {
        cartId: row.cartId,
        quantity: row.quantity
    }
    updateCart(item)
}

const getList = () => {
    selectMyCartList().then(res => {
        cartList.value = res.data
    })
}

onMounted(() => {
    getList()
})

</script>

<style scoped>
/* 购物车页面主容器样式 */
.cart-page {
    padding: 20px; /* 内边距 */
    max-width: 1200px; /* 最大宽度 */
    margin: 0 auto; /* 水平居中 */
}

/* 购物车头部样式 */
.cart-header {
    display: flex; /* 弹性布局 */
    justify-content: space-between; /* 两端对齐 */
    align-items: center; /* 垂直居中 */
    margin-bottom: 20px; /* 底部外边距 */
}

/* 购物车表格样式 */
.cart-table {
    margin-bottom: 20px; /* 底部外边距 */
}

/* 菜品信息容器样式 */
.dish-info {
    display: flex; /* 弹性布局 */
    align-items: center; /* 垂直居中 */
}

/* 菜品图片样式 */
.dish-image {
    width: 80px; /* 宽度 */
    height: 80px; /* 高度 */
    border-radius: 8px; /* 圆角 */
    object-fit: cover; /* 图片填充方式：覆盖 */
    margin-right: 15px; /* 右外边距 */
}

/* 菜品详情容器样式 */
.dish-details {
    flex: 1; /* 占据剩余空间 */
}

/* 菜品名称样式 */
.dish-name {
    font-size: 16px; /* 字体大小 */
    font-weight: 500; /* 字体粗细 */
    color: #333; /* 字体颜色 */
    margin-bottom: 5px; /* 底部外边距 */
}

/* 菜品描述样式 */
.dish-description {
    font-size: 14px; /* 字体大小 */
    color: #999; /* 浅灰色字体 */
}

/* 购物车汇总区域样式 */
.cart-summary {
    background: #f5f7fa; /* 浅灰色背景 */
    border-radius: 8px; /* 圆角 */
    padding: 20px; /* 内边距 */
    margin-bottom: 20px; /* 底部外边距 */
}

/* 汇总内容容器样式 */
.summary-content {
    display: flex; /* 弹性布局 */
    justify-content: space-between; /* 两端对齐 */
    align-items: center; /* 垂直居中 */
}

/* 汇总项样式 */
.summary-item {
    display: flex; /* 弹性布局 */
    align-items: center; /* 垂直居中 */
    gap: 20px; /* 子项间距 */
    font-size: 16px; /* 字体大小 */
    color: #666; /* 中灰色字体 */
}

/* 总价样式 */
.total-price {
    font-size: 20px; /* 较大的字体大小 */
    color: #f56c6c; /* 主题红色 */
    font-weight: bold; /* 粗体 */
}

/* 结算按钮样式 */
.checkout-btn {
    width: 120px; /* 固定宽度 */
}

/* 空购物车状态样式 */
.empty-cart {
    padding: 50px 0; /* 上下内边距 */
    text-align: center; /* 文本居中 */
}
</style>
