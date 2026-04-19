<template>
  <MainLayout>
    <div class="home-page">
      <h1>欢迎进入商家管理系统</h1>
      <p>当前登录商家：{{ $store.getters.diningHallInfo.name }}</p>
      <!-- 可添加商家数据概览等内容 -->
    </div>
  </MainLayout>
</template>

<script>
import MainLayout from '@/components/Layout/MainLayout'
export default {
  name: 'Home',
  components: {
    MainLayout
  },
  mounted() {
    // 加载商家信息
    this.$store.dispatch('getProfile')
  }
}
</script>

<style scoped>
.home-page {
  text-align: center;
  padding: 50px 0;
}
.home-page h1 {
  color: #2c3e50;
  margin-bottom: 20px;
}
</style>