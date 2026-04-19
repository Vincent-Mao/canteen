import Vue from 'vue'
import App from './App.vue'
// 取消注释，恢复路由和状态管理
import router from '@/router/index.js'
import store from '@/store/index.js'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

Vue.use(ElementUI)
Vue.config.productionTip = false

// 恢复完整的 Vue 实例
new Vue({
    el: '#app',
    router, // 恢复路由
    store,  // 恢复状态管理
    render: h => h(App)
})