<template>
  <div class="main-layout">
    <!-- 顶部导航 -->
    <header class="header">
      <nav class="nav">
        <router-link to="/home" class="nav-item">主页</router-link>
        <router-link to="/home/menu-manage" class="nav-item">菜单管理</router-link>
        <router-link to="/home/order-manage" class="nav-item">订单管理</router-link>
        <router-link to="/home/feedback" class="nav-item">用户意见反馈</router-link>
        <router-link to="/home/profile" class="nav-item">个人中心</router-link>
        <button class="logout-btn" @click="logout">退出登录</button>
      </nav>
    </header>
    <!-- 内容区域 -->
    <main class="content">
      <router-view />
    </main>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
export default {
  name: 'MainLayout',
  methods: {
    ...mapMutations(['LOGOUT']),
    logout() {
      this.LOGOUT()
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>
.header {
  background: #2c3e50;
  padding: 0 20px;
}
.nav {
  display: flex;
  align-items: center;
  height: 60px;
}
.nav-item {
  color: white;
  text-decoration: none;
  margin-right: 30px;
  font-size: 16px;
}
.nav-item:hover {
  color: #3498db;
}
.logout-btn {
  margin-left: auto;
  background: #e74c3c;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
}
.content {
  padding: 20px;
  min-height: calc(100vh - 60px);
}
</style>