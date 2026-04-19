import request from '@/utils/request'

/**
 * 获取订单列表（分页+筛选）
 * @param {Object} params - 筛选参数
 * @param {number} params.pageNum - 页码
 * @param {number} params.pageSize - 每页条数
 * @param {string} [params.orderNo] - 订单编号
 * @param {number} [params.status] - 状态（0-待支付 1-已支付 2-已完成 3-已取消）
 * @param {string} [params.startDate] - 开始日期
 * @param {string} [params.endDate] - 结束日期
 * @returns {Promise}
 */
export const getOrderListApi = (params) => {
    return request({
        url: '/diningHall/order/list',
        method: 'get',
        params
    })
}

/**
 * 获取订单详情
 * @param {string} id - 订单ID
 * @returns {Promise}
 */
export const getOrderDetailApi = (id) => {
    return request({
        url: `/diningHall/order/detail/${id}`,
        method: 'get'
    })
}

/**
 * 标记订单完成
 * @param {string} id - 订单ID
 * @returns {Promise}
 */
export const completeOrderApi = (id) => {
    return request({
        url: `/diningHall/order/complete/${id}`,
        method: 'post'
    })
}

/**
 * 取消订单
 * @param {string} id - 订单ID
 * @returns {Promise}
 */
export const cancelOrderApi = (id) => {
    return request({
        url: `/diningHall/order/cancel/${id}`,
        method: 'post'
    })
}

/**
 * 订单统计（今日/本周/本月）
 * @param {string} type - 统计类型（today/week/month）
 * @returns {Promise}
 */
export const getOrderStatistics = (type) => {
    return request({
        url: `/diningHall/order/statistics/${type}`,
        method: 'get'
    })
}