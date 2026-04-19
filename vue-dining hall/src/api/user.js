import request from './request' // 复用上面的axios实例

// 获取商家个人信息（含窗口位置）
export const getProfile = () => {
    return request({
        url: '/dining-hall/profile',
        method: 'get'
    })
}

// 修改商家个人信息（含窗口位置）
export const updateProfile = (data) => {
    return request({
        url: '/dining-hall/profile',
        method: 'put',
        data
    })
}