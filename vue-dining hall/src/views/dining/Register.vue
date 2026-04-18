<template>
  <div class="page">
    <div class="box">
      <div class="banner"><h2>商家注册</h2></div>
      <div class="form-box">
        <h3>账号注册</h3>
        <el-form ref="refForm" :model="form" :rules="rules">
          <el-form-item prop="username">
            <el-input v-model="form.username" placeholder="账号" />
          </el-form-item>

          <!-- 窗口位置 → 只提示，不强制 -->
          <el-form-item prop="windowLocation">
            <el-input
                v-model="form.windowLocation"
                placeholder="请填写：XX园XX楼XX窗口，例如：荷一二楼陕西手工扯面"
            />
          </el-form-item>

          <el-form-item prop="password">
            <el-input v-model="form.password" type="password" placeholder="密码" />
          </el-form-item>

          <el-button type="primary" block @click="onRegister">注册</el-button>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { register } from '@/api/dining'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()
const refForm = ref(null)
const form = reactive({
  username: '',
  windowLocation: '',
  password: ''
})

// ✅ 只保留非空校验，不再强制必须包含“园”“楼”
const rules = {
  username: [{ required: true, message: '请输入账号', trigger: 'blur' }],
  windowLocation: [
    { required: true, message: '请填写窗口信息，例如：荷一二楼哪个窗口', trigger: 'blur' }
  ],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const onRegister = async () => {
  await refForm.value.validate()
  await register(form)
  ElMessage.success('注册成功')
  router.push('/dining/login')
}
</script>

<style lang="scss" scoped>
.page{height:100vh;display:flex;align-items:center;justify-content:center;background:#f5f7fa;}
.box{width:900px;height:550px;background:#fff;border-radius:8px;display:flex;box-shadow:0 0 20px #ccc;}
.banner{flex:1;background:#409eff;color:#fff;display:flex;align-items:center;justify-content:center;font-size:24px;font-weight:bold;}
.form-box{flex:1;padding:40px;display:flex;flex-direction:column;justify-content:center;}
</style>