<template>
    <div class="menu-page">
        <!-- 搜索和筛选区域 -->
        <div class="menu-header">
            <div class="search-bar">
                <!-- 菜品搜索输入框 -->
                <el-input v-model="query.name"
                          placeholder="搜索菜品..."
                          clearable
                          @clear="getDishesList"
                          style="margin-right: 20px; width: 300px;"
                >
                    <!-- 搜索按钮 -->
                    <template #append>
                        <el-button icon="Search" @click="getDishesList"/>
                    </template>
                </el-input>

                <!-- 菜品分类选择器 -->
                <el-select v-model="query.categoryId" placeholder="请选择分类"
                           clearable @change="getDishesList"
                           style="margin-right: 20px; width: 260px;"
                >
                    <el-option
                            v-for="item in categoryList"
                            :key="item.categoryId"
                            :label="item.name"
                            :value="item.categoryId"
                    />
                </el-select>
            </div>
        </div>

        <!-- 菜品卡片列表 -->
        <div>
            <el-row :gutter="20">
                <el-col :span="6"
                        v-for="dish in dishesList"
                        :key="dish.dishesId"
                        class="dish-col"
                >
                    <!-- 菜品卡片 -->
                    <el-card class="dish-card">
                        <!-- 菜品图片 -->
                        <div class="dish-image-container">
                            <img :src="baseUrl + dish.image" alt="" class="dish-image">
                        </div>

                        <!-- 菜品信息 -->
                        <div style="padding: 15px">
                            <div class="dish-header">
                                <h3>{{ dish.name }}</h3>
                                <!-- 收藏按钮 -->
                                <el-button :icon="isCollect(dish.dishesId) ? 'Star' : 'StarFilled'"
                                           :type="isCollect(dish.dishesId) ? 'warning' : 'info'"
                                           circle size="small" @click="handleCollect(dish.dishesId)"/>
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

        <!-- 分页组件 -->
        <pagination
                class="menu-pagination"
                v-show="total>0"
                :total="total"
                v-model:page="query.pageNum"
                v-model:limit="query.pageSize"
                @pagination="getDishesList"
                :pageSizes="[8,16,32]"
        />

    </div>
</template>

<script setup>
import {onMounted, ref} from 'vue'
import {listDishes} from "@/api/canteen/dishes.js";
import {selectAllCategory} from "@/api/canteen/category.js";
import {addCollect, selectCollectIdByDishesIdAndUserId, selectMyCollectDishes} from "@/api/canteen/collect.js";
import {ElMessage} from "element-plus";
import {addCart} from "@/api/canteen/cart.js";
import {useRoute} from "vue-router";

//基础URL
const baseUrl = import.meta.env.VITE_APP_BASE_API

//添加到购物车
const addToCart = (dishesId) => {
    const item = {
        dishesId: dishesId
    }
    addCart(item).then(res => {
        getDishesList()
        ElMessage.success('成功添加至购物车')
    })
}

//查询参数
const query = ref({
    pageNum: 1,
    pageSize: 8,
    name: null,
    categoryId: null
})

//菜品列表数据
const dishesList = ref([])

//数据总数
const total = ref(0)

//查询菜品列表数据
const getDishesList = () => {
    listDishes(query.value).then(res => {
        dishesList.value = res.rows
        total.value = res.total
    })
}

//菜品分类数据
const categoryList = ref([])

//切换收藏状态
const handleCollect = (dishesId) => {
    const index = collectDishes.value.indexOf(dishesId)
    //如果已收藏, 变为未收藏; 如果未收藏, 变为已收藏
    if (index === -1) {
        const item = {
            dishesId: dishesId
        }
        addCollect(item).then(res => {
            collectDishes.value.push(dishesId)
            ElMessage.success('收藏成功~')
        })
    } else {
        selectCollectIdByDishesIdAndUserId(dishesId).then(res => {
            collectDishes.value.splice(index, 1)
            ElMessage.success('已取消收藏')
        })
    }
}

