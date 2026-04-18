<template>
  <div class="profile">
    <h3>个人中心</h3>
    <el-descriptions :column="1" border>
      <el-descriptions-item label="账号">{{ userInfo.username }}</el-descriptions-item>
      <el-descriptions-item label="窗口位置">{{ userInfo.windowLocation }}</el-descriptions-item>
      <el-descriptions-item label="创建时间">{{ userInfo.createTime }}</el-descriptions-item>
    </el-descriptions>
  </div>
</template>

<script setup>
import { useStore } from 'vuex'
const store = useStore()
const userInfo = store.state.dining.userInfo
</script>