<template>
  <el-upload
      class="avatar-uploader"
      :action="uploadUrl"
      :show-file-list="false"
      :on-success="handleSuccess"
      :before-upload="beforeUpload"
      :disabled="disabled"
      :headers="headers"
  >
    <!-- 已上传图片预览 -->
    <img v-if="modelValue" :src="modelValue" class="avatar" />
    <!-- 未上传时的占位 -->
    <div v-else class="upload-placeholder">
      <el-icon class="el-icon--plus"><Plus /></el-icon>
      <div class="el-upload__text">点击上传</div>
    </div>
  </el-upload>
</template>

<script setup>
import { ref, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores/userStore'

// 接收父组件参数
const props = defineProps({
  // 已上传的图片地址（支持v-model双向绑定）
  modelValue: {
    type: String,
    default: ''
  },
  // 是否禁用上传
  disabled: {
    type: Boolean,
    default: false
  },
  // 自定义上传接口地址（默认读取环境变量）
  uploadUrl: {
    type: String,
    default: import.meta.env.VITE_API_BASE_URL + '/upload/image'
  }
})

// 向父组件传值（实现v-model）
const emit = defineEmits(['update:modelValue'])

// 获取用户token（请求头携带鉴权）
const userStore = useUserStore()
const headers = computed(() => ({
  Authorization: `Bearer ${userStore.token}`
}))

// 上传前校验（格式+大小）
const beforeUpload = (file) => {
  // 校验图片格式
  const isImage = file.type.startsWith('image/')
  if (!isImage) {
    ElMessage.error('请上传jpg/png/jpeg格式的图片！')
    return false
  }
  // 校验图片大小（2MB以内）
  const isLt2M = file.size / 1024 / 1024 < 2
  if (!isLt2M) {
    ElMessage.error('图片大小不能超过2MB！')
    return false
  }
  return true
}

// 上传成功回调
const handleSuccess = (res) => {
  if (res.code === 200) {
    // 把上传后的图片地址返回给父组件
    emit('update:modelValue', res.data.url)
    ElMessage.success('图片上传成功！')
  } else {
    ElMessage.error(res.msg || '图片上传失败，请重试')
  }
}
</script>

<style scoped>
/* 上传容器样式 */
.avatar-uploader {
  width: 150px;
  height: 150px;
  border: 1px dashed #d9d9d9;
  border-radius: 8px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  background-color: #fafafa;
}

/* 已上传图片样式 */
.avatar {
  width: 100%;
  height: 100%;
  display: block;
  object-fit: cover;
}

/* 未上传占位样式 */
.upload-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: #999;
}

.el-icon--plus {
  font-size: 28px;
  margin-bottom: 8px;
}

.el-upload__text {
  font-size: 14px;
}

/* 禁用状态样式 */
:deep(.avatar-uploader.is-disabled) {
  cursor: not-allowed;
  opacity: 0.6;
}
</style>