//判断菜品是否被收藏
const isCollect = (dishesId) => {
    return collectDishes.value.includes(dishesId)
}

//用户收藏的菜品ID列表
const collectDishes = ref([])

//查询用户收藏的菜品列表
const loadCollectDishes = () => {
    selectMyCollectDishes().then(res => {
        //将收藏列表转换为菜品ID数组
        collectDishes.value = res.data.map(item => item.dishesId)
    })
}

const route = useRoute()

//组件挂载后执行
onMounted(() => {
    //检查是否有分类ID参数
    if (route.query.categoryId) {
        query.value.categoryId = route.query.categoryId
    }

    //获取菜品列表
    getDishesList()

    //查询所有分类
    selectAllCategory().then(res => {
        //将分类列表数据赋值给变量
        categoryList.value = res.data
    })

    //加载用户收藏列表
    loadCollectDishes()
})

</script>

<style scoped>
/* 菜单页面主容器样式 */
.menu-page {
    padding: 20px; /* 内边距 */
    max-width: 1400px; /* 最大宽度 */
    margin: 0 auto; /* 水平居中 */
}

/* 菜单头部样式 */
.menu-header {
    display: flex; /* 弹性布局 */
    justify-content: space-between; /* 两端对齐 */
    align-items: center; /* 垂直居中 */
    margin-bottom: 20px; /* 底部外边距 */
    flex-wrap: wrap; /* 允许换行 */
    gap: 10px; /* 元素间距 */
}

/* 搜索栏样式 */
.search-bar {
    display: flex; /* 弹性布局 */
    align-items: center; /* 垂直居中 */
    flex-wrap: wrap; /* 允许换行 */
    gap: 10px; /* 元素间距 */
}

/* 菜品列样式 */
.dish-col {
    margin-bottom: 20px; /* 底部外边距 */
}

/* 菜品卡片样式 */
.dish-card {
    border-radius: 10px; /* 圆角边框 */
    overflow: hidden; /* 隐藏溢出内容 */
    transition: all 0.3s ease; /* 过渡动画效果 */
}

/* 菜品卡片悬停效果 */
.dish-card:hover {
    transform: translateY(-5px); /* 向上移动5像素 */
    box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15); /* 阴影效果 */
}

/* 菜品图片容器样式 */
.dish-image-container {
    position: relative; /* 相对定位 */
    height: 200px; /* 固定高度 */
    overflow: hidden; /* 隐藏溢出内容 */
}

/* 菜品图片样式 */
.dish-image {
    width: 100%; /* 宽度100% */
    height: 100%; /* 高度100% */
    object-fit: cover; /* 图片填充方式：覆盖 */
}

/* 菜品头部信息样式 */
.dish-header {
    display: flex; /* 弹性布局 */
    justify-content: space-between; /* 两端对齐 */
    align-items: center; /* 垂直居中 */
    margin-bottom: 10px; /* 底部外边距 */
}

/* 菜品名称样式 */
.dish-header h3 {
    margin: 0; /* 清除默认外边距 */
    font-size: 18px; /* 字体大小 */
    color: #333; /* 字体颜色 */
}

/* 菜品描述样式 */
.dish-description {
    color: #666; /* 字体颜色 */
    font-size: 14px; /* 字体大小 */
    margin-bottom: 15px; /* 底部外边距 */
    min-height: 40px; /* 最小高度 */
}

/* 菜品底部信息样式 */
.dish-footer {
    display: flex; /* 弹性布局 */
    justify-content: space-between; /* 两端对齐 */
    align-items: center; /* 垂直居中 */
    margin-bottom: 15px; /* 底部外边距 */
}

/* 菜品价格样式 */
.dish-price {
    font-size: 20px; /* 字体大小 */
    font-weight: bold; /* 字体粗细 */
    color: #f56c6c; /* 价格颜色（红色系） */
}

/* 分页组件容器样式 */
.menu-pagination {
    display: flex; /* 弹性布局 */
    justify-content: center; /* 水平居中 */
}
</style>
