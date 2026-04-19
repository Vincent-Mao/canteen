import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs' // 中文语言包
import App from './App.vue'
import router from './router'
import './assets/css/global.css' // 全局样式

const app = createApp(App)
app.use(createPinia())
app.use(ElementPlus, { locale: zhCn }) // 配置Element Plus中文
app.use(router)
app.mount('#app')