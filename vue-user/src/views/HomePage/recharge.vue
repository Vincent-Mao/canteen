<template>
    <div class="recharge-page">
        <h1 class="page-title">饭卡充值</h1>

        <!-- 余额显示 -->
        <el-card class="balance-card">
            <div class="balance-info">
                <div class="balance-label">当前余额:</div>
                <div class="balance-amount">¥{{ myBalance }}</div>
            </div>
        </el-card>

        <!-- 充值表单 -->
        <el-card class="recharge-card">
            <el-form :model="form" :rules="rules" ref="formRef">
                <el-form-item label="充值金额" prop="amount">
                    <el-input v-model="form.amount"
                              placeholder="请输入充值金额"
                              clearable
                              size="large"
                    >
                        <template #prepend>¥</template>
                    </el-input>
                </el-form-item>

                <el-form-item label="充值方式" prop="paymentMethod">
                    <div class="payment-options">
                        <el-radio v-for="method in paymentMethods"
                                  :key="method"
                                  :label="method.value"
                                  v-model="form.paymentMethod"
                                  class="payment-option"
                                  border
                        >
                            <div class="payment-method">
                                <SvgIcon icon-class="微信" v-if="method.lable === '微信支付'" class="payment-icon"/>
                                <SvgIcon icon-class="支付宝" v-if="method.lable === '支付宝'" class="payment-icon"/>
                                <SvgIcon icon-class="银行卡" v-if="method.lable === '银行卡'" class="payment-icon"/>
                                <span class="payment-text">{{ method.lable }}</span>
                            </div>
                        </el-radio>
                    </div>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" size="large" class="recharge-button"
                               @click="submitRecharge"
                    >
                        立即充值
                    </el-button>
                </el-form-item>

            </el-form>
        </el-card>

    </div>
</template>

<script setup>
import {onMounted, ref, reactive} from 'vue'
import {recharge, selectMyBalance} from "@/api/system/user.js";
import SvgIcon from "@/components/SvgIcon/index.vue";
import {ElMessage} from "element-plus";

//表单实例
const formRef = ref(null)

//提交充值
const submitRecharge = () => {
    //判断充值金额是否大于0.01
    if (form.value.amount < 0.01) {
        ElMessage.error('充值金额必须大于0.01')
    } else {
        //表单验证
        formRef.value.validate((valid) => {
            if (valid) {
                //验证通过, 调用充值API
                recharge(form.value.amount).then(res => {
                    //重置表单
                    formRef.value.resetFields()
                    //显示充值成功消息
                    ElMessage.success('充值成功!')
                    getMyBalance()
                })
            }
        })
    }
}

//支付方式数据
const paymentMethods = [
    {
        value: 'wechat',
        lable: '微信支付'
    },
    {
        value: 'alipay',
        lable: '支付宝'
    },
    {
        value: 'bank',
        lable: '银行卡'
    },
]

//表单参数
const form = ref({
    amount: null,
    paymentMethod: 'wechat'
})

//表单验证
const rules = reactive({
    amount: [
        {required: true, message: '请输入充值金额', trigger: 'blur'}
    ],
    paymentMethod: [
        {required: true, message: '请选择充值方式', trigger: 'change'}
    ]
})

//饭卡余额
const myBalance = ref(null)

const getMyBalance = () => {
    selectMyBalance().then(res => {
        myBalance.value = res.data
    })
}

onMounted(() => {
    getMyBalance()
})

</script>

<style scoped>
/* 充值页面主容器样式 */
.recharge-page {
    padding: 20px; /* 内边距 */
    max-width: 800px; /* 最大宽度 */
    margin: 0 auto; /* 水平居中 */
}

/* 页面标题样式 */
.page-title {
    text-align: center; /* 文字居中 */
    color: #333; /* 文字颜色 - 深灰色 */
    margin-bottom: 20px; /* 底部外边距 */
}

/* 余额卡片样式 */
.balance-card {
    margin-bottom: 30px; /* 底部外边距 */
}

/* 余额信息容器样式 */
.balance-info {
    display: flex; /* 弹性布局 */
    justify-content: space-between; /* 两端对齐 */
    align-items: center; /* 垂直居中 */
}

/* 余额标签样式 */
.balance-label {
    font-size: 18px; /* 字体大小 */
    color: #666; /* 文字颜色 - 中灰色 */
}

/* 余额金额样式 */
.balance-amount {
    font-size: 28px; /* 字体大小 */
    font-weight: bold; /* 粗体 */
    color: #f56c6c; /* 文字颜色 - 红色系 */
}

/* 充值卡片样式 */
.recharge-card {
    margin-bottom: 20px; /* 底部外边距 */
}

/* 支付选项容器样式 */
.payment-options {
    display: flex; /* 弹性布局 */
    gap: 15px; /* 子项间距 */
    justify-content: space-between; /* 两端对齐 */
}

/* 单个支付选项样式 */
.payment-option {
    flex: 1; /* 等分剩余空间 */
    height: 70px !important; /* 固定高度，使用 !important 提高优先级 */
    display: flex; /* 弹性布局 */
    align-items: center; /* 垂直居中 */
    justify-content: center; /* 水平居中 */
}

/* 支付方式显示样式 */
.payment-method {
    display: flex; /* 弹性布局 */
    align-items: center; /* 垂直居中 */
    justify-content: center; /* 水平居中 */
}

/* 支付图标样式 */
.payment-icon {
    font-size: 30px; /* 图标大小 */
    margin-right: 10px; /* 右侧外边距 */
}

/* 支付文字样式 */
.payment-text {
    font-size: 18px; /* 字体大小 */
    font-weight: 500; /* 中等字重 */
}

/* 充值按钮样式 */
.recharge-button {
    width: 100%; /* 宽度100% */
    margin-top: 20px; /* 顶部外边距 */
    height: 50px; /* 固定高度 */
    font-size: 18px; /* 字体大小 */
}

/* 深度选择器 - 修改 Element Plus 组件内部样式 */

/* 表单标签样式 */
:deep(.el-form-item__label) {
    font-size: 16px; /* 字体大小 */
    font-weight: 500; /* 中等字重 */
}

/* 输入框内部样式 */
:deep(.el-input__inner) {
    height: 50px; /* 输入框高度 */
    font-size: 16px; /* 字体大小 */
}

/* 单选按钮选中时的标签样式 */
:deep(.el-radio__input.is-checked+.el-radio__label) {
    font-weight: 500; /* 中等字重 */
}
</style>
