import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
    mode: 'history',
    base: import.meta.env.BASE_URL,
    routes: [
        {
            path: '/',
            redirect: '/login'
        },
        {
            path: '/login',
            name: 'Login',
            component: () => import('@/views/Login/Login.vue').catch(() => ({
                template: '<div>登录页加载中...</div>'
            }))
        },
        {
            path: '/register',
            name: 'Register',
            component: () => import('@/views/Register/Register.vue').catch(() => ({
                template: '<div>注册页加载中...</div>'
            }))
        },
        // 新增主页路由（核心：登录后跳转的目标页）
        {
            path: '/home',
            name: 'Home',
            component: () => import('@/views/Home/Home.vue').catch(() => ({
                template: '<div>主页加载中...</div>'
            })),
            meta: { requireAuth: true } // 需要登录才能访问
        }
    ]
})