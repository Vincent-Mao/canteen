import request from './request' // 复用axios实例

// 商家登录接口
export const login = (data) => {
    return request({
        url: '/dining-hall/login',
        method: 'post',
        data
    })
}

// 商家注册接口
export const register = (data) => {
    return request({
        url: '/dining-hall/register',
        method: 'post',
        data
    })
}