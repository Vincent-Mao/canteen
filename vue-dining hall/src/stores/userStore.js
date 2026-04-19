import { defineStore } from 'pinia'
import { getToken, setToken, removeToken, getUserInfo, setUserInfo, removeUserInfo } from '@/utils/auth'

export const useUserStore = defineStore('user', {
    state: () => ({
        token: getToken() || '', // 登录token
        userInfo: getUserInfo() || {} // 商家信息
    }),
    actions: {
        // 登录：存储token和用户信息
        login({ token, userInfo }) {
            this.token = token
            this.userInfo = userInfo
            setToken(token)
            setUserInfo(userInfo)
        },
        // 退出登录：清空状态和本地存储
        logout() {
            this.token = ''
            this.userInfo = {}
            removeToken()
            removeUserInfo()
        },
        // 更新用户信息
        updateUserInfo(newInfo) {
            this.userInfo = { ...this.userInfo, ...newInfo }
            setUserInfo(this.userInfo)
        }
    }
})