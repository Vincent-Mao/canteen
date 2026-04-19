import request from '@/utils/request'

// 商家登录
export const login = (data) => {
    return request({
        url: '/diningHall/login',
        method: 'post',
        data
    })
}

// 商家注册
export const register = (data) => {
    return request({
        url: '/diningHall/register',
        method: 'post',
        data
    })
}