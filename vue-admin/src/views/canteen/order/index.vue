<template>
    <div class="app-container">
        <!-- 顶部搜索 -->
        <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
            <el-form-item label="订单号" prop="orderId">
                <el-input
                        v-model="queryParams.orderId"
                        placeholder="请输入订单号"
                        clearable
                        @keyup.enter="handleQuery"
                />
            </el-form-item>
            <el-form-item label="状态" prop="status">
                <el-select style="width: 200px;" v-model="queryParams.status" placeholder="请选择状态" clearable>
                    <el-option
                            v-for="dict in order_status"
                            :key="dict.value"
                            :label="dict.label"
                            :value="dict.value"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="下单用户" prop="userName">
                <el-input
                        v-model="queryParams.userName"
                        placeholder="请输入下单用户"
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
                  border v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center"/>
            <el-table-column label="序号" align="center" type="index" :index="indexMethod"/>
            <el-table-column label="订单号" align="center" prop="orderId">
                <template #default="scope">
                    <el-link type="primary" @click="goToDetail(scope.row.orderId)">{{ scope.row.orderId }}</el-link>
                </template>
            </el-table-column>
            <el-table-column label="总价" align="center" prop="totalPrice"/>
            <el-table-column label="状态" align="center" prop="status">
                <template #default="scope">
                    <dict-tag :options="order_status" :value="scope.row.status"/>
                </template>
            </el-table-column>
            <el-table-column label="下单用户" align="center" prop="userName"/>
            <el-table-column label="下单时间" align="center" prop="createTime"/>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template #default="scope">
                    <el-button v-if="scope.row.status === '制作中'"
                               type="success" @click="handleComplete(scope.row)">
                        制作完成
                    </el-button>
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

        <!-- 添加或修改订单对话框 -->
        <vxe-modal :title="title" v-model="open" width="40%" show-maximize showFooter resize>
            <el-card>
                <el-form ref="orderRef" :model="form" :rules="rules" label-width="80px">

                    <el-form-item label="总价" prop="totalPrice">
                        <el-input v-model="form.totalPrice" placeholder="请输入总价"/>
                    </el-form-item>
                    <el-divider content-position="center">订单菜品信息</el-divider>
                    <el-row :gutter="10" class="mb8">
                        <el-col :span="1.5">
                            <el-button type="primary" icon="Plus" @click="handleAddOrderOd">添加</el-button>
                        </el-col>
                        <el-col :span="1.5">
                            <el-button type="danger" icon="Delete" @click="handleDeleteOrderOd">删除</el-button>
                        </el-col>
                    </el-row>
                    <el-table :data="orderOdList" :row-class-name="rowOrderOdIndex" height="30vh"
                              @selection-change="handleOrderOdSelectionChange" ref="orderOd">
                        <el-table-column type="selection" width="50" align="center"/>
                        <el-table-column label="序号" align="center" prop="index" width="50"/>
                        <el-table-column label="菜品名称" prop="name">
                            <template #default="scope">
                                <el-input v-model="scope.row.name" placeholder="请输入菜品名称"/>
                            </template>
                        </el-table-column>
                        <el-table-column label="数量" prop="quantity" width="150">
                            <template #default="scope">
                                <el-input v-model="scope.row.quantity" placeholder="请输入数量"/>
                            </template>
                        </el-table-column>
                        <el-table-column label="单价" prop="price" width="150">
                            <template #default="scope">
                                <el-input v-model="scope.row.price" placeholder="请输入单价"/>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-form>
            </el-card>
            <template #footer>
                <div class="dialog-footer">
                    <el-button type="primary" @click="submitForm">确 定</el-button>
                    <el-button @click="cancel">取 消</el-button>
                </div>
            </template>
        </vxe-modal>

        <!-- 订单详情组件 -->
        <OrderDetail ref="orderDetail"/>
    </div>
</template>

<script setup name="Order">
import {listOrder, getOrder, delOrder, addOrder, updateOrder} from "@/api/canteen/order"
import {getToken} from "@/utils/auth.js";
import OrderDetail from "@/views/canteen/order/OrderDetail.vue";
import {ElMessage, ElMessageBox} from "element-plus";

