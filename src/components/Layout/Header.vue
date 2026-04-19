<template>
  <div class="header">
    <div class="header-right">
      <el-dropdown>
        <span class="user-info">
          <el-avatar :src="userStore.userInfo.avatar || defaultAvatar"></el-avatar>
          {{ userStore.userInfo.shopName || '商家账号' }}
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="$router.push('/dashboard/profile/info')">
              <el-icon><User /></el-icon>
              个人信息
            </el-dropdown-item>
            <el-dropdown-item @click="handleLogout">
              <el-icon><SwitchButton /></el-icon>
              退出登录
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script setup>
import { User, SwitchButton } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useUserStore } from '@/stores/userStore'

const router = useRouter()
const userStore = useUserStore()
const defaultAvatar = '@/assets/images/avatar.png' // 可自行添加默认头像

// 退出登录
const handleLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    userStore.logout()
    ElMessage.success('退出成功！')
    router.push('/login')
  })
}
</script>

<style scoped>
.header {
  height: 60px;
  line-height: 60px;
  background-color: #fff;
  padding: 0 20px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: flex-end;
}

.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
}

:deep(.el-avatar) {
  margin-right: 8px;
}
</style>