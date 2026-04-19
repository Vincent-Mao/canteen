import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/Login/Login.vue'
import Register from '@/views/Register/Register.vue'
import MainLayout from '@/components/Layout/MainLayout.vue'

Vue.use(Router)

export default new Router({
    mode: 'hash',
    base: '/',
    routes: [
        { path: '/', redirect: '/login' },
        { path: '/login', name: 'Login', component: Login },
        { path: '/register', name: 'Register', component: Register },
        {
            path: '/home',
            component: MainLayout,
            meta: { requireAuth: true },
            children: [
                // 主页：极简提示，无多余内容
                {
                    path: '',
                    component: {
                        template: `
              <div style="width:100%;height:100%;display:flex;flex-direction:column;justify-content:center;align-items:center;background:#f5f7fa;">
                <h1 style="color:#2c3e50;margin-bottom:20px;">商家管理系统</h1>
                <p style="color:#666;font-size:16px;">请选择顶部导航栏功能进行操作</p>
              </div>
            `
                    }
                },
                // 加载你的 MenuManage.vue 文件
                {
                    path: 'menu-manage',
                    component: () => import('@/views/MenuManage/MenuManage.vue')
                },
                // 加载你的 OrderManage.vue 文件
                {
                    path: 'order-manage',
                    component: () => import('@/views/OrderManage/OrderManage.vue')
                },
                // 加载你的 Feedback.vue 文件
                {
                    path: 'feedback',
                    component: () => import('@/views/Feedback/Feedback.vue')
                },
                // 加载你的 Profile.vue 文件
                {
                    path: 'profile',
                    component: () => import('@/views/Profile/Profile.vue')
                }
            ]
        },
        { path: '*', redirect: '/login' }
    ]
})