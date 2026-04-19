// token相关key
const TOKEN_KEY = 'dining_hall_token'
const USER_INFO_KEY = 'dining_hall_user_info'

// 存储token
export const setToken = (token) => {
    localStorage.setItem(TOKEN_KEY, token)
}

// 获取token
export const getToken = () => {
    return localStorage.getItem(TOKEN_KEY)
}

// 删除token
export const removeToken = () => {
    localStorage.removeItem(TOKEN_KEY)
}

// 存储用户信息
export const setUserInfo = (info) => {
    localStorage.setItem(USER_INFO_KEY, JSON.stringify(info))
}

// 获取用户信息
export const getUserInfo = () => {
    const info = localStorage.getItem(USER_INFO_KEY)
    return info ? JSON.parse(info) : {}
}

// 删除用户信息
export const removeUserInfo = () => {
    localStorage.removeItem(USER_INFO_KEY)
}