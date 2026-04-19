<template>
  <div class="sidebar">
    <div class="sidebar-logo">
      <h3>商家窗口管理</h3>
    </div>
    <el-menu
        default-active="/dashboard/dish"
        class="sidebar-menu"
        router
        unique-opened
    >
      <el-menu-item index="/dashboard/dish">
        <el-icon><Menu /></el-icon>
        <template #title>菜品管理</template>
      </el-menu-item>
      <el-menu-item index="/dashboard/order">
        <el-icon><ShoppingCart /></el-icon>
        <template #title>订单管理</template>
      </el-menu-item>
      <el-menu-item index="/dashboard/feedback">
        <el-icon><Message /></el-icon>
        <template #title>意见反馈</template>
      </el-menu-item>
      <el-sub-menu index="/dashboard/profile">
        <template #title>
          <el-icon><User /></el-icon>
          <span>个人中心</span>
        </template>
        <el-menu-item index="/dashboard/profile/info">基本信息</el-menu-item>
        <el-menu-item index="/dashboard/profile/password">修改密码</el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>
</template>

<script setup>
import { Menu, ShoppingCart, Message, User } from '@element-plus/icons-vue'
</script>

<style scoped>
.sidebar {
  width: 200px;
  height: 100%;
  background-color: #2e3b4e;
  color: #fff;
}

.sidebar-logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  font-size: 16px;
  border-bottom: 1px solid #384454;
}

.sidebar-menu {
  border-right: none;
  height: calc(100% - 60px);
  background-color: #2e3b4e;
}

:deep(.el-menu) {
  background-color: #2e3b4e;
  color: #fff;
}

:deep(.el-menu-item) {
  color: #fff;
}

:deep(.el-menu-item.is-active) {
  background-color: #1f2d3d;
  color: #409eff;
}

:deep(.el-sub-menu__title) {
  color: #fff;
}
</style>