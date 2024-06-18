import request from '@/utils/request.js'

export const userRegisterService = ({ userName, password, email }) =>
  request.post('/register', { userName, password, email })

export const userLoginService = ({ userName, password }) =>
  request.post('/login', { userName, password })

export const getUserInfoService = () => request.get('/my/userinfo')
