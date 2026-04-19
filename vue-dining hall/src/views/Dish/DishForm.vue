<template>
  <el-dialog
      v-model="visible"
      :title="isEdit ? '编辑菜品' : '新增菜品'"
      width="600px"
      @close="resetForm"
  >
    <el-form
        ref="dishFormRef"
        :model="dishForm"
        :rules="dishRules"
        label-width="100px"
    >
      <el-form-item label="菜品名称" prop="name">
        <el-input v-model="dishForm.name" placeholder="请输入菜品名称"></el-input>
      </el-form-item>
      <el-form-item label="菜品价格" prop="price">
        <el-input
            v-model="dishForm.price"
            type="number"
            placeholder="请输入菜品价格"
            suffix="元"
            step="0.01"
            min="0"
        ></el-input>
      </el-form-item>
      <el-form-item label="菜品图片" prop="image">
        <UploadImage
            :value="dishForm.image"
            @change="(val) => (dishForm.image = val)"
        />
      </el-form-item>
      <el-form-item label="菜品描述" prop="desc">
        <el-input
            v-model="dishForm.desc"
            type="textarea"
            placeholder="请输入菜品描述（选填）"
            rows="4"
        ></el-input>
      </el-form-item>
      <el-form-item label="菜品状态" prop="status">
        <el-radio-group v-model="dishForm.status">
          <el-radio label="1">上架</el-radio>
          <el-radio label="0">下架</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="submitForm" :loading="loading">
        确定
      </el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, watch } from 'vue'
import { ElMessage } from 'element-plus'
import UploadImage from '@/components/UploadImage.vue'
import { addDish, editDish } from '@/api/dish'

// 接收父组件参数
const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false
  },
  dishInfo: {
    type: Object,
    default: () => ({})
  },
  isEdit: {
    type: Boolean,
    default: false
  }
})

// 向父组件传值
const emit = defineEmits(['update:modelValue', 'success'])

const visible = ref(props.modelValue)
const loading = ref(false)
const dishFormRef = ref(null)
const dishForm = ref({
  id: '',
  name: '',
  price: '',
  image: '',
  desc: '',
  status: '1'
})

// 表单校验规则
const dishRules = ref({
  name: [{ required: true, message: '请输入菜品名称', trigger: 'blur' }],
  price: [
    { required: true, message: '请输入菜品价格', trigger: 'blur' },
    { type: 'number', min: 0, message: '价格不能为负数', trigger: 'blur' }
  ]
})

// 监听弹窗显示状态
watch(
    () => props.modelValue,
    (val) => {
      visible.value = val
      if (val) {
        dishForm.value = { ...props.dishInfo }
      }
    }
)

// 监听弹窗关闭，通知父组件
watch(
    () => visible.value,
    (val) => {
      emit('update:modelValue', val)
    }
)

// 重置表单
const resetForm = () => {
  dishFormRef.value?.resetFields()
  dishForm.value = {
    id: '',
    name: '',
    price: '',
    image: '',
    desc: '',
    status: '1'
  }
}

// 提交表单
const submitForm = async () => {
  try {
    await dishFormRef.value.validate()
    loading.value = true
    if (props.isEdit) {
      // 编辑菜品
      await editDish(dishForm.value)
      ElMessage.success('编辑菜品成功')
    } else {
      // 新增菜品
      await addDish(dishForm.value)
      ElMessage.success('新增菜品成功')
    }
    visible.value = false
    emit('success') // 通知父组件刷新列表
  } catch (error) {
    ElMessage.error(error.msg || '提交失败，请稍后重试')
  } finally {
    loading.value = false
  }
}
</script>