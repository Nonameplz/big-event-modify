import { createApp } from 'vue'

import '@/assets/main.scss'
import '@/assets/tailwindcss.css'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from './router'
import pinia from '@/stores/index.js'

const app = createApp(App)

app.use(pinia)
app.use(router)
app.use(ElementPlus)

app.mount('#app')
