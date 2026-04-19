// 导入所有模块的接口
import * as loginApi from './login'
import * as profileApi from './profile'
import * as dishApi from './dish'
import * as orderApi from './order'
import * as feedbackApi from './feedback'

// 统一导出
export default {
    login: loginApi,
    profile: profileApi,
    dish: dishApi,
    order: orderApi,
    feedback: feedbackApi
}

// 也支持按需导出（可选）
export { loginApi, profileApi, dishApi, orderApi, feedbackApi }