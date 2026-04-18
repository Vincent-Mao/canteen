import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    { path: '/', redirect: '/dining/login' },
    { path: '/dining/login', name: 'DiningLogin', component: () => import('@/views/dining/Login.vue') },
    { path: '/dining/register', name: 'DiningRegister', component: () => import('@/views/dining/Register.vue') },
    {
        path: '/dining',
        component: () => import('@/views/dining/Layout.vue'),
        children: [
            { path: 'index', name: 'DiningIndex', component: () => import('@/views/dining/index.vue') },
            { path: 'profile', name: 'DiningProfile', component: () => import('@/views/dining/Profile.vue') }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router