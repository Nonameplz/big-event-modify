import axios from 'axios'
import { useUserStore } from '@/stores/index.js'
import { ElMessage } from 'element-plus'
import router from '@/router/index.js'

const baseURL = 'http://localhost:8080'

const instance = axios.create({
  baseURL,
  timeout: 100000
})

instance.interceptors.request.use(
  (config) => {
    const userStore = useUserStore()
    if (userStore.token) {
      config.headers.Authorization = userStore.token
    }
    return config
  },
  (err) => Promise.reject(err)
)

instance.interceptors.response.use(
  (res) => {
    // 成功
    if (res.data.code === 1) {
      return res.data
    }
    // 失败
    if (res.data.code === 0) {
      if (res.data.msg === 'NOT_LOGIN') {
        ElMessage.error('登录失效或未登录')
        router.push('/login')
        return Promise.reject(res.data)
      }
      ElMessage.error(res.data.msg || '服务异常')
      return Promise.reject(res.data)
    }
    ElMessage.error(res.data.message || '服务异常')
    return Promise.reject(res.data)
  },
  (err) => {
    ElMessage.error(err.response.message || '服务异常')
    return Promise.reject(err)
  }
)

export default instance
