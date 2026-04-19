<template>
  <div class="main-layout">
    <!-- 顶部导航栏 -->
    <header class="header">
      <!-- 左侧 自定义图片Logo + 商家窗口名称 -->
      <div class="logo-area">
        <!-- 在这里替换你的本地Logo图片路径 -->
        <img class="logo-img" src="@/assets/logo.png" alt="logo" />
        <span class="system-name">商家窗口</span>
      </div>

      <!-- 中间导航菜单 -->
      <nav class="nav">
        <router-link to="/home/menu-manage" class="nav-item" active-class="active">菜单管理</router-link>
        <router-link to="/home/order-manage" class="nav-item" active-class="active">订单管理</router-link>
        <router-link to="/home/feedback" class="nav-item" active-class="active">用户意见反馈</router-link>
        <router-link to="/home/profile" class="nav-item" active-class="active">个人中心</router-link>
      </nav>

      <!-- 右侧 商家头像+下拉退出 -->
      <div class="user-dropdown">
        <div class="user-info">
          <div class="user-avatar">{{ userName.slice(0,1) }}</div>
          <span class="user-name">{{ userName }}</span>
        </div>
        <!-- hover弹出退出 -->
        <div class="logout-pop">
          <div class="logout-btn" @click="logout">退出登录</div>
        </div>
      </div>
    </header>

    <!-- 主内容区域 -->
    <main class="content">
      <router-view />
    </main>
  </div>
</template>

<script>
export default {
  name: 'MainLayout',
  computed: {
    userName() {
      return this.$store.getters.diningHallInfo?.name || '商家'
    }
  },
  methods: {
    logout() {
      this.$store.commit('SET_TOKEN', '')
      this.$store.commit('SET_DINING_HALL_INFO', {})
      this.$router.push('/login')
      this.$message.success('退出登录成功')
    }
  }
}
</script>

<style scoped>
.main-layout {
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
}

/* 顶部导航栏 改成和用户端同款深绿色 */
.header {
  background-color: #226644;
  padding: 0 24px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

/* 左侧Logo区域 */
.logo-area {
  display: flex;
  align-items: center;
  gap: 10px;
  color: #ffffff;
  font-size: 18px;
  font-weight: 600;
}
/* 自定义图片Logo 自动适配大小不变形 */
.logo-img {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  object-fit: cover;
}
.system-name {
  white-space: nowrap;
}

/* 中间导航 */
.nav {
  display: flex;
  align-items: center;
}
.nav-item {
  color: #ffffff;
  text-decoration: none;
  margin: 0 18px;
  font-size: 15px;
  padding: 4px 0;
}
.nav-item.active {
  color: #ffffff;
  font-weight: bold;
  border-bottom: 2px solid #ffffff;
}
.nav-item:hover {
  color: #e8f5e9;
}

/* 右侧用户下拉 */
.user-dropdown {
  position: relative;
  cursor: pointer;
}
.user-info {
  display: flex;
  align-items: center;
  gap: 6px;
  color: #fff;
}
.user-avatar {
  width: 28px;
  height: 28px;
  border-radius: 50%;
  background: #52c41a;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
}
.user-name {
  font-size: 14px;
}

/* 下拉退出菜单 完全复刻用户端 */
.logout-pop {
  position: absolute;
  top: 60px;
  right: 0;
  background: #fff;
  border-radius: 4px;
  min-width: 80px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.12);
  opacity: 0;
  visibility: hidden;
  transform: translateY(-8px);
  transition: all 0.2s ease;
}
.user-dropdown:hover .logout-pop {
  opacity: 1;
  visibility: visible;
  transform: translateY(0);
}
.logout-btn {
  padding: 10px;
  text-align: center;
  font-size: 14px;
  color: #333;
}
.logout-btn:hover {
  background-color: #f5f5f5;
  color: #e53935;
}

/* 内容区域 */
.content {
  flex: 1;
  padding: 20px;
  background-color: #f5f7fa;
  box-sizing: border-box;
}
</style>