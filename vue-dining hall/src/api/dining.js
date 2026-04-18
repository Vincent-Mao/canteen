import request from '@/utils/request'

export function login(data) {
    return request({ url: '/dining/login', method: 'post', data })
}

export function register(data) {
    return request({ url: '/dining/register', method: 'post', data })
}

export function getInfo() {
    return request({ url: '/dining/info', method: 'get' })
}

export function updateProfile(data) {
    return request({ url: '/dining/profile', method: 'put', data })
}

export function updatePwd(data) {
    return request({ url: '/dining/updatePwd', method: 'put', data })
}