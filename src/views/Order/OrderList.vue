<template>
  <div class="order-list-page">
    <!-- 筛选栏 -->
    <el-card shadow="never" class="filter-card">
      <el-form :model="filterForm" inline @submit.prevent="getOrderList">
        <el-form-item label="订单编号">
          <el-input
              v-model="filterForm.orderNo"
              placeholder="请输入订单编号"
              clearable
              style="width: 200px"
          />
        </el-form-item>
        <el-form-item label="订单状态">
          <el-select
              v-model="filterForm.status"
              placeholder="全部状态"
              clearable
              style="width: 160px"
          >
            <el-option label="待支付" value="0" />
            <el-option label="已支付" value="1" />
            <el-option label="已完成" value="2" />
            <el-option label="已取消" value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="下单时间">
          <el-date-picker
              v-model="filterForm.dateRange"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getOrderList">查询</el-button>
          <el-button @click="resetFilter">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 列表展示 -->
    <el-card shadow="hover" class="list-card">
      <el-table
          v-loading="loading"
          :data="orderList"
          border
          stripe
          style="width: 100%"
          @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="orderNo" label="订单编号" min-width="180" />
        <el-table-column prop="userName" label="下单用户" min-width="100" />
        <el-table-column prop="userPhone" label="联系电话" min-width="120" />
        <el-table-column prop="totalAmount" label="订单金额" width="100">
          <template #default="scope">
            ¥{{ scope.row.totalAmount.toFixed(2) }}
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="下单时间" min-width="180" />
        <el-table-column label="订单状态" min-width="100">
          <template #default="scope">
            <el-tag
                :type="
                scope.row.status === '0'
                  ? 'warning'
                  : scope.row.status === '1'
                  ? 'primary'
                  : scope.row.status === '2'
                  ? 'success'
                  : 'danger'
              "
            >
              {{
                scope.row.status === '0'
                    ? '待支付'
                    : scope.row.status === '1'
                        ? '已支付'
                        : scope.row.status === '2'
                            ? '已完成'
                            : '已取消'
              }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" min-width="200" align="center">
          <template #default="scope">
            <el-button
                type="primary"
                size="small"
                @click="handleDetail(scope.row)"
            >
              查看详情
            </el-button>
            <el-button
                v-if="scope.row.status === '1'"
                type="success"
                size="small"
                @click="handleComplete(scope.row.id)"
            >
              标记完成
            </el-button>
            <el-button
                v-if="scope.row.status === '0'"
                type="danger"
                size="small"
                @click="handleCancel(scope.row.id)"
            >
              取消订单
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination-container" v-if="total > 0">
        <el-pagination
            v-model:current-page="pagination.pageNum"
            v-model:page-size="pagination.pageSize"
            :page-sizes="[10, 20, 50, 100]"
            :total="total"
            layout="total, sizes, prev, pager, next, jumper"
            @size-change="getOrderList"
            @current-change="getOrderList"
        />
      </div>

      <!-- 空数据占位 -->
      <Empty v-else text="暂无订单数据" />
    </el-card>

    <!-- 订单详情弹窗 -->
    <el-dialog
        v-model="detailDialogVisible"
        title="订单详情"
        width="800px"
        destroy-on-close
    >
      <div class="order-detail">
        <!-- 订单基本信息 -->
        <el-descriptions :column="2" border class="mb-4">
          <el-descriptions-item label="订单编号" span="2">
            {{ currentOrder.orderNo || '-' }}
          </el-descriptions-item>
          <el-descriptions-item label="下单用户">
            {{ currentOrder.userName || '-' }}
          </el-descriptions-item>
          <el-descriptions-item label="联系电话">
            {{ currentOrder.userPhone || '-' }}
          </el-descriptions-item>
          <el-descriptions-item label="订单金额">
            ¥{{ currentOrder.totalAmount?.toFixed(2) || '0.00' }}
          </el-descriptions-item>
          <el-descriptions-item label="订单状态">
            <el-tag
                :type="
                currentOrder.status === '0'
                  ? 'warning'
                  : currentOrder.status === '1'
                  ? 'primary'
                  : currentOrder.status === '2'
                  ? 'success'
                  : 'danger'
              "
            >
              {{
                currentOrder.status === '0'
                    ? '待支付'
                    : currentOrder.status === '1'
                        ? '已支付'
                        : currentOrder.status === '2'
                            ? '已完成'
                            : '已取消'
              }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="下单时间" span="2">
            {{ currentOrder.createTime || '-' }}
          </el-descriptions-item>
        </el-descriptions>

        <!-- 订单菜品列表 -->
        <el-table :data="currentOrder.dishList || []" border stripe>
          <el-table-column prop="dishName" label="菜品名称" min-width="200" />
          <el-table-column prop="price" label="单价" width="100">
            <template #default="scope">
              ¥{{ scope.row.price.toFixed(2) }}
            </template>
          </el-table-column>
          <el-table-column prop="quantity" label="数量" width="80" align="center" />
          <el-table-column prop="amount" label="小计" width="100">
            <template #default="scope">
              ¥{{ scope.row.amount.toFixed(2) }}
            </template>
          </el-table-column>
        </el-table>
      </div>
      <template #footer>
        <el-button @click="detailDialogVisible = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import Empty from '@/components/Empty.vue'
import {
  getOrderListApi,
  getOrderDetailApi,
  completeOrderApi,
  cancelOrderApi
} from '@/api/order'

// 加载状态
const loading = ref(false)
// 订单列表数据
const orderList = ref([])
// 总条数
const total = ref(0)
// 筛选表单
const filterForm = ref({
  orderNo: '', // 订单编号
  status: '', // 0-待支付 1-已支付 2-已完成 3-已取消
  dateRange: [] // 时间范围
})
// 分页参数
const pagination = ref({
  pageNum: 1,
  pageSize: 10
})
// 详情弹窗
const detailDialogVisible = ref(false)
// 当前选中的订单
const currentOrder = ref({})
// 批量选中的ID
const selectedIds = ref([])

// 获取订单列表
const getOrderList = async () => {
  try {
    loading.value = true
    // 构造请求参数
    const params = {
      pageNum: pagination.value.pageNum,
      pageSize: pagination.value.pageSize,
      orderNo: filterForm.value.orderNo,
      status: filterForm.value.status,
      startDate: filterForm.value.dateRange[0] || '',
      endDate: filterForm.value.dateRange[1] || ''
    }
    const res = await getOrderListApi(params)
    orderList.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error('获取订单列表失败')
  } finally {
    loading.value = false
  }
}

// 重置筛选条件
const resetFilter = () => {
  filterForm.value = {
    orderNo: '',
    status: '',
    dateRange: []
  }
  pagination.value.pageNum = 1
  getOrderList()
}

// 查看订单详情
const handleDetail = async (row) => {
  try {
    const res = await getOrderDetailApi(row.id)
    currentOrder.value = res.data
    detailDialogVisible.value = true
  } catch (error) {
    ElMessage.error('获取订单详情失败')
  }
}

// 标记订单完成
const handleComplete = async (id) => {
  try {
    await ElMessageBox.confirm('确定标记该订单为已完成？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await completeOrderApi(id)
    ElMessage.success('标记成功')
    getOrderList()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('标记失败，请重试')
    }
  }
}

// 取消订单
const handleCancel = async (id) => {
  try {
    await ElMessageBox.confirm('确定取消该订单？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'danger'
    })
    await cancelOrderApi(id)
    ElMessage.success('订单已取消')
    getOrderList()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('取消失败，请重试')
    }
  }
}

// 批量选择事件
const handleSelectionChange = (val) => {
  selectedIds.value = val.map(item => item.id)
}

// 页面加载时获取列表
onMounted(() => {
  getOrderList()
})
</script>

<style scoped>
.order-list-page {
  padding: 20px;
}

.filter-card {
  margin-bottom: 20px;
}

.pagination-container {
  margin-top: 20px;
  text-align: right;
}

.order-detail {
  max-height: 600px;
  overflow-y: auto;
}

.mb-4 {
  margin-bottom: 16px;
}
</style>