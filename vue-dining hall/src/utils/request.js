import axios from 'axios'
import { getToken } from './auth'
import { ElMessage } from 'element-plus'

const service = axios.create({
    baseURL: '/api',
    timeout: 10000
})

service.interceptors.request.use(config => {
    const token = getToken()
    if (token) config.headers.Authorization = 'Bearer ' + token
    return config
})

service.interceptors.response.use(res => {
    return res.data
}, err => {
    ElMessage.error(err.message || '请求失败')
    return Promise.reject(err)
})

export default service