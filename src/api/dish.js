import request from '@/utils/request'

// 获取菜品列表
export const getDishList = (params) => {
    return request({
        url: '/dish/list',
        method: 'get',
        params
    })
}

// 新增菜品
export const addDish = (data) => {
    return request({
        url: '/dish/add',
        method: 'post',
        data
    })
}

// 编辑菜品
export const editDish = (data) => {
    return request({
        url: '/dish/edit',
        method: 'post',
        data
    })
}

// 删除菜品
export const deleteDish = (id) => {
    return request({
        url: `/dish/delete/${id}`,
        method: 'post'
    })
}

// 更新菜品状态
export const updateDishStatus = (data) => {
    return request({
        url: '/dish/updateStatus',
        method: 'post',
        data
    })
}