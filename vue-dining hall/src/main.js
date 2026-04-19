import Vue from 'vue'
import App from './App.vue'
import router from '@/router/index.js'
// 确保store路径正确
import store from '@/store/index.js'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

Vue.use(ElementUI)
Vue.config.productionTip = false

new Vue({
    el: '#app',
    router,
    store,
    render: h => h(App)
})