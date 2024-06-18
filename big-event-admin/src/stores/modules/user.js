import { defineStore } from 'pinia'
import { ref } from 'vue'
import { getUserInfoService } from '@/api/user.js'
import { jwtDecode } from 'jwt-decode'

export const useUserStore = defineStore(
  'using-user',
  () => {
    const token = ref('')
    const setToken = (newToken) => {
      token.value = newToken
    }
    const removeToken = () => {
      token.value = ''
    }

    const user = ref({})

    const getUser = async () => {
      const res = await getUserInfoService()
      const jwtToken = res.data
      user.value = jwtDecode(jwtToken)
    }

    const setUser = (obj) => {
      user.value = obj
    }
    return {
      token,
      setToken,
      removeToken,
      user,
      getUser,
      setUser
    }
  },
  {
    persist: true
  }
)
