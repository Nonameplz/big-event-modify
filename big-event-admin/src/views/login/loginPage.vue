<script setup>
import { User, Lock, Message } from '@element-plus/icons-vue'
import { ref, watch } from 'vue'
import { userLoginService, userRegisterService } from '@/api/user.js'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/index.js'
import { useRouter } from 'vue-router'

const isRegister = ref(false)

const form = ref()

const formModel = ref({
  userName: '',
  password: '',
  rePassword: '',
  email: ''
})

const rules = {
  userName: [
    { required: true, message: '清输入用户名', trigger: 'blur' },
    {
      min: 6,
      max: 20,
      message: '用户名长度必须为6 - 20位的 字符',
      trigger: 'blur'
    }
  ],
  password: [
    { required: true, message: '密码不能为空', trigger: 'blur' },
    {
      pattern: /^(?=.*[A-Z])(?=.*\d)[\w@]{6,20}$/,
      message:
        '密码长度必须为6 - 20位 数字 英文 至少存在1个大写英文的组合字符串(可包含@_)',
      trigger: 'blur' | 'change'
    }
  ],
  rePassword: [
    {
      validator: (rule, value, callback) => {
        if (value !== formModel.value.password) {
          callback(new Error('两次密码不匹配!'))
        } else {
          callback()
        }
      }
    }
  ],
  email: [
    {
      required: true,
      message: '请输入邮箱地址',
      trigger: 'blur'
    },
    {
      type: 'email',
      message: '请输入正确的邮箱地址',
      trigger: ['blur', 'change']
    }
  ]
}

const register = async () => {
  await form.value.validate()
  await userRegisterService(formModel.value)
  ElMessage.success('注册成功')
  isRegister.value = false
}

const UserStore = useUserStore()
const router = useRouter()

const login = async () => {
  const res = await userLoginService(formModel.value)
  UserStore.setToken(res.data)
  ElMessage.success('登录成功!')
  await router.push('/')
}

watch(isRegister, () => {
  formModel.value = {
    userName: '',
    password: '',
    rePassword: '',
    email: ''
  }
})
</script>

<template>
  <el-row class="login-page bg">
    <el-col :span="7" class="form">
      <div class="bg-[#ffc0cbcc] px-4 pb-4 pt-6 rounded-2xl">
        <el-form
          ref="form"
          size="large"
          :model="formModel"
          :rules="rules"
          autocomplete="off"
          v-if="isRegister"
        >
          <el-form-item>
            <h1 class="text-3xl font-bold">注册</h1>
          </el-form-item>
          <el-form-item prop="username">
            <el-input
              v-model="formModel.userName"
              :prefix-icon="User"
              placeholder="请输入用户名"
            ></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              :prefix-icon="Lock"
              v-model="formModel.password"
              type="password"
              :show-password="true"
              placeholder="请输入密码"
            ></el-input>
          </el-form-item>
          <el-form-item prop="rePassword">
            <el-input
              :prefix-icon="Lock"
              v-model="formModel.rePassword"
              type="password"
              :show-password="true"
              placeholder="请输入再次密码"
            ></el-input>
          </el-form-item>
          <el-form-item prop="email">
            <el-input
              :prefix-icon="Message"
              v-model="formModel.email"
              type="text"
              placeholder="请输入邮箱"
            ></el-input>
          </el-form-item>

          <el-form-item>
            <el-button
              class="button"
              type="primary"
              auto-insert-space
              @click="register"
            >
              注册
            </el-button>
          </el-form-item>
          <el-form-item class="flex">
            <el-link type="info" :underline="false" @click="isRegister = false">
              ← 返回
            </el-link>
          </el-form-item>
        </el-form>
        <el-form
          ref="form"
          :model="formModel"
          size="large"
          autocomplete="off"
          v-else
        >
          <el-form-item>
            <h1 class="text-3xl font-bold">登录</h1>
          </el-form-item>
          <el-form-item>
            <el-input
              :prefix-icon="User"
              v-model="formModel.userName"
              placeholder="请输入用户名"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-input
              name="password"
              :prefix-icon="Lock"
              v-model="formModel.password"
              type="password"
              placeholder="请输入密码"
            ></el-input>
          </el-form-item>
          <el-form-item class="flex">
            <div class="flex">
              <el-checkbox>记住我</el-checkbox>
              <el-link type="primary" :underline="false">忘记密码？</el-link>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button
              class="button"
              @click="login"
              type="primary"
              auto-insert-space
              >登录</el-button
            >
          </el-form-item>
          <el-form-item class="flex">
            <el-link type="info" :underline="false" @click="isRegister = true">
              注册 →
            </el-link>
          </el-form-item>
        </el-form>
      </div>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
.login-page {
  height: 100vh;
  background-color: #fff;
  justify-content: center;
  .form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    user-select: none;
    .title {
      margin: 0 auto;
    }
    .button {
      width: 100%;
    }
    .flex {
      width: 100%;
      display: flex;
      justify-content: space-between;
    }
  }
}
.bg {
  background:
    //url('@/assets/images/logo2.png') no-repeat 60% center / 240px auto,
    url('@/assets/images/loginBg.png') no-repeat center / cover;
  border-radius: 0 20px 20px 0;
}
</style>