const baseURL = import.meta.env.VITE_APP_BASE_API

const {proxy} = getCurrentInstance()
const {order_status} = proxy.useDict('order_status')

const orderList = ref([])
const orderOdList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const checkedOrderOd = ref([])
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
        orderId: null,
        status: null,
        userId: null,
        userName: null
    },
    rules: {
        totalPrice: [
            {required: true, message: "总价不能为空", trigger: "blur"}
        ],
        status: [
            {required: true, message: "状态不能为空", trigger: "change"}
        ],
        userId: [
            {required: true, message: "用户ID不能为空", trigger: "blur"}
        ],
    }
})

const {queryParams, form, rules} = toRefs(data)

//制作完成
const handleComplete = (row) => {
    ElMessageBox.confirm(
        '确认已经制作完成了吗?',
        '提示',
        {confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning',}
    )
        .then(() => {
            const item = {
                orderId: row.orderId,
                status: '待取餐',
                orderOdList: row.orderOdList
            }
            updateOrder(item).then(res => {
                getList()
                ElMessage.success('制作完成~')
            })
        })
}

//订单详情组件实例
const orderDetail = ref(null)

//打开订单详情
const goToDetail = (orderId) => {
    orderDetail.value.handleOpen(orderId)
}

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

/** 查询订单列表 */
const getList = () => {
    loading.value = true
    listOrder(queryParams.value).then(response => {
        orderList.value = response.rows
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
        orderId: null,
        totalPrice: null,
        status: null,
        userId: null,
        createTime: null
    }
    orderOdList.value = []
    proxy.resetForm("orderRef")
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
    ids.value = selection.map(item => item.orderId)
    single.value = selection.length != 1
    multiple.value = !selection.length
}

/** 新增按钮操作 */
const handleAdd = () => {
    reset()
    open.value = true
    title.value = "添加订单"
}

/** 修改按钮操作 */
const handleUpdate = (row) => {
    reset()
    const _orderId = row.orderId || ids.value
    getOrder(_orderId).then(response => {
        form.value = response.data
        orderOdList.value = response.data.orderOdList
        open.value = true
        title.value = "修改订单"
    })
}

/** 提交按钮 */
const submitForm = () => {
    proxy.$refs["orderRef"].validate(valid => {
        if (valid) {
            form.value.orderOdList = orderOdList.value
            if (form.value.orderId != null) {
                updateOrder(form.value).then(response => {
                    proxy.$modal.msgSuccess("修改成功")
                    open.value = false
                    getList()
                })
            } else {
                addOrder(form.value).then(response => {
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
    const _orderIds = row.orderId || ids.value
    proxy.$modal.confirm('是否确认删除该项数据？').then(function () {
        return delOrder(_orderIds)
    }).then(() => {
        getList()
        proxy.$modal.msgSuccess("删除成功")
    }).catch(() => {
    })
}

/** 订单菜品序号 */
const rowOrderOdIndex = ({row, rowIndex}) => {
    row.index = rowIndex + 1
}

/** 订单菜品添加按钮操作 */
const handleAddOrderOd = () => {
    let obj = {}
    obj.name = ""
    obj.image = ""
    obj.quantity = ""
    obj.price = ""
    orderOdList.value.push(obj)
}

/** 订单菜品删除按钮操作 */
const handleDeleteOrderOd = () => {
    if (checkedOrderOd.value.length == 0) {
        proxy.$modal.msgError("请先选择要删除的订单菜品数据")
    } else {
        const orderOds = orderOdList.value
        const checkedOrderOds = checkedOrderOd.value
        orderOdList.value = orderOds.filter(function (item) {
            return checkedOrderOds.indexOf(item.index) == -1
        })
    }
}

/** 复选框选中数据 */
const handleOrderOdSelectionChange = (selection) => {
    checkedOrderOd.value = selection.map(item => item.index)
}

/** 导出按钮操作 */
const handleExport = () => {
    proxy.download('canteen/order/export', {
        ...queryParams.value
    }, `order_${new Date().getTime()}.xlsx`)
}

getList()
</script>
