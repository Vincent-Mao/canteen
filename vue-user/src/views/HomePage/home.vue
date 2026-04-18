<template>
    <div class="home-page">
        <!-- 轮播图 -->
        <div class="recommend-section">
            <el-carousel height="500px" indicator-position="outside">
                <el-carousel-item v-for="banner in bannerList"
                                  :key="banner.bannerId">
                    <div class="carousel-item">
                        <img :src="baseUrl + banner.image" alt="">
                        <div class="carousel-info">
                            <h3>{{ banner.name }}</h3>
                            <p>{{ banner.description }}</p>
                            <div class="price">¥{{ banner.price }}</div>
                        </div>
                    </div>
                </el-carousel-item>
            </el-carousel>
        </div>

        <!-- 菜品分类 -->
        <div class="categoryList-section">
            <el-row :gutter="20">
                <el-col :span="4" v-for="category in categoryList" :key="category.categoryId">
                    <div class="category-card" @click="goToCategory(category.categoryId)">
                        <img :src="baseUrl + category.image" alt="">
                        <p>{{ category.name }}</p>
                    </div>
                </el-col>
            </el-row>
        </div>

    </div>
</template>

<script setup>
import {onMounted, ref} from 'vue'
import {listBanner} from "@/api/canteen/banner.js";
import {listCategory} from "@/api/canteen/category.js";
import {useRouter} from "vue-router";

//路由实例
const router = useRouter()

//跳转到指定分类查询
const goToCategory = (categoryId) => {
    router.push(`/index/menu?categoryId=${categoryId}`)
}

//基础URL
const baseUrl = import.meta.env.VITE_APP_BASE_API

//轮播图数据
const bannerList = ref([])

//菜品分类数据
const categoryList = ref([])

onMounted(() => {
    listBanner().then(res => {
        bannerList.value = res.rows
    })

    listCategory().then(res => {
        categoryList.value = res.rows
    })
})

</script>

<style scoped>
/* 首页整体容器样式 */
.home-page {
    /* 设置最大宽度为1300px，在大屏幕上不会无限扩展 */
    max-width: 1300px;
    /* 水平居中显示 */
    margin: 0 auto;
    /* 内边距20px，使内容与容器边缘保持距离 */
    padding: 20px;
}

/* 推荐菜品区域样式 */
.recommend-section {
    /* 底部外边距30px，与下方内容保持距离 */
    margin-bottom: 30px;
}

/* 轮播图单个项目样式 */
.carousel-item {
    /* 相对定位，为内部绝对定位元素提供参考 */
    position: relative;
    /* 固定高度500px，与轮播图组件高度一致 */
    height: 500px;
    /* 圆角边框，使图片边角更柔和 */
    border-radius: 10px;
    /* 溢出隐藏，确保圆角效果正常显示 */
    overflow: hidden;
}

/* 轮播图图片样式 */
.carousel-item img {
    /* 宽度100%，填满容器 */
    width: 100%;
    /* 高度100%，填满容器 */
    height: 100%;
    /* 保持图片比例，裁剪多余部分以填满容器 */
    object-fit: cover;
}

/* 轮播图信息覆盖层样式 */
.carousel-info {
    /* 绝对定位，相对于.carousel-item定位 */
    position: absolute;
    /* 定位到底部 */
    bottom: 0;
    /* 定位到左侧 */
    left: 0;
    /* 定位到右侧 */
    right: 0;
    /* 半透明黑色背景，提高文字可读性 */
    background: rgba(0, 0, 0, 0.6);
    /* 文字颜色为白色 */
    color: white;
    /* 内边距20px，使文字与背景边缘保持距离 */
    padding: 20px;
}

/* 轮播图标题样式 */
.carousel-info h3 {
    /* 字体大小24px */
    font-size: 24px;
    /* 底部外边距10px，与下方元素保持距离 */
    margin-bottom: 10px;
}

