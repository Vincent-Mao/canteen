<template>
  <div class="empty-container" :style="{ height: height }">
    <div class="empty-content">
      <!-- 空数据图标 -->
      <el-icon class="empty-icon"><Picture /></el-icon>
      <!-- 自定义提示文本 -->
      <div class="empty-text">{{ text || '暂无数据' }}</div>
      <!-- 可选操作按钮 -->
      <div class="empty-action" v-if="showButton">
        <el-button type="primary" size="small" @click="handleClick">
          {{ buttonText || '刷新数据' }}
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { Picture } from '@element-plus/icons-vue'

// 接收父组件传参
const props = defineProps({
  // 占位容器高度（默认铺满父容器）
  height: {
    type: String,
    default: '100%'
  },
  // 空数据提示文本
  text: {
    type: String,
    default: ''
  },
  // 是否显示操作按钮
  showButton: {
    type: Boolean,
    default: false
  },
  // 按钮文本
  buttonText: {
    type: String,
    default: ''
  }
})

// 按钮点击事件（向父组件传值）
const emit = defineEmits(['click'])
const handleClick = () => {
  emit('click')
}
</script>

<style scoped>
.empty-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  background-color: #fff;
}

.empty-content {
  text-align: center;
  padding: 40px 20px;
}

.empty-icon {
  font-size: 60px;
  color: #c0c4cc;
  margin-bottom: 16px;
}

.empty-text {
  font-size: 14px;
  color: #909399;
  margin-bottom: 20px;
}
</style>