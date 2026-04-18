import request from '@/utils/request'

//查询顶部数据统计
export function selectHomeCount() {
    return request({
        url: '/home/page/selectHomeCount',
        method: 'get'
    })
}

//查询销售趋势数据
export function selectSalesChart() {
    return request({
        url: '/home/page/selectSalesChart',
        method: 'get'
    })
}

//查询订单状态分布数据
export function selectOrderStatusChart() {
    return request({
        url: '/home/page/selectOrderStatusChart',
        method: 'get'
    })
}
