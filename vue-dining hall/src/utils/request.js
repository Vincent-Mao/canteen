import axios from 'axios'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useUserStore } from '@/stores/userStore'
import { removeToken, removeUserInfo } from '@/utils/auth'

// 创建axios实例
const service = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL || '/api', // 接口基础地址（在.env文件配置）
    timeout: 5000 // 请求超时时间
})

// 请求拦截器：添加token
service.interceptors.request.use(
    (config) => {
        const userStore = useUserStore()
        if (userStore.token) {
            config.headers['Authorization'] = `Bearer ${userStore.token}`
        }
        return config
    },
    (error) => {
        Promise.reject(error)
    }
)

// 响应拦截器：统一处理错误
service.interceptors.request.use(
    (config) => {
        const userStore = useUserStore()
        // 注册接口跳过token携带
        if (config.url.includes('/diningHall/register')) {
            return config;
        }
        // 其他接口正常携带token
        if (userStore.token) {
            config.headers.Authorization = `Bearer ${userStore.token}`
        }
        return config
    },
    (error) => {
        return Promise.reject(error)
    }
)

// 补充：添加注册接口（api/login.js 中新增）
export const register = (data) => {
    return request({
        url: '/diningHall/register',
        method: 'post',
        data
    })
}

export default service