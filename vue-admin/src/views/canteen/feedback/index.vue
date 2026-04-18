<template>
    <div class="app-container">
        <!-- 顶部搜索 -->
        <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
            <el-form-item label="反馈类型" prop="type">
                <el-select style="width: 200px;" v-model="queryParams.type" placeholder="请选择反馈类型" clearable>
                    <el-option
                            v-for="dict in feedback_type"
                            :key="dict.value"
                            :label="dict.label"
                            :value="dict.value"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="反馈用户" prop="userName">
                <el-input
                        v-model="queryParams.userName"
                        placeholder="请输入反馈用户"
                        clearable
                        @keyup.enter="handleQuery"
                />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
                <el-button icon="Refresh" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>

        <!-- 顶部按钮 -->
        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button
                        type="primary"
                        plain
                        icon="Plus"
                        @click="handleAdd"
                >新增
                </el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button
                        type="success"
                        plain
                        icon="Edit"
                        :disabled="single"
                        @click="handleUpdate"
                >修改
                </el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button
                        type="danger"
                        plain
                        icon="Delete"
                        :disabled="multiple"
                        @click="handleDelete"
                >删除
                </el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button
                        type="warning"
                        plain
                        icon="Download"
                        @click="handleExport"
                >导出
                </el-button>
            </el-col>
            <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <!-- 表格 -->
        <el-table @row-click="clickRow" ref="table" highlight-current-row
                  border v-loading="loading" :data="feedbackList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center"/>
            <el-table-column label="序号" align="center" type="index" :index="indexMethod"/>
            <el-table-column label="反馈类型" align="center" prop="type">
                <template #default="scope">
                    <dict-tag :options="feedback_type" :value="scope.row.type"/>
                </template>
            </el-table-column>
            <el-table-column label="联系方式" align="center" prop="contact"/>
            <el-table-column label="反馈内容" align="center" prop="content"/>
            <el-table-column label="反馈用户" align="center" prop="userName"/>
            <el-table-column label="反馈时间" align="center" prop="createTime"/>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template #default="scope">
                    <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)">修改</el-button>
                    <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 分页组件 -->
        <pagination
                v-show="total>0"
                :total="total"
                v-model:page="queryParams.pageNum"
                v-model:limit="queryParams.pageSize"
                @pagination="getList"
        />

        <!-- 添加或修改意见反馈对话框 -->
        <vxe-modal :title="title" v-model="open" width="500px" show-maximize showFooter resize>
            <el-form ref="feedbackRef" :model="form" :rules="rules" label-width="80px">
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
                    <el-input v-model="form.contact" placeholder="请输入联系方式"/>
                </el-form-item>
                <el-form-item label="反馈内容" prop="content">
                    <el-input v-model="form.content" type="textarea" placeholder="请输入内容"/>
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button type="primary" @click="submitForm">确 定</el-button>
                    <el-button @click="cancel">取 消</el-button>
                </div>
            </template>
        </vxe-modal>
    </div>
</template>

<script setup name="Feedback">
import {listFeedback, getFeedback, delFeedback, addFeedback, updateFeedback} from "@/api/canteen/feedback"
import {getToken} from "@/utils/auth.js";

const baseURL = import.meta.env.VITE_APP_BASE_API

const {proxy} = getCurrentInstance()
const {feedback_type} = proxy.useDict('feedback_type')

const feedbackList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const selectedRow = ref(null)

const data = reactive({
    form: {},
    queryParams: {
        pageNum: 1,
        pageSize: 10,
        type: null,
        userId: null,
        userName: null
    },
    rules: {
        type: [
            {required: true, message: "反馈类型不能为空", trigger: "change"}
        ],
        content: [
            {required: true, message: "反馈内容不能为空", trigger: "blur"}
        ],
        userId: [
            {required: true, message: "反馈用户ID不能为空", trigger: "blur"}
        ],
    }
})

const {queryParams, form, rules} = toRefs(data)

//点击行 获取行
const clickRow = (row) => {
    selectedRow.value = row; // 更新选中的行
    const table = proxy.$refs.table;
    // 清除所有已选中的行
    table.clearSelection();
    // 选中当前点击的行
    table.toggleRowSelection(row, true);
}

/** 自定义序号 */
const indexMethod = (index) => {
    let pageNum = queryParams.value.pageNum - 1;
    if ((pageNum !== -1 && pageNum !== 0)) {
        return (index + 1) + (pageNum * queryParams.value.pageSize);
    } else {
        return (index + 1)
    }
}

/** 查询意见反馈列表 */
const getList = () => {
    loading.value = true
    listFeedback(queryParams.value).then(response => {
        feedbackList.value = response.rows
        total.value = response.total
        loading.value = false
    })
}

// 取消按钮
const cancel = () => {
    open.value = false
    reset()
}

// 表单重置
const reset = () => {
    form.value = {
        feedbackId: null,
        type: null,
        contact: null,
        content: null,
        userId: null,
        createTime: null
    }
    proxy.resetForm("feedbackRef")
}

/** 搜索按钮操作 */
const handleQuery = () => {
    queryParams.value.pageNum = 1
    getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
    proxy.resetForm("queryRef")
    handleQuery()
}

// 多选框选中数据
const handleSelectionChange = (selection) => {
    ids.value = selection.map(item => item.feedbackId)
    single.value = selection.length != 1
    multiple.value = !selection.length
}

/** 新增按钮操作 */
const handleAdd = () => {
    reset()
    open.value = true
    title.value = "添加意见反馈"
}

/** 修改按钮操作 */
const handleUpdate = (row) => {
    reset()
    const _feedbackId = row.feedbackId || ids.value
    getFeedback(_feedbackId).then(response => {
        form.value = response.data
        open.value = true
        title.value = "修改意见反馈"
    })
}

/** 提交按钮 */
const submitForm = () => {
    proxy.$refs["feedbackRef"].validate(valid => {
        if (valid) {
            if (form.value.feedbackId != null) {
                updateFeedback(form.value).then(response => {
                    proxy.$modal.msgSuccess("修改成功")
                    open.value = false
                    getList()
                })
            } else {
                addFeedback(form.value).then(response => {
                    proxy.$modal.msgSuccess("新增成功")
                    open.value = false
                    getList()
                })
            }
        }
    })
}

/** 删除按钮操作 */
const handleDelete = (row) => {
    const _feedbackIds = row.feedbackId || ids.value
    proxy.$modal.confirm('是否确认删除该项数据？').then(function () {
        return delFeedback(_feedbackIds)
    }).then(() => {
        getList()
        proxy.$modal.msgSuccess("删除成功")
    }).catch(() => {
    })
}

/** 导出按钮操作 */
const handleExport = () => {
    proxy.download('canteen/feedback/export', {
        ...queryParams.value
    }, `feedback_${new Date().getTime()}.xlsx`)
}

getList()
</script>
