<template>
  <div class="layout-container">
    <!-- 侧边栏 -->
    <Sidebar />
    <!-- 主内容区 -->
    <div class="main-content">
      <!-- 头部 -->
      <Header />
      <!-- 页面内容 -->
      <div class="page-content">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script setup>
import Sidebar from './Sidebar.vue'
import Header from './Header.vue'
</script>

<style scoped>
.layout-container {
  height: 100%;
  display: flex;
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.page-content {
  flex: 1;
  padding: 20px;
  background-color: #f5f7fa;
  overflow-y: auto;
}
</style>