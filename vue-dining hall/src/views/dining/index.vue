<template>
  <div class="dashboard">
    <div class="welcome">
      <h2>欢迎回来，{{ userInfo.username }}</h2>
      <p>窗口：{{ userInfo.windowLocation }}</p>
    </div>
  </div>
</template>

<script setup>
import { useStore } from 'vuex'
const store = useStore()
const userInfo = store.state.dining.userInfo
</script>

<style lang="scss" scoped>
.welcome{background:#fff;padding:20px;border-radius:8px;box-shadow:0 0 10px #eee;}
</style>