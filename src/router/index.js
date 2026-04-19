import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/userStore'
import Layout from '@/components/Layout/index.vue'

// 导入页面组件
import Login from '@/views/Login/index.vue'
import Register from '@/views/Register/index.vue'
import DishList from '@/views/Dish/DishList.vue'
import OrderList from '@/views/Order/OrderList.vue'
import FeedbackList from '@/views/Feedback/FeedbackList.vue'
import BasicInfo from '@/views/Profile/BasicInfo.vue'
import Password from '@/views/Profile/Password.vue'

// 路由守卫：验证登录状态
const requireAuth = (to, from, next) => {
    const userStore = useUserStore()
    if (userStore.token) {
        next()
    } else {
        next('/login') // 未登录跳转到登录页
    }
}

const routes = [
    { path: '/', redirect: '/login' },
    // 登录/注册（无需鉴权）
    { path: '/login', name: 'Login', component: Login },
    { path: '/register', name: 'Register', component: Register },
    // 后台布局（需鉴权）
    {
        path: '/dashboard',
        name: 'Layout',
        component: Layout,
        beforeEnter: requireAuth,
        children: [
            { path: 'dish', name: 'Dish', component: DishList, meta: { title: '菜品管理' } },
            { path: 'order', name: 'Order', component: OrderList, meta: { title: '订单管理' } },
            { path: 'feedback', name: 'Feedback', component: FeedbackList, meta: { title: '意见反馈' } },
            { path: 'profile/info', name: 'BasicInfo', component: BasicInfo, meta: { title: '基本信息' } },
            { path: 'profile/password', name: 'Password', component: Password, meta: { title: '修改密码' } }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
})

export default router