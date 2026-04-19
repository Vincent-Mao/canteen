<template>
  <div class="feedback-list-page">
    <!-- 筛选栏 -->
    <el-card shadow="never" class="filter-card">
      <el-form :model="filterForm" inline @submit.prevent="getFeedbackList">
        <el-form-item label="反馈状态">
          <el-select
              v-model="filterForm.status"
              placeholder="全部状态"
              clearable
              style="width: 160px"
          >
            <el-option label="未处理" value="0" />
            <el-option label="已处理" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="反馈时间">
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
          <el-button type="primary" @click="getFeedbackList">查询</el-button>
          <el-button @click="resetFilter">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 列表展示 -->
    <el-card shadow="hover" class="list-card">
      <el-table
          v-loading="loading"
          :data="feedbackList"
          border
          stripe
          style="width: 100%"
          @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="ID" width="80" align="center" />
        <el-table-column prop="userName" label="反馈用户" min-width="100" />
        <el-table-column prop="content" label="反馈内容" min-width="300">
          <template #default="scope">
            <el-tooltip :content="scope.row.content" placement="top">
              <span>{{ scope.row.content.slice(0, 50) }}...</span>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="联系电话" min-width="120" />
        <el-table-column prop="createTime" label="反馈时间" min-width="180" />
        <el-table-column label="反馈状态" min-width="100">
          <template #default="scope">
            <el-tag :type="scope.row.status === '1' ? 'success' : 'warning'">
              {{ scope.row.status === '1' ? '已处理' : '未处理' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" min-width="150" align="center">
          <template #default="scope">
            <el-button
                type="primary"
                size="small"
                @click="handleDetail(scope.row)"
            >
              查看详情
            </el-button>
            <el-button
                v-if="scope.row.status === '0'"
                type="success"
                size="small"
                @click="handleHandle(scope.row.id)"
            >
              标记已处理
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
            @size-change="getFeedbackList"
            @current-change="getFeedbackList"
        />
      </div>

      <!-- 空数据占位 -->
      <Empty v-else text="暂无反馈数据" />
    </el-card>

    <!-- 详情弹窗 -->
    <el-dialog
        v-model="detailDialogVisible"
        title="反馈详情"
        width="600px"
        destroy-on-close
    >
      <el-descriptions :column="1" border :data="currentFeedback">
        <el-descriptions-item label="反馈用户">
          {{ currentFeedback.userName || '-' }}
        </el-descriptions-item>
        <el-descriptions-item label="联系电话">
          {{ currentFeedback.phone || '-' }}
        </el-descriptions-item>
        <el-descriptions-item label="反馈时间">
          {{ currentFeedback.createTime || '-' }}
        </el-descriptions-item>
        <el-descriptions-item label="反馈状态">
          <el-tag :type="currentFeedback.status === '1' ? 'success' : 'warning'">
            {{ currentFeedback.status === '1' ? '已处理' : '未处理' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="反馈内容">
          <div style="white-space: pre-wrap; word-break: break-all">
            {{ currentFeedback.content || '-' }}
          </div>
        </el-descriptions-item>
        <el-descriptions-item label="处理备注" v-if="currentFeedback.handleRemark">
          <div style="white-space: pre-wrap; word-break: break-all">
            {{ currentFeedback.handleRemark || '-' }}
          </div>
        </el-descriptions-item>
      </el-descriptions>
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
  getFeedbackListApi,
  handleFeedbackApi,
  getFeedbackDetailApi
} from '@/api/feedback'

// 加载状态
const loading = ref(false)
// 反馈列表数据
const feedbackList = ref([])
// 总条数
const total = ref(0)
// 筛选表单
const filterForm = ref({
  status: '', // 0-未处理 1-已处理
  dateRange: [] // 时间范围
})
// 分页参数
const pagination = ref({
  pageNum: 1,
  pageSize: 10
})
// 详情弹窗
const detailDialogVisible = ref(false)
// 当前选中的反馈
const currentFeedback = ref({})
// 批量选中的ID
const selectedIds = ref([])

// 获取反馈列表
const getFeedbackList = async () => {
  try {
    loading.value = true
    // 构造请求参数
    const params = {
      pageNum: pagination.value.pageNum,
      pageSize: pagination.value.pageSize,
      status: filterForm.value.status,
      startDate: filterForm.value.dateRange[0] || '',
      endDate: filterForm.value.dateRange[1] || ''
    }
    const res = await getFeedbackListApi(params)
    feedbackList.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error('获取反馈列表失败')
  } finally {
    loading.value = false
  }
}

// 重置筛选条件
const resetFilter = () => {
  filterForm.value = {
    status: '',
    dateRange: []
  }
  pagination.value.pageNum = 1
  getFeedbackList()
}

// 查看详情
const handleDetail = async (row) => {
  try {
    const res = await getFeedbackDetailApi(row.id)
    currentFeedback.value = res.data
    detailDialogVisible.value = true
  } catch (error) {
    ElMessage.error('获取反馈详情失败')
  }
}

// 标记已处理
const handleHandle = async (id) => {
  try {
    await ElMessageBox.confirm('确定标记该反馈为已处理？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await handleFeedbackApi(id)
    ElMessage.success('标记成功')
    getFeedbackList()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('标记失败，请重试')
    }
  }
}

// 批量选择事件
const handleSelectionChange = (val) => {
  selectedIds.value = val.map(item => item.id)
}

// 页面加载时获取列表
onMounted(() => {
  getFeedbackList()
})
</script>

<style scoped>
.feedback-list-page {
  padding: 20px;
}

.filter-card {
  margin-bottom: 20px;
}

.pagination-container {
  margin-top: 20px;
  text-align: right;
}
</style>