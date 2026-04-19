<template>
  <MainLayout>
    <div class="profile-page">
      <el-card title="基本信息">
        <el-form :model="profileForm" :rules="profileRules" ref="profileForm" label-width="100px">
          <el-form-item label="商家名称" prop="name">
            <el-input v-model="profileForm.name"></el-input>
          </el-form-item>
          <el-form-item label="联系电话" prop="phone">
            <el-input v-model="profileForm.phone"></el-input>
          </el-form-item>
          <el-form-item label="商家地址" prop="address">
            <el-input v-model="profileForm.address"></el-input>
          </el-form-item>
          <!-- 新增：窗口位置 -->
          <el-form-item label="窗口位置" prop="windowPosition">
            <el-input v-model="profileForm.windowPosition" placeholder="如：一楼101"></el-input>
          </el-form-item>
          <el-form-item label="头像" prop="avatar">
            <el-upload
                action="/dining-hall/upload/avatar"
                :on-success="handleAvatarSuccess"
                :show-file-list="false"
            >
              <img v-if="profileForm.avatar" :src="profileForm.avatar" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleUpdate">保存修改</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </MainLayout>
</template>

<script>
import MainLayout from '@/components/Layout/MainLayout'
import api from '@/api'
export default {
  name: 'Profile',
  components: {
    MainLayout
  },
  data() {
    return {
      profileForm: {
        name: '',
        phone: '',
        address: '',
        windowPosition: '', // 新增：窗口位置
        avatar: ''
      },
      profileRules: {
        name: [
          { required: true, message: '请输入商家名称', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入联系电话', trigger: 'blur' },
          { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
        ],
        windowPosition: [
          { required: true, message: '请输入窗口位置', trigger: 'blur' }
        ]
      }
    }
  },
  mounted() {
    this.getProfileInfo()
  },
  methods: {
    // 获取个人信息
    getProfileInfo() {
      api.userApi.getProfile().then(res => {
        this.profileForm = res.data
      }).catch(err => {
        this.$message.error('获取信息失败：' + err.message)
      })
    },
    // 头像上传成功
    handleAvatarSuccess(res) {
      this.profileForm.avatar = res.data.url
      this.$message.success('头像上传成功')
    },
    // 修改个人信息
    handleUpdate() {
      this.$refs.profileForm.validate((valid) => {
        if (valid) {
          api.userApi.updateProfile(this.profileForm).then(() => {
            this.$message.success('修改成功')
            // 更新store中的信息
            this.$store.commit('SET_DINING_HALL_INFO', this.profileForm)
          }).catch(err => {
            this.$message.error('修改失败：' + err.message)
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.profile-page {
  max-width: 800px;
  margin: 0 auto;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
}
</style>