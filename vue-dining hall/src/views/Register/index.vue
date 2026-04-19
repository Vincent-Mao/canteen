<template>
  <div class="register-page">
    <div class="register-card">
      <h2 class="register-title">商家窗口端注册</h2>
      <el-form
          ref="registerFormRef"
          :model="registerForm"
          :rules="registerRules"
          label-width="80px"
          class="register-form"
      >
        <el-form-item label="账号" prop="username">
          <el-input
              v-model="registerForm.username"
              placeholder="请设置商家账号"
              size="large"
          ></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
              v-model="registerForm.password"
              type="password"
              placeholder="请设置密码（不少于6位）"
              size="large"
              show-password
          ></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input
              v-model="registerForm.confirmPassword"
              type="password"
              placeholder="请再次输入密码"
              size="large"
              show-password
          ></el-input>
        </el-form-item>
        <el-form-item label="店铺名称" prop="shopName">
          <el-input
              v-model="registerForm.shopName"
              placeholder="请输入窗口名称"
              size="large"
          ></el-input>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input
              v-model="registerForm.phone"
              placeholder="请输入联系电话"
              size="large"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
              type="primary"
              size="large"
              class="register-btn"
              @click="handleRegister"
              :loading="loading"
          >
            注册
          </el-button>
          <el-button
              type="text"
              size="large"
              @click="$router.push('/login')"
          >
            已有账号？去登录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { register as registerApi } from '@/api/login'

const router = useRouter()
const loading = ref(false)
const registerFormRef = ref(null)

// 注册表单数据
const registerForm = ref({
  username: '',
  password: '',
  confirmPassword: '',
  shopName: '',
  phone: ''
})

// 表单校验规则
const registerRules = ref({
  username: [{ required: true, message: '请设置商家账号', trigger: 'blur' }],
  password: [
    { required: true, message: '请设置密码', trigger: 'blur' },
    { min: 6, message: '密码长度不少于6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== registerForm.value.password) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ],
  shopName: [{ required: true, message: '请输入窗口名称', trigger: 'blur' }],
  phone: [
    { required: true, message: '请输入联系电话', trigger: 'blur' },
    {
      pattern: /^1[3-9]\d{9}$/,
      message: '请输入正确的手机号',
      trigger: 'blur'
    }
  ]
})

// 注册逻辑
const handleRegister = async () => {
  try {
    await registerFormRef.value.validate()
    loading.value = true
    // 调用注册接口
    await registerApi(registerForm.value)
    ElMessage.success('注册成功！请登录')
    router.push('/login') // 注册成功跳转到登录页
  } catch (error) {
    ElMessage.error(error.msg || '注册失败，请稍后重试')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.register-page {
  height: 100%;
  background-color: #f5f7fa;
  display: flex;
  justify-content: center;
  align-items: center;
}

.register-card {
  width: 500px;
  padding: 30px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.register-title {
  text-align: center;
  margin-bottom: 20px;
  color: #303133;
}

.register-form {
  margin-top: 20px;
}

.register-btn {
  width: 100%;
  margin-bottom: 10px;
}
</style>