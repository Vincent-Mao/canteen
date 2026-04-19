<template>
  <div class="password-page">
    <el-card shadow="hover">
      <el-form
          ref="passwordFormRef"
          :model="passwordForm"
          :rules="passwordRules"
          label-width="100px"
          class="password-form"
      >
        <el-form-item label="原密码" prop="oldPassword">
          <el-input
              v-model="passwordForm.oldPassword"
              type="password"
              placeholder="请输入原密码"
              show-password
          ></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input
              v-model="passwordForm.newPassword"
              type="password"
              placeholder="请输入新密码（不少于6位）"
              show-password
          ></el-input>
        </el-form-item>
        <el-form-item label="确认新密码" prop="confirmPassword">
          <el-input
              v-model="passwordForm.confirmPassword"
              type="password"
              placeholder="请再次输入新密码"
              show-password
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
              type="primary"
              @click="submitPassword"
              :loading="loading"
          >
            确认修改
          </el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { updatePassword } from '@/api/profile'

// 加载状态
const loading = ref(false)
// 表单引用
const passwordFormRef = ref(null)
// 表单数据
const passwordForm = ref({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

// 表单校验规则
const passwordRules = ref({
  oldPassword: [
    { required: true, message: '请输入原密码', trigger: 'blur' }
  ],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, message: '新密码长度不少于6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认新密码', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== passwordForm.value.newPassword) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
})

// 提交密码修改
const submitPassword = async () => {
  try {
    // 表单校验
    await passwordFormRef.value.validate()
    loading.value = true
    // 调用修改密码接口
    await updatePassword(passwordForm.value)
    ElMessage.success('密码修改成功，请重新登录')
    // 重置表单
    resetForm()
    // 这里可添加退出登录逻辑（可选）
    // const userStore = useUserStore()
    // userStore.logout()
    // router.push('/login')
  } catch (error) {
    ElMessage.error(error.msg || '密码修改失败')
  } finally {
    loading.value = false
  }
}

// 重置表单
const resetForm = () => {
  passwordFormRef.value?.resetFields()
  passwordForm.value = {
    oldPassword: '',
    newPassword: '',
    confirmPassword: ''
  }
}
</script>

<style scoped>
.password-page {
  padding: 20px;
}

.password-form {
  max-width: 500px;
  margin: 0 auto;
  padding-top: 20px;
}
</style>