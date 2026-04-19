import request from './request'

// 商家登录
export const login = (data) => {
    return request({
        url: '/dining-hall/login',
        method: 'post',
        data
    })
}

// 商家注册
export const register = (data) => {
    return request({
        url: '/dining-hall/register',
        method: 'post',
        data
    })
}

// 验证码接口
export function getCodeImg() {
    return request({
        url: '/captchaImage',
        method: 'get'
    })
}