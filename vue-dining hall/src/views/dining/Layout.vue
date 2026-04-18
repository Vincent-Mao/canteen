<template>
  <div class="layout">
    <header class="header">
      <div class="logo">食堂窗口商家管理系统</div>
      <div class="info">
        <span>{{ store.state.dining.userInfo.username }}</span>
        <span>{{ store.state.dining.userInfo.windowLocation }}</span>
        <el-button text @click="logout">退出登录</el-button>
      </div>
    </header>
    <div class="container"><router-view /></div>
  </div>
</template>

<script setup>
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const store = useStore()
const router = useRouter()

const logout = async () => {
  await store.dispatch('dining/logout')
  ElMessage.success('退出成功')
  router.push('/dining/login')
}
</script>

<style lang="scss" scoped>
.layout { min-height: 100vh; }
.header {
  background: #409eff; color: white; height: 60px; padding: 0 20px;
  display: flex; align-items: center; justify-content: space-between;
}
.info { display: flex; gap: 15px; align-items: center; }
.container { padding: 20px; }
</style>