import axios from 'axios'
import { ElMessage, ElMessageBox } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'

// 创建axios实例
const service = axios.create({
    baseURL: import.meta.env.VITE_APP_BASE_API || '/dev-api', // 基础路径，和vue-user一致
    timeout: 5000, // 请求超时时间
    headers: {
        'Content-Type': 'application/json;charset=utf-8'
    }
})

// 请求拦截器
service.interceptors.request.use(
    config => {
        // 添加token，和vue-user鉴权逻辑一致
        if (getToken()) {
            config.headers['Authorization'] = 'Bearer ' + getToken()
        }
        return config
    },
    error => {
        console.error('请求错误:', error)
        return Promise.reject(error)
    }
)

// 响应拦截器
service.interceptors.response.use(
    response => {
        const res = response.data
        // 业务状态码判断，和vue-user一致（根据后端实际码值调整）
        if (res.code !== 200) {
            ElMessage({
                message: res.msg || '请求失败',
                type: 'error',
                duration: 5 * 1000
            })

            // 401未登录/令牌过期，和vue-user一致
            if (res.code === 401) {
                ElMessageBox.confirm(
                    '登录状态已过期，请重新登录',
                    '系统提示',
                    {
                        confirmButtonText: '重新登录',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }
                ).then(() => {
                    store.dispatch('user/logout').then(() => {
                        location.reload()
                    })
                })
            }
            return Promise.reject(new Error(res.msg || '请求失败'))
        } else {
            return res
        }
    },
    error => {
        console.error('响应错误:', error)
        ElMessage({
            message: error.msg || '服务器错误',
            type: 'error',
            duration: 5 * 1000
        })
        return Promise.reject(error)
    }
)

export default service