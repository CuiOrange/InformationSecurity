import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus' // Vue 3 中使用 Element Plus
import 'element-plus/dist/index.css'   // Element Plus 的样式
import './assets/gloable.css'
import request from "@/util/request";

const app = createApp(App)

// 注册全局变量
app.config.globalProperties.$request = request

// 注册插件
app.use(router)
app.use(store)
app.use(ElementPlus)

// 挂载应用
app.mount('#app')
