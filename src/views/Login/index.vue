<template>
  <div class="login-page">
    <div class="login-card">
      <h2 class="login-title">商家窗口端登录</h2>
      <el-form
          ref="loginFormRef"
          :model="loginForm"
          :rules="loginRules"
          label-width="80px"
          class="login-form"
      >
        <el-form-item label="账号" prop="username">
          <el-input
              v-model="loginForm.username"
              placeholder="请输入商家账号"
              size="large"
          ></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
              v-model="loginForm.password"
              type="password"
              placeholder="请输入密码"
              size="large"
              show-password
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
              type="primary"
              size="large"
              class="login-btn"
              @click="handleLogin"
              :loading="loading"
          >
            登录
          </el-button>
          <el-button
              type="text"
              size="large"
              @click="$router.push('/register')"
          >
            还没有账号？去注册
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
import { useUserStore } from '@/stores/userStore'
import { login as loginApi } from '@/api/login'

const router = useRouter()
const userStore = useUserStore()
const loading = ref(false) // 登录按钮加载状态
const loginFormRef = ref(null)

// 登录表单数据
const loginForm = ref({
  username: '',
  password: ''
})

// 表单校验规则
const loginRules = ref({
  username: [{ required: true, message: '请输入商家账号', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
})

// 登录逻辑
const handleLogin = async () => {
  try {
    // 表单校验
    await loginFormRef.value.validate()
    loading.value = true
    // 调用登录接口
    const res = await loginApi(loginForm.value)
    // 存储用户信息和token
    userStore.login({
      token: res.data.token,
      userInfo: res.data.user
    })
    ElMessage.success('登录成功！')
    router.push('/dashboard/dish') // 登录后跳转到菜品管理页
  } catch (error) {
    ElMessage.error(error.msg || '登录失败，请检查账号密码')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-page {
  height: 100%;
  background-color: #f5f7fa;
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-card {
  width: 450px;
  padding: 30px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.login-title {
  text-align: center;
  margin-bottom: 20px;
  color: #303133;
}

.login-form {
  margin-top: 20px;
}

.login-btn {
  width: 100%;
  margin-bottom: 10px;
}
</style>