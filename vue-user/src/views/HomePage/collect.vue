<template>
    <div class="favorites-page">
        <!-- 有收藏菜品时的显示 -->
        <div v-if="myCollectDishes.length > 0">
            <el-row :gutter="20">
                <el-col :span="8"
                        v-for="dish in myCollectDishes"
                        :key="dish.collectId"
                        class="dish-col"
                >
                    <el-card class="dish-card">
                        <div class="dish-image-container">
                            <img :src="baseUrl + dish.image" alt="" class="dish-image">
                        </div>

                        <div class="dish-info">
                            <div class="dish-header">
                                <h3>{{ dish.dishesName }}</h3>
                                <el-button icon="Star"
                                           type="warning"
                                           circle
                                           size="small"
                                           @click="deleteCollect(dish.collectId)"
                                />
                            </div>

                            <p class="dish-description">{{ dish.description }}</p>

                            <div class="dish-footer">
                                <div class="dish-price">¥{{ dish.price }}</div>
                                <el-button type="primary"
                                           size="small"
                                           @click="addToCart(dish.dishesId)"
                                >
                                    加入购物车
                                </el-button>
                            </div>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
        </div>

        <!-- 没有收藏菜品时的显示 -->
        <div class="no-favorites" v-else>
            <el-empty description="您还没有收藏任何菜品">
                <el-button type="primary" @click="router.push('/index/menu')">去点餐</el-button>
            </el-empty>
        </div>
    </div>
</template>

<script setup>
import {onMounted, ref} from 'vue'
import {delCollect, selectMyCollectDishes} from "@/api/canteen/collect.js";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";
import {addCart} from "@/api/canteen/cart.js";

//基础URL
const baseUrl = import.meta.env.VITE_APP_BASE_API

//路由实例
const router = useRouter()

//收藏的菜品列表
const myCollectDishes = ref([])

//添加到购物车
const addToCart = (dishesId) => {
    const item = {
        dishesId: dishesId
    }
    addCart(item).then(res => {
        getMyCollectList()
        ElMessage.success('成功添加至购物车')
    })
}

//移出收藏
const deleteCollect = (collectId) => {
    delCollect(collectId).then(res => {
        getMyCollectList()
        ElMessage.success('已移出收藏')
    })
}

//查询我的收藏列表
const getMyCollectList = () => {
    selectMyCollectDishes().then(res => {
        myCollectDishes.value = res.data
    })
}

onMounted(() => {
    getMyCollectList()
})

</script>

<style scoped>
/* 收藏页面主容器样式 */
.favorites-page {
    /* 内边距 */
    padding: 20px;
    /* 最大宽度限制，避免在大屏幕上过宽 */
    max-width: 1400px;
    /* 水平居中 */
    margin: 0 auto;
}

/* 菜品列样式 */
.dish-col {
    /* 底部外边距，创建行间距 */
    margin-bottom: 20px;
}

/* 菜品卡片样式 */
.dish-card {
    /* 圆角边框 */
    border-radius: 10px;
    /* 隐藏溢出内容，确保圆角效果 */
    overflow: hidden;
    /* 过渡动画效果，所有属性变化在0.3秒内缓动完成 */
    transition: all 0.3s ease;
}

/* 卡片悬停效果 */
.dish-card:hover {
    /* 向上移动5像素，产生悬浮效果 */
    transform: translateY(-5px);
    /* 增加阴影，增强立体感 */
    box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

/* 菜品图片容器样式 */
.dish-image-container {
    /* 相对定位，为可能的绝对定位子元素提供参考 */
    position: relative;
    /* 固定高度 */
    height: 200px;
    /* 隐藏溢出内容 */
    overflow: hidden;
}

/* 菜品图片样式 */
.dish-image {
    /* 宽度100%填充容器 */
    width: 100%;
    /* 高度100%填充容器 */
    height: 100%;
    /* 保持图片比例的同时填充整个容器，可能会裁剪部分内容 */
    object-fit: cover;
}

/* 菜品信息区域样式 */
.dish-info {
    /* 内边距 */
    padding: 15px;
}

/* 菜品头部样式（名称和收藏按钮） */
.dish-header {
    /* 弹性布局，两端对齐 */
    display: flex;
    justify-content: space-between;
    /* 垂直居中对齐 */
    align-items: center;
    /* 底部外边距 */
    margin-bottom: 10px;
}

/* 菜品名称样式 */
.dish-header h3 {
    /* 移除默认外边距 */
    margin: 0;
    /* 字体大小 */
    font-size: 18px;
    /* 字体颜色 */
    color: #333;
}

/* 菜品描述样式 */
.dish-description {
    /* 灰色文字 */
    color: #666;
    /* 较小字体大小 */
    font-size: 14px;
    /* 底部外边距 */
    margin-bottom: 15px;
    /* 最小高度，保持布局一致性 */
    min-height: 40px;
}

/* 菜品底部样式（价格和按钮） */
.dish-footer {
    /* 弹性布局，两端对齐 */
    display: flex;
    justify-content: space-between;
    /* 垂直居中对齐 */
    align-items: center;
}

/* 菜品价格样式 */
.dish-price {
    /* 较大字体 */
    font-size: 20px;
    /* 粗体 */
    font-weight: bold;
    /* 红色系价格颜色 */
    color: #f56c6c;
}

/* 无收藏内容时的样式 */
.no-favorites {
    /* 文本居中 */
    text-align: center;
    /* 上下内边距 */
    padding: 50px 0;
}
</style>
