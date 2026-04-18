<template>
    <div class="feedback-page">
        <el-card>
            <template #header>
                <div class="card-header">
                    <h2>意见反馈</h2>
                    <p>感谢您光临本食堂, 欢迎提出您宝贵的意见</p>
                </div>
            </template>

            <el-form :model="form" :rules="rules" ref="formRef" label-width="100px" class="feedback-form">
                <el-form-item label="反馈类型" prop="type">
                    <el-select v-model="form.type" placeholder="请选择反馈类型">
                        <el-option
                                v-for="dict in feedback_type"
                                :key="dict.value"
                                :label="dict.label"
                                :value="dict.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="联系方式" prop="contact">
                    <el-input v-model="form.contact" placeholder="请输入邮箱或手机号, 方便我们与您联系(选填)"/>
                </el-form-item>
                <el-form-item label="反馈内容" prop="content">
                    <el-input :rows="6" v-model="form.content" type="textarea"
                              placeholder="请详细描述您的意见或建议, 我们会认真考虑每一条反馈"/>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submit" size="large" class="submit-button">
                        提交反馈
                    </el-button>
                    <el-button @click="resetForm" size="large">
                        重置
                    </el-button>
                </el-form-item>
            </el-form>

        </el-card>
    </div>
</template>

<script setup>
import {ref} from 'vue'
import {addFeedback} from "@/api/canteen/feedback.js";
import {ElMessage} from "element-plus";

const {proxy} = getCurrentInstance()
const {feedback_type} = proxy.useDict('feedback_type')

//提交反馈
const submit = () => {
  formRef.value.validate((valid) => {
      if (valid) {
          addFeedback(form.value).then(res => {
              resetForm()
              ElMessage.success('反馈提交成功!感谢您的宝贵意见~')
          })
      }
  })
}

//表单实例
const formRef = ref(null)

//重置表单
const resetForm = () => {
    formRef.value.resetFields()
}

//表单参数
const form = ref({
    type: null,
    contact: null,
    content: null
})

//表单验证
const rules = ref({
    type: [
        {required: true, message: "请选择反馈类型", trigger: "change"}
    ],
    content: [
        {required: true, message: "请输入反馈内容", trigger: "blur"},
        {min: 10, message: '反馈内容至少10个字', trigger: "blur"}
    ]
})

</script>

<style scoped>
/* 反馈页面整体样式 */
.feedback-page {
    padding: 20px; /* 内边距 */
    max-width: 800px; /* 最大宽度，保持表单不会过宽 */
    margin: 0 auto; /* 水平居中 */
}

/* 卡片头部样式 */
.card-header h2 {
    margin: 0; /* 移除默认外边距 */
    color: #333; /* 主文字颜色 */
    font-size: 24px; /* 标题字体大小 */
}

.card-header p {
    margin: 10px 0 0; /* 上边距10px，其他方向0 */
    color: #666; /* 次要文字颜色 */
    font-size: 14px; /* 描述文字大小 */
}

/* 表单样式 */
.feedback-form {
    margin-top: 20px; /* 表单与头部间距 */
}

/* 提交按钮样式 */
.submit-button {
    width: 120px; /* 固定宽度 */
    margin-right: 20px; /* 与重置按钮间距 */
}

/* 深度选择器，用于修改子组件样式 */
/*
   :deep() 是Vue3的样式穿透语法，用于修改子组件的样式
   在scoped样式下，普通选择器无法修改子组件样式
*/
:deep(.el-card__header) {
    background-color: #f5f7fa; /* 卡片头部背景色 */
    border-bottom: 1px solid #ebeef5; /* 底部边框 */
}
</style>
