<template>
  <div class="register-page">
    <div class="register-form">
      <h2>商家注册</h2>
      <el-form :model="registerForm" :rules="registerRules" ref="registerForm" label-width="80px">
        <el-form-item label="账号" prop="username">
          <el-input v-model="registerForm.username" placeholder="请输入商家账号"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="registerForm.password" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="registerForm.confirmPassword" type="password" placeholder="请确认密码"></el-input>
        </el-form-item>
        <el-form-item label="商家名称" prop="name">
          <el-input v-model="registerForm.name" placeholder="请输入商家名称"></el-input>
        </el-form-item>
        <el-form-item label="窗口位置" prop="windowPosition">
          <el-input v-model="registerForm.windowPosition" placeholder="请输入窗口位置（如：一楼101）"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleRegister">注册</el-button>
          <el-button type="text" @click="$router.push('/login')">返回登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import api from '@/api'
export default {
  name: 'Register',
  data() {
    // 自定义校验：确认密码
    const validateConfirmPassword = (rule, value, callback) => {
      if (value !== this.registerForm.password) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    }
    return {
      registerForm: {
        username: '',
        password: '',
        confirmPassword: '',
        name: '',
        windowPosition: ''
      },
      registerRules: {
        username: [
          { required: true, message: '请输入商家账号', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请确认密码', trigger: 'blur' },
          { validator: validateConfirmPassword, trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入商家名称', trigger: 'blur' }
        ],
        windowPosition: [
          { required: true, message: '请输入窗口位置', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    handleRegister() {
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          // 剔除确认密码字段
          const { confirmPassword, ...params } = this.registerForm
          api.loginApi.register(params).then(() => {
            this.$message.success('注册成功，请登录')
            this.$router.push('/login')
          }).catch(err => {
            this.$message.error('注册失败：' + (err.response?.data?.msg || '服务器错误'))
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.register-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: #f5f5f5;
}
.register-form {
  width: 450px;
  padding: 30px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}
.register-form h2 {
  text-align: center;
  margin-bottom: 20px;
  color: #333;
}
</style>