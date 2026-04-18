<template>
    <div class="app-container">
        <!-- 顶部搜索 -->
        <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
            <el-form-item label="菜品名称" prop="name">
                <el-input
                        v-model="queryParams.name"
                        placeholder="请输入菜品名称"
                        clearable
                        @keyup.enter="handleQuery"
                />
            </el-form-item>
            <el-form-item label="菜品分类" prop="categoryId">
                <el-select style="width: 200px" v-model="queryParams.categoryId" placeholder='请选择分类'>
                    <el-option
                        v-for="item in categoryList"
                        :key="item.categoryId"
                        :label="item.name"
                        :value="item.categoryId"
                    />
                </el-select>
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
                  border v-loading="loading" :data="dishesList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center"/>
            <el-table-column label="序号" align="center" type="index" :index="indexMethod"/>
            <el-table-column label="菜品名称" align="center" prop="name"/>
            <el-table-column label="描述" align="center" prop="description"/>
            <el-table-column label="价格" align="center" prop="price"/>
            <el-table-column label="图片" align="center" prop="image" width="100">
                <template #default="scope">
                    <image-preview :src="scope.row.image" :width="50" :height="50"/>
                </template>
            </el-table-column>
            <el-table-column label="菜品分类" align="center" prop="categoryName"/>
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

        <!-- 添加或修改菜品对话框 -->
        <vxe-modal :title="title" v-model="open" width="500px" show-maximize showFooter resize>
            <el-form ref="dishesRef" :model="form" :rules="rules" label-width="80px">
                <el-form-item label="菜品名称" prop="name">
                    <el-input v-model="form.name" placeholder="请输入菜品名称"/>
                </el-form-item>
                <el-form-item label="描述" prop="description">
                    <el-input v-model="form.description" type="textarea" placeholder="请输入内容"/>
                </el-form-item>
                <el-form-item label="价格" prop="price">
                    <el-input v-model="form.price" placeholder="请输入价格"/>
                </el-form-item>
                <el-form-item label="图片" prop="image">
                    <image-upload v-model="form.image"/>
                </el-form-item>
                <el-form-item label="菜品分类" prop="categoryId">
                    <el-select v-model="form.categoryId" placeholder='请选择分类'>
                        <el-option
                                v-for="item in categoryList"
                                :key="item.categoryId"
                                :label="item.name"
                                :value="item.categoryId"
                        />
                    </el-select>
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

<script setup name="Dishes">
import {listDishes, getDishes, delDishes, addDishes, updateDishes} from "@/api/canteen/dishes"
import {getToken} from "@/utils/auth.js";
import {selectAllCategory} from "@/api/canteen/category.js";

const baseURL = import.meta.env.VITE_APP_BASE_API

const {proxy} = getCurrentInstance()

const dishesList = ref([])
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
        name: null,
        categoryId: null
    },
    rules: {
        name: [
            {required: true, message: "菜品名称不能为空", trigger: "blur"}
        ],
        description: [
            {required: true, message: "描述不能为空", trigger: "blur"}
        ],
        price: [
            {required: true, message: "价格不能为空", trigger: "blur"}
        ],
        image: [
            {required: true, message: "图片不能为空", trigger: "blur"}
        ],
        categoryId: [
            {required: true, message: "分类ID不能为空", trigger: "blur"}
        ]
    }
})

const {queryParams, form, rules} = toRefs(data)

//分类列表数据
const categoryList = ref([])

//组件挂载完之后执行
onMounted(() => {
    //查询所有分类
    selectAllCategory().then(res => {
        //将分类列表数据赋值给变量
        categoryList.value = res.data
    })
})

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

/** 查询菜品列表 */
const getList = () => {
    loading.value = true
    listDishes(queryParams.value).then(response => {
        dishesList.value = response.rows
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
        dishesId: null,
        name: null,
        description: null,
        price: null,
        image: null,
        categoryId: null
    }
    proxy.resetForm("dishesRef")
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
    ids.value = selection.map(item => item.dishesId)
    single.value = selection.length != 1
    multiple.value = !selection.length
}

/** 新增按钮操作 */
const handleAdd = () => {
    reset()
    open.value = true
    title.value = "添加菜品"
}

/** 修改按钮操作 */
const handleUpdate = (row) => {
    reset()
    const _dishesId = row.dishesId || ids.value
    getDishes(_dishesId).then(response => {
        form.value = response.data
        open.value = true
        title.value = "修改菜品"
    })
}

/** 提交按钮 */
const submitForm = () => {
    proxy.$refs["dishesRef"].validate(valid => {
        if (valid) {
            if (form.value.dishesId != null) {
                updateDishes(form.value).then(response => {
                    proxy.$modal.msgSuccess("修改成功")
                    open.value = false
                    getList()
                })
            } else {
                addDishes(form.value).then(response => {
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
    const _dishesIds = row.dishesId || ids.value
    proxy.$modal.confirm('是否确认删除该项数据？').then(function () {
        return delDishes(_dishesIds)
    }).then(() => {
        getList()
        proxy.$modal.msgSuccess("删除成功")
    }).catch(() => {
    })
}

/** 导出按钮操作 */
const handleExport = () => {
    proxy.download('canteen/dishes/export', {
        ...queryParams.value
    }, `dishes_${new Date().getTime()}.xlsx`)
}

getList()
</script>
