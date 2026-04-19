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
          <h3 class="title">商家登录</h3>
          <p class="subtitle">请输入您的商家账号信息</p>
        </div>

        <el-form ref="loginRef" :model="loginForm" :rules="loginRules" class="form-content">
          <el-form-item prop="username">
            <el-input
                v-model="loginForm.username"
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
                v-model="loginForm.password"
                type="password"
                size="large"
                auto-complete="off"
                placeholder="请输入密码"
                @keyup.enter="handleLogin"
            >
              <template #prefix>
                <i class="el-icon-lock el-input__icon input-icon"></i>
              </template>
            </el-input>
          </el-form-item>

          <el-form-item prop="code" v-if="captchaEnabled">
            <el-row :gutter="15">
              <el-col :span="14">
                <el-input
                    v-model="loginForm.code"
                    size="large"
                    auto-complete="off"
                    placeholder="请输入验证码"
                    @keyup.enter="handleLogin"
                >
                  <template #prefix>
                    <i class="el-icon-check el-input__icon input-icon"></i>
                  </template>
                </el-input>
              </el-col>
              <el-col :span="10">
                <div class="login-code">
                  <img :src="codeUrl" @click="getCode" class="login-code-img" alt="验证码"/>
                </div>
              </el-col>
            </el-row>
          </el-form-item>

          <el-form-item>
            <el-checkbox v-model="loginForm.rememberMe">记住密码</el-checkbox>
          </el-form-item>

          <el-form-item style="width:100%;">
            <el-button
                :loading="loading"
                size="large"
                type="primary"
                style="width:100%;"
                @click.prevent="handleLogin"
            >
              {{ loading ? '登录中...' : '登录' }}
            </el-button>
          </el-form-item>

          <div class="register-link">
            还没有账户？
            <router-link class="link-type" to="/register">立即注册</router-link>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import { getCodeImg, login } from "@/api/login"
import Cookies from "js-cookie"
import { encrypt, decrypt } from "@/utils/jsencrypt"
import store from '@/store'

export default {
  name: "Login",
  data() {
    return {
      title: "商家窗口管理系统",
      loginForm: {
        username: "",
        password: "",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [{ required: true, message: "请输入账号", trigger: "blur" }],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
        code: [{ required: true, message: "请输入验证码", trigger: "blur" }]
      },
      codeUrl: "",
      loading: false,
      captchaEnabled: true
    }
  },
  created() {
    this.getCode()
    this.getCookie()
  },
  methods: {
    async handleLogin() {
      // 1. 表单校验
      const valid = await this.$refs.loginRef.validate().catch(()=>{})
      if (!valid) return

      // 2. 防止重复点击
      if (this.loading) return
      this.loading = true

      const params = {
        username: this.loginForm.username,
        password: encrypt(this.loginForm.password),
        code: this.loginForm.code,
        uuid: this.loginForm.uuid
      }

      console.log('【登录提交参数】', params)

      try {
        // 3. 调用登录接口
        const res = await store.dispatch("user/login", params)
        console.log('【登录接口返回成功】', res)

        this.$message.success({
          message: '登录成功，正在进入系统',
          duration: 1500, // 1.5秒自动关闭
          customClass: 'no-block-nav'
        })

        // 4. 强制兜底跳转（hash路由100%生效）
        setTimeout(()=>{
          window.location.replace('/#/home')
        }, 600)

      } catch (err) {
        // 5. 任何报错都会进入这里，关闭loading，绝对不会卡死
        console.error('【登录失败详情】', err)
        this.$message.error('登录失败：账号/密码/验证码错误 或 后端未启动')

        // 失败刷新验证码
        if(this.captchaEnabled) this.getCode()

      } finally {
        // ✅ 【核心兜底】无论成功/失败，一定会关闭loading
        this.loading = false
      }
    },

    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.captchaEnabled !== false
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img
          this.loginForm.uuid = res.uuid
        }
      }).catch(()=>{
        this.captchaEnabled = false
      })
    },

    getCookie() {
      const username = Cookies.get("username")
      const password = Cookies.get("password")
      const rememberMe = Cookies.get("rememberMe")
      if(rememberMe) {
        this.loginForm.username = username || ''
        this.loginForm.password = decrypt(password || '') || ''
        this.loginForm.rememberMe = true
      }
    }
  }
}
</script>

<style lang='scss' scoped>
.login-container {
  display: flex;align-items: center;justify-content: center;height: 100vh;
  background:linear-gradient(135deg,#226644,#2c3e50);
}
.login-box {
  display:flex;width:900px;height:500px;background:rgba(255,255,255,0.95);
  border-radius:10px;overflow:hidden;box-shadow:0 15px 35px rgba(0,0,0,0.15);
}
.login-banner {
  flex:1;background:rgba(34,102,68,0.8);color:#fff;display:flex;align-items:center;justify-content:center;
}
.banner-content { z-index:9;text-align:center; }
.banner-title { font-size:32px;font-weight:600;margin-bottom:15px; }
.banner-subtitle { opacity:0.9; }
.login-form {
  flex:1;padding:40px;display:flex;flex-direction:column;justify-content:center;
}
.form-header { text-align:center;margin-bottom:30px; }
.title { font-size:28px;font-weight:600;color:#333; }
.subtitle { color:#999;font-size:14px; }
.form-content :deep(.el-form-item) { margin-bottom:22px; }
.form-content :deep(.el-input__inner) { height:46px;padding-left:40px;border-radius:4px; }
.login-code {
  width:100%;height:46px;display:flex;align-items:center;justify-content:center;background:#f5f5f5;border-radius:4px;cursor:pointer;
}
.register-link { text-align:center;margin-top:20px;color:#666; }
.link-type { color:#226644;text-decoration:none;margin-left:5px; }
.link-type:hover { text-decoration:underline; }
@media (max-width:950px){
  .login-box { width:90%; }
  .login-banner { display:none; }
}
</style>