<template>
    <div class="nutrition-page">
        <!-- 营养推荐 -->
        <el-row :gutter="20" class="section-row">
            <el-col :span="24">
                <el-card class="section-card">
                    <template #header>
                        <div class="card-header">
                            <el-icon>
                                <Apple/>
                            </el-icon>
                            <span>营养推荐</span>
                        </div>
                    </template>
                    <el-row :gutter="20">
                        <el-col :span="8" v-for="recommend in recommendList"
                                :key="recommend.recommendId">
                            <div class="recommend-item">
                                <el-image :src="baseUrl + recommend.image" class="recommend-image" fit="cover"/>
                                <div class="recommend-content">
                                    <h3>{{ recommend.title }}</h3>
                                    <p class="recommend-description">{{ recommend.description }}</p>
                                    <div class="recommend-meta">
                                        <span class="calories">热量: {{ recommend.calories }}kcal</span>
                                    </div>
                                </div>
                            </div>
                        </el-col>
                    </el-row>
                </el-card>
            </el-col>
        </el-row>

        <!-- 健康小贴士 -->
        <el-row :gutter="20" class="section-row">
            <el-col :span="24">
                <el-card class="section-card">
                    <template #header>
                        <div class="card-header">
                            <el-icon>
                                <Lightning/>
                            </el-icon>
                            <span>健康小贴士</span>
                        </div>
                    </template>
                    <div class="tips-content">
                        <el-row :gutter="20">
                            <el-col :span="8" v-for="tip in tipList" :key="tip.tipId">
                                <div class="tip-item">
                                    <el-icon class="tip-icon"><Check/></el-icon>
                                    <span class="tip-text">{{ tip.content }}</span>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </el-card>
            </el-col>
        </el-row>

    </div>
</template>

<script setup>
import {onMounted, ref} from 'vue'
import {Apple, Check, Lightning} from "@element-plus/icons-vue";
import {listRecommend} from "@/api/canteen/recommend.js";
import {listTip} from "@/api/canteen/tip.js";

const baseUrl = import.meta.env.VITE_APP_BASE_API

//营养推荐数据
const recommendList = ref([])

//健康小贴士数据
const tipList = ref([])

onMounted(() => {
    listRecommend().then(res => {
        recommendList.value = res.rows
    })
    listTip().then(res => {
        tipList.value = res.rows
    })
})

</script>

<style scoped>
/* 页面容器样式 */
.nutrition-page {
    /* 内边距 */
    padding: 20px;
    /* 最大宽度限制，在大屏幕上居中显示 */
    max-width: 1400px;
    /* 水平居中 */
    margin: 0 auto;
    /* 背景色 */
    background-color: #f5f5f5;
    /* 最小高度：视口高度减去120px，确保有足够的内容区域 */
    min-height: calc(100vh - 120px);
}

/* 区块卡片通用样式 */
.section-card {
    /* 底部外边距，卡片之间的间距 */
    margin-bottom: 20px;
    /* 圆角边框 */
    border-radius: 12px;
    /* 盒子阴影：水平偏移 垂直偏移 模糊半径 扩展半径 颜色 */
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    /* 移除边框 */
    border: none;
}

/* 卡片悬停效果 */
.section-card:hover {
    /* 加深阴影，增强悬停效果 */
    box-shadow: 0 4px 20px 0 rgba(0, 0, 0, 0.15);
}

/* 卡片头部样式 */
.card-header {
    /* 弹性布局，子元素垂直居中 */
    display: flex;
    align-items: center;
    /* 字体大小 */
    font-size: 20px;
    /* 字体粗细 */
    font-weight: 600;
    /* 字体颜色 */
    color: #333;
    /* 内边距 */
    padding: 10px 0;
}

/* 卡片头部图标样式 */
.card-header .el-icon {
    /* 图标右边距 */
    margin-right: 10px;
    /* 图标大小 */
    font-size: 22px;
    /* 主题绿色 */
    color: #67C23A;
}

/* ========== 营养推荐样式 ========== */
.recommend-item {
    /* 弹性布局，垂直方向排列 */
    display: flex;
    flex-direction: column;
    /* 高度100%填充父容器 */
    height: 100%;
    /* 边框样式 */
    border: 1px solid #e6e6e6;
    /* 圆角 */
    border-radius: 10px;
    /* 隐藏溢出内容 */
    overflow: hidden;
    /* 底部外边距 */
    margin-bottom: 20px;
    /* 白色背景 */
    background-color: white;
    /* 过渡动画：所有属性，0.3秒，缓动函数 */
    transition: all 0.3s ease;
}

/* 推荐项悬停效果 */
.recommend-item:hover {
    /* 增强阴影 */
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
    /* 向上移动5px */
    transform: translateY(-5px);
}

/* 推荐图片样式 */
.recommend-image {
    /* 固定高度 */
    height: 260px;
}

/* 推荐内容区域样式 */
.recommend-content {
    /* 内边距 */
    padding: 15px;
    /* 弹性扩展填充剩余空间 */
    flex: 1;
    /* 弹性布局，垂直排列 */
    display: flex;
    flex-direction: column;
}

/* 推荐标题样式 */
.recommend-content h3 {
    /* 清除默认边距 */
    margin: 0 0 10px 0;
    font-size: 16px;
    font-weight: 600;
    color: #333;
    /* 行高 */
    line-height: 1.3;
}

/* 推荐描述样式 */
.recommend-description {
    margin: 0 0 15px 0;
    font-size: 13px;
    color: #666;
    /* 弹性扩展，填充可用空间 */
    flex: 1;
    line-height: 1.5;
}

/* 推荐元信息区域 */
.recommend-meta {
    display: flex;
    /* 两端对齐 */
    justify-content: space-between;
    align-items: center;
}

/* 热量信息样式 */
.calories {
    font-size: 13px;
    color: #999;
    font-weight: 500;
}

/* ========== 健康小贴士样式 ========== */
.tips-content {
    /* 宽度100% */
    width: 100%;
}

/* 单个贴士项样式 */
.tip-item {
    display: flex;
    /* 顶部对齐 */
    align-items: flex-start;
    margin-bottom: 18px;
    font-size: 14px;
    color: #555;
    line-height: 1.5;
    /* 内边距 */
    padding: 8px 12px;
    border-radius: 6px;
    /* 过渡效果 */
    transition: all 0.2s;
}

/* 贴士项悬停效果 */
.tip-item:hover {
    /* 悬停时背景色变化 */
    background-color: #f9f9f9;
}

/* 贴士图标样式 */
.tip-icon {
    /* 主题绿色 */
    color: #67C23A;
    /* 右边距 */
    margin-right: 12px;
    /* 顶部外边距，微调垂直对齐 */
    margin-top: 2px;
    /* 防止图标被压缩 */
    flex-shrink: 0;
    font-size: 16px;
}

/* 贴士文本样式 */
.tip-text {
    /* 弹性扩展填充剩余空间 */
    flex: 1;
}

/* 区块行样式 */
.section-row {
    margin-bottom: 20px;
}
</style>
