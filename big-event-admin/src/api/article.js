import request from '@/utils/request.js'

export const getArticles = (params) => request.get('my/cate/list', { params })

export const getCategories = () => request.get('my/cate/category')

export const addArticleChannelService = (data) =>
  request.post('my/cate/addNew', data)

export const updateArticleChannelService = (data) =>
  request.put('my/cate/update', data)

export const artDelChannelService = (articleUID) =>
  request.delete('/my/cate/del', {
    params: { articleUID }
  })

export const artPublishService = (data) => request.post('/my/article/add', data)
