import axios from 'axios'

// 创建axios实例（统一配置后端接口、超时、请求拦截器等）
const request = axios.create({
    // 匹配 vite.config.js 中的代理前缀，自动转发到后端
    baseURL: import.meta.env.VITE_APP_BASE_API || '/dev-api',
    timeout: 5000 // 请求超时时间
})

// 请求拦截器：统一携带token
request.interceptors.request.use(
    (config) => {
        // 从localStorage获取商家token，添加到请求头
        const token = localStorage.getItem('diningHallToken')
        if (token) {
            config.headers.Authorization = `Bearer ${token}`
        }
        return config
    },
    (error) => {
        return Promise.reject(error)
    }
)

// 响应拦截器：统一处理错误
request.interceptors.response.use(
    (response) => {
        // 只返回响应数据，简化业务层调用
        return response.data
    },
    (error) => {
        // 统一错误提示
        console.error('请求失败：', error.message)
        return Promise.reject(error)
    }
)

export default request