/* 轮播图描述文字样式 */
.carousel-info p {
    /* 字体大小16px */
    font-size: 16px;
    /* 底部外边距10px，与下方元素保持距离 */
    margin-bottom: 10px;
}

/* 轮播图价格样式 */
.price {
    /* 字体大小20px */
    font-size: 20px;
    /* 字体加粗 */
    font-weight: bold;
    /* 金色文字，突出显示价格 */
    color: #FFD700;
}

/* 菜品分类区域样式 */
.categoryList-section {
    /* 底部外边距30px，与下方内容保持距离 */
    margin-bottom: 30px;
}

/* 单个分类卡片样式 */
.category-card {
    /* 白色背景 */
    background: white;
    /* 圆角边框 */
    border-radius: 10px;
    /* 内边距15px */
    padding: 15px;
    /* 文字居中显示 */
    text-align: center;
    /* 鼠标指针变为手型，表示可点击 */
    cursor: pointer;
    /* 所有样式变化使用0.3秒缓动效果 */
    transition: all 0.3s ease;
    /* 轻微阴影，增加层次感 */
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}

/* 分类卡片悬停效果 */
.category-card:hover {
    /* 向上移动5px，产生悬浮效果 */
    transform: translateY(-5px);
    /* 加深阴影，增强悬浮感 */
    box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

/* 分类卡片图片样式 */
.category-card img {
    /* 宽度100%，填满容器 */
    width: 100%;
    /* 固定高度100px */
    height: 100px;
    /* 保持图片比例，裁剪多余部分以填满容器 */
    object-fit: cover;
    /* 圆角边框 */
    border-radius: 8px;
    /* 底部外边距10px，与文字保持距离 */
    margin-bottom: 10px;
}

/* 分类卡片文字样式 */
.category-card p {
    /* 字体大小16px */
    font-size: 16px;
    /* 深灰色文字 */
    color: #333;
    /* 中等字重 */
    font-weight: 500;
}

/* 营业时间标题样式 */
.business-hours h3 {
    /* 字体大小20px */
    font-size: 20px;
    /* 底部外边距15px */
    margin-bottom: 15px;
    /* 深灰色文字 */
    color: #333;
    /* 底部边框，浅灰色 */
    border-bottom: 1px solid #eee;
    /* 底部内边距10px，与边框保持距离 */
    padding-bottom: 10px;
}

/* 公告标题样式 */
.announcements h3 {
    /* 字体大小20px */
    font-size: 20px;
    /* 底部外边距15px */
    margin-bottom: 15px;
    /* 深灰色文字 */
    color: #333;
    /* 底部边框，浅灰色 */
    border-bottom: 1px solid #eee;
    /* 底部内边距10px，与边框保持距离 */
    padding-bottom: 10px;
}

/* 营业时间列表样式 */
.business-hours ul {
    /* 移除默认列表样式 */
    list-style: none;
    /* 移除默认内边距 */
    padding: 0;
}

/* 公告列表样式 */
.announcements ul {
    /* 移除默认列表样式 */
    list-style: none;
    /* 移除默认内边距 */
    padding: 0;
}

/* 营业时间列表项样式 */
.business-hours ul li {
    /* 上下内边距8px */
    padding: 8px 0;
    /* 弹性布局 */
    display: flex;
    /* 两端对齐，使内容分布在两侧 */
    justify-content: space-between;
}

/* 公告列表项样式 */
.announcements ul li {
    /* 上下内边距8px */
    padding: 8px 0;
    /* 弹性布局 */
    display: flex;
    /* 两端对齐，使内容分布在两侧 */
    justify-content: space-between;
    /* 鼠标指针变为手型，表示可点击 */
    cursor: pointer;
}

/* 公告列表项悬停效果 */
.announcements ul li:hover {
    /* 悬停时背景色变为浅灰色 */
    background-color: #f5f7fa;
}
</style>
