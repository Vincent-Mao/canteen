<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-banner">
        <div class="banner-content">
          <h2 class="banner-title">{{ title }}</h2>
          <p class="banner-subtitle">商家管理系统 · 高效便捷的运营工具</p>
        </div>
      </div>

      <div class="login-form">
        <div class="form-header">
          <h3 class="title">商家注册</h3>
          <p class="subtitle">请填写您的商家信息完成注册</p>
        </div>

        <el-form ref="registerRef" :model="registerForm" :rules="registerRules" class="form-content">
          <el-form-item prop="username">
            <el-input
                v-model="registerForm.username"
                type="text"
                size="large"
                auto-complete="off"
                placeholder="请输入商家账号"
            >
              <template #prefix>
                <i class="el-icon-user el-input__icon input-icon"></i>
              </template>
            </el-input>
          </el-form-item>

          <el-form-item prop="password">
            <el-input
                v-model="registerForm.password"
                type="password"
                size="large"
                auto-complete="off"
                placeholder="请输入密码"
            >
              <template #prefix>
                <i class="el-icon-lock el-input__icon input-icon"></i>
              </template>
            </el-input>
          </el-form-item>

          <el-form-item prop="confirmPassword">
            <el-input
                v-model="registerForm.confirmPassword"
                type="password"
                size="large"
                auto-complete="off"
                placeholder="请确认密码"
            >
              <template #prefix>
                <i class="el-icon-lock el-input__icon input-icon"></i>
              </template>
            </el-input>
          </el-form-item>

          <el-form-item prop="shopName">
            <el-input
                v-model="registerForm.shopName"
                type="text"
                size="large"
                auto-complete="off"
                placeholder="请输入商家名称"
            >
              <template #prefix>
                <i class="el-icon-shop el-input__icon input-icon"></i>
              </template>
            </el-input>
          </el-form-item>

          <el-form-item prop="address">
            <el-input
                v-model="registerForm.address"
                type="text"
                size="large"
                auto-complete="off"
                placeholder="请输入窗口位置"
            >
              <template #prefix>
                <i class="el-icon-location el-input__icon input-icon"></i>
              </template>
            </el-input>
          </el-form-item>

          <!-- 注册按钮 -->
          <el-form-item style="width:100%;">
            <el-button
                :loading="loading"
                size="large"
                type="primary"
                style="width:100%;"
                @click.prevent="submitRegister"
            >
              {{ loading ? '注册中...' : '注册' }}
            </el-button>
          </el-form-item>

          <!-- 成功后显示：去登录按钮 -->
          <div class="register-link" v-if="success">
            <el-button type="success" plain style="width:100%" @click="goToLogin">
              ✅ 注册成功！点击前往登录
            </el-button>
          </div>

          <!-- 平时显示：去登录链接 -->
          <div class="register-link" v-else>
            已有账户？
            <router-link class="link-type" to="/login">立即登录</router-link>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import { register } from "@/api/login"
import { ElMessage } from 'element-ui'

export default {
  name: "Register",
  data() {
    const validateConfirmPassword = (rule, value, callback) => {
      if (value !== this.registerForm.password) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    }

    return {
      title: "商家窗口管理系统",
      registerForm: {
        username: "",
        password: "",
        confirmPassword: "",
        shopName: "",
        address: ""
      },
      registerRules: {
        username: [{ required: true, trigger: "blur", message: "请输入商家账号" }],
        password: [{ required: true, trigger: "blur", message: "请输入密码" }],
        confirmPassword: [
          { required: true, trigger: "blur", message: "请确认密码" },
          { validator: validateConfirmPassword, trigger: "blur" }
        ],
        shopName: [{ required: true, trigger: "blur", message: "请输入商家名称" }],
        address: [{ required: true, trigger: "blur", message: "请输入窗口位置" }]
      },
      loading: false,
      success: false // 注册成功标记
    }
  },
  methods: {
    // 提交注册
    submitRegister() {
      this.$refs.registerRef.validate(valid => {
        if (!valid) {
          ElMessage.warning("请完成表单填写")
          return
        }

        this.loading = true
        this.success = false

        const params = {
          username: this.registerForm.username,
          password: this.registerForm.password,
          shopName: this.registerForm.shopName,
          address: this.registerForm.address
        }

        console.log("提交注册参数：", params)

        // 调用接口
        register(params).then(res => {
          console.log("注册成功返回：", res)
          this.loading = false
          this.success = true // 显示成功按钮
          ElMessage.success("注册成功！请点击下方按钮前往登录")
        }).catch(err => {
          console.error("注册失败：", err)
          this.loading = false
          ElMessage.error("注册失败：" + (err.message || "后端服务未启动或接口异常"))
        })
      })
    },

    // 前往登录（绝对稳定跳转）
    goToLogin() {
      window.location.href = "/#/login"
    }
  }
}
</script>

<style lang='scss' scoped>
.login-container {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background: linear-gradient(135deg, #226644 0%, #2c3e50 100%);
}

.login-box {
  display: flex;
  width: 900px;
  height: 550px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 10px;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
  overflow: hidden;
}

.login-banner {
  flex: 1;
  background-color: rgba(34, 102, 68, 0.8);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
}

.banner-content {
  position: relative;
  z-index: 1;
  text-align: center;
}

.banner-title {
  font-size: 32px;
  font-weight: 600;
  margin-bottom: 15px;
}

.banner-subtitle {
  font-size: 16px;
  opacity: 0.9;
}

.login-form {
  flex: 1;
  padding: 40px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.form-header {
  text-align: center;
  margin-bottom: 20px;
}

.title {
  font-size: 28px;
  font-weight: 600;
  color: #333;
}

.subtitle {
  color: #999;
}

.form-content :deep(.el-form-item) {
  margin-bottom: 18px;
}

.form-content :deep(.el-input__inner) {
  height: 46px;
  line-height: 46px;
  padding-left: 40px;
  border-radius: 4px;
  border: 1px solid #e6e6e6;
}

.register-link {
  text-align: center;
  margin-top: 10px;
  color: #666;
}

.link-type {
  color: #226644;
  text-decoration: none;
}

@media (max-width: 950px) {
  .login-box {
    width: 90%;
  }
  .login-banner {
    display: none;
  }
}
</style>