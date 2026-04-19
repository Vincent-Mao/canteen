<template>
  <div class="basic-info-page">
    <el-card shadow="hover">
      <el-form
          ref="infoFormRef"
          :model="infoForm"
          :rules="infoRules"
          label-width="100px"
          class="info-form"
      >
        <el-form-item label="店铺名称" prop="shopName">
          <el-input
              v-model="infoForm.shopName"
              placeholder="请输入店铺名称"
          ></el-input>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input
              v-model="infoForm.phone"
              placeholder="请输入联系电话"
          ></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input
              v-model="infoForm.email"
              placeholder="请输入邮箱（选填）"
              type="email"
          ></el-input>
        </el-form-item>
        <el-form-item label="商家头像" prop="avatar">
          <UploadImage
              :value="infoForm.avatar"
              @change="(val) => (infoForm.avatar = val)"
          />
        </el-form-item>
        <el-form-item label="店铺简介" prop="desc">
          <el-input
              v-model="infoForm.desc"
              type="textarea"
              placeholder="请输入店铺简介（选填）"
              rows="4"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
              type="primary"
              @click="submitInfo"
              :loading="loading"
          >
            保存修改
          </el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/userStore'
import { getProfile, updateProfile } from '@/api/profile'
import UploadImage from '@/components/UploadImage.vue'

// 加载状态
const loading = ref(false)
// 表单引用
const infoFormRef = ref(null)
// 用户状态管理
const userStore = useUserStore()
// 表单数据
const infoForm = ref({
  shopName: '',
  phone: '',
  email: '',
  avatar: '',
  desc: ''
})

// 表单校验规则
const infoRules = ref({
  shopName: [
    { required: true, message: '请输入店铺名称', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入联系电话', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  email: [
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
  ]
})

// 获取用户基本信息
const getInfo = async () => {
  try {
    loading.value = true
    const res = await getProfile()
    infoForm.value = { ...res.data }
  } catch (error) {
    ElMessage.error('获取个人信息失败')
  } finally {
    loading.value = false
  }
}

// 提交信息修改
const submitInfo = async () => {
  try {
    await infoFormRef.value.validate()
    loading.value = true
    await updateProfile(infoForm.value)
    ElMessage.success('个人信息修改成功')
    // 更新store中的用户信息
    userStore.updateUserInfo(infoForm.value)
  } catch (error) {
    ElMessage.error(error.msg || '修改失败，请稍后重试')
  } finally {
    loading.value = false
  }
}

// 重置表单
const resetForm = () => {
  infoFormRef.value?.resetFields()
  // 重置为原始数据
  getInfo()
}

// 页面加载时获取用户信息
onMounted(() => {
  getInfo()
})
</script>

<style scoped>
.basic-info-page {
  padding: 20px;
}

.info-form {
  max-width: 600px;
  margin: 0 auto;
  padding-top: 20px;
}
</style>