<template>
  <div class="page">
    <div class="box">
      <div class="banner"><h2>商家登录</h2></div>
      <div class="form-box">
        <h3>账号登录</h3>
        <el-form ref="refForm" :model="form" :rules="rules">
          <el-form-item prop="username"><el-input v-model="form.username" placeholder="账号" /></el-form-item>
          <el-form-item prop="password"><el-input v-model="form.password" type="password" placeholder="密码" /></el-form-item>
          <el-button type="primary" block @click="onLogin">登录</el-button>
          <div class="tac mt10">
            <el-button link @click="$router.push('/dining/register')">注册</el-button>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const store = useStore()
const router = useRouter()
const refForm = ref(null)
const form = reactive({ username: '', password: '' })
const rules = {
  username: [{ required: true, message: '请输入账号' }],
  password: [{ required: true, message: '请输入密码' }]
}

const onLogin = async () => {
  await refForm.value.validate()
  await store.dispatch('dining/login', form)
  await store.dispatch('dining/getInfo')
  ElMessage.success('登录成功')
  router.push('/dining/index')
}
</script>

<style lang="scss" scoped>
.page{height:100vh;display:flex;align-items:center;justify-content:center;background:#f5f7fa;}
.box{width:900px;height:500px;background:#fff;border-radius:8px;display:flex;box-shadow:0 0 20px #ccc;}
.banner{flex:1;background:#409eff;color:#fff;display:flex;align-items:center;justify-content:center;font-size:24px;font-weight:bold;}
.form-box{flex:1;padding:40px;display:flex;flex-direction:column;justify-content:center;}
.tac{text-align:center;}.mt10{margin-top:10px;}
</style>