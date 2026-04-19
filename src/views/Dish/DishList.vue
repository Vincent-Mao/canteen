<template>
  <div class="dish-list">
    <el-card>
      <!-- 搜索和新增按钮 -->
      <div class="dish-header">
        <el-input
            v-model="searchKey"
            placeholder="请输入菜品名称搜索"
            style="width: 300px"
            @keyup.enter="getDishList"
        >
          <template #append>
            <el-button @click="getDishList"><el-icon><Search /></el-icon></el-button>
          </template>
        </el-input>
        <el-button type="primary" @click="openDishForm()">
          <el-icon><Plus /></el-icon>新增菜品
        </el-button>
      </div>

      <!-- 菜品列表 -->
      <el-table
          :data="dishList"
          border
          stripe
          v-loading="loading"
          style="width: 100%; margin-top: 20px"
      >
        <el-table-column prop="id" label="ID" width="80" align="center"></el-table-column>
        <el-table-column prop="name" label="菜品名称" min-width="150"></el-table-column>
        <el-table-column prop="price" label="价格(元)" width="100" align="center">
          <template #default="scope">
            {{ scope.row.price.toFixed(2) }}
          </template>
        </el-table-column>
        <el-table-column prop="image" label="菜品图片" width="120" align="center">
          <template #default="scope">
            <el-image
                :src="scope.row.image || defaultDishImg"
                fit="cover"
                style="width: 80px; height: 60px; border-radius: 4px"
            ></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100" align="center">
          <template #default="scope">
            <el-switch
                v-model="scope.row.status"
                active-value="1"
                inactive-value="0"
                @change="updateDishStatus(scope.row)"
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template #default="scope">
            <el-button type="primary" size="small" @click="openDishForm(scope.row)">
              编辑
            </el-button>
            <el-button type="danger" size="small" @click="deleteDish(scope.row.id)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[10, 20, 50]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          style="margin-top: 20px; text-align: right"
      >
      </el-pagination>
    </el-card>

    <!-- 新增/编辑菜品弹窗 -->
    <DishForm
        v-model="dialogVisible"
        :dish-info="currentDish"
        :is-edit="isEdit"
        @success="getDishList"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Plus } from '@element-plus/icons-vue'
import DishForm from './DishForm.vue'
import {
  getDishList as getDishListApi,
  deleteDish as deleteDishApi,
  updateDishStatus as updateDishStatusApi
} from '@/api/dish'

// 分页参数
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const loading = ref(false)
const searchKey = ref('') // 搜索关键词
const dishList = ref([]) // 菜品列表
const dialogVisible = ref(false) // 弹窗显示状态
const isEdit = ref(false) // 是否编辑
const currentDish = ref({}) // 当前编辑的菜品信息
const defaultDishImg = '@/assets/images/dish-default.png' // 菜品默认图片

// 获取菜品列表
const getDishList = async () => {
  loading.value = true
  try {
    const res = await getDishListApi({
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      name: searchKey.value
    })
    dishList.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error('获取菜品列表失败')
  } finally {
    loading.value = false
  }
}

// 分页切换
const handleSizeChange = (val) => {
  pageSize.value = val
  getDishList()
}
const handleCurrentChange = (val) => {
  pageNum.value = val
  getDishList()
}

// 打开新增/编辑弹窗
const openDishForm = (row = {}) => {
  dialogVisible.value = true
  if (row.id) {
    // 编辑
    isEdit.value = true
    currentDish.value = { ...row }
  } else {
    // 新增
    isEdit.value = false
    currentDish.value = {
      name: '',
      price: '',
      image: '',
      desc: '',
      status: '1'
    }
  }
}

// 删除菜品
const deleteDish = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该菜品吗？', '提示', {
      type: 'warning'
    })
    await deleteDishApi(id)
    ElMessage.success('删除成功')
    getDishList()
  } catch (error) {
    ElMessage.info('已取消删除')
  }
}

// 更新菜品状态（上架/下架）
const updateDishStatus = async (row) => {
  try {
    await updateDishStatusApi({
      id: row.id,
      status: row.status
    })
    ElMessage.success(`菜品已${row.status === '1' ? '上架' : '下架'}`)
  } catch (error) {
    row.status = row.status === '1' ? '0' : '1' // 状态回滚
    ElMessage.error('更新状态失败')
  }
}

// 初始化加载列表
onMounted(() => {
  getDishList()
})
</script>

<style scoped>
.dish-list {
  height: 100%;
}

.dish-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>