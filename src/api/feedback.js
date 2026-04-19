import request from '@/utils/request'

// 获取反馈列表
export const getFeedbackListApi = (params) => {
    return request({
        url: '/diningHall/feedback/list',
        method: 'get',
        params
    })
}

// 获取反馈详情
export const getFeedbackDetailApi = (id) => {
    return request({
        url: `/diningHall/feedback/detail/${id}`,
        method: 'get'
    })
}

// 标记反馈已处理
export const handleFeedbackApi = (id) => {
    return request({
        url: `/diningHall/feedback/handle/${id}`,
        method: 'post'
    })
}