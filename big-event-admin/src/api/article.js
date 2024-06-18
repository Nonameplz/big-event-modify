import request from '@/utils/request.js'

export const getArticles = (params) =>
  request.get('my/article/list', { params })

export const getCategories = () => request.get('my/article/category')

export const addArticleChannelService = (data) =>
  request.post('my/article/addNew', data)

export const updateArticleChannelService = (data) =>
  request.put('my/article/update', data)

export const artDelChannelService = (articleUID) =>
  request.delete('/my/article/del', {
    params: { articleUID }
  })

export const artPublishService = (data) => request.post('/my/article/add', data)
