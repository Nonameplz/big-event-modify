import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/index.js'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      component: () => import('@/views/login/loginPage.vue')
    },
    {
      path: '/',
      component: () => import('@/views/layout/layoutManager.vue'),
      redirect: '/article/manager',
      children: [
        {
          path: '/article/manager',
          component: () => import('@/views/article/articleManager.vue')
        },
        {
          path: '/article/channel',
          component: () => import('@/views/article/articleChannel.vue')
        },
        {
          path: '/user/profile',
          component: () => import('@/views/user/userProfile.vue')
        },
        {
          path: '/user/avatar',
          component: () => import('@/views/user/userAvatar.vue')
        },
        {
          path: '/user/resetPassword',
          component: () => import('@/views/user/resetPassword.vue')
        }
      ]
    }
  ]
})

router.beforeEach((to) => {
  const UserStore = useUserStore()
  if (!UserStore.token && to.path !== '/login') {
    return '/login'
  }
})
export default router
