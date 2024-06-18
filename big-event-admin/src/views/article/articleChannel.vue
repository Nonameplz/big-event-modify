<script setup>
import PageContainer from '@/components/pageContainer.vue'
import { onMounted, ref } from 'vue'
import { artDelChannelService, getArticles } from '@/api/article.js'
import { jwtDecode } from 'jwt-decode'
import { Delete, Edit } from '@element-plus/icons-vue'
import ChannelEdit from '@/views/article/components/ChannelEdit.vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const isLoading = ref(true)
const dialog = ref()
const articleChannelList = ref([])
const articleCategoryList = ref([])
const categoryList = ref([])

const getArticleChannelList = async () => {
  isLoading.value = true
  articleCategoryList.value = []
  const res = await getArticles()
  for (let i = 0; i < res.data.length; i++) {
    articleChannelList.value[i] = jwtDecode(res.data[i])
    const index = checkCategoryExist(articleChannelList.value[i].category)
    if (typeof index === 'number') {
      articleCategoryList.value[index].articles.push({
        articleUID: articleChannelList.value[i].articleUID,
        title: articleChannelList.value[i].title,
        description: articleChannelList.value[i].description,
        likes: articleChannelList.value[i].likes,
        state: articleChannelList.value[i].state === 0 ? '未发布' : '已发布'
      })
      articleCategoryList.value[index].likesCount +=
        articleChannelList.value[i].likes
    } else {
      const newCategory = {
        category: articleChannelList.value[i].category,
        articles: [
          {
            articleUID: articleChannelList.value[i].articleUID,
            title: articleChannelList.value[i].title,
            description: articleChannelList.value[i].description,
            likes: articleChannelList.value[i].likes,
            state: articleChannelList.value[i].state === 0 ? '未发布' : '已发布'
          }
        ],
        likesCount: articleChannelList.value[i].likes
      }
      articleCategoryList.value.push(newCategory)
      categoryList.value.push({
        value: articleChannelList.value[i].category,
        label: articleChannelList.value[i].category
      })
    }
  }
  isLoading.value = false
}

onMounted(() => {
  getArticleChannelList()
})

const checkCategoryExist = (category) => {
  for (let i = 0; i < articleCategoryList.value.length; i++) {
    if (category === articleCategoryList.value[i].category) {
      return i
    }
  }
  return false
}

const indexMethod = (index) => {
  return index + 1
}

const onAddChannel = () => {
  dialog.value.open({}, '', categoryList.value)
}

const onEditChannel = (row, category) => {
  dialog.value.open(row, category, categoryList.value)
}
const onDelChanel = async (row) => {
  await ElMessageBox.confirm('你确认删除该文章吗？', '温馨提示', {
    type: 'warning',
    confirmButtonText: '确认',
    cancelButtonText: '取消'
  })
  await artDelChannelService(row.articleUID)
  ElMessage({ type: 'success', message: '删除成功' })
  await getArticleChannelList()
}

const onSuccess = () => {
  getArticleChannelList()
}
</script>
<template>
  <page-container title="文章总览">
    <template #extra>
      <div class="flex">
        <div class="addition">
          当前共有:
          <p class="text-red-50 w-4 ml-2">
            {{ articleChannelList.length }}
          </p>
          篇文章
        </div>
        <el-button type="primary" @click="onAddChannel">添加新文章</el-button>
      </div>
    </template>
    <el-table
      :data="articleCategoryList"
      :border="true"
      stripe
      v-loading="isLoading"
      style="width: 100%"
    >
      <el-table-column label="ID" type="index" :index="indexMethod" />
      <el-table-column prop="category" label="category" width="180" />
      <el-table-column label="article_count" width="180">
        <template #default="props">
          {{ props.row.articles.length }}
        </template>
      </el-table-column>
      <el-table-column prop="likesCount" label="likes" />
      <el-table-column type="expand">
        <template #default="props">
          <h3 class="font-bold my-2 bg-gray-300">
            category : {{ props.row.category }}
          </h3>
          <el-table :data="props.row.articles" :border="true">
            <el-table-column label="ID" type="index" :index="indexMethod" />
            <el-table-column label="title" prop="title" />
            <el-table-column label="description" prop="description" />
            <el-table-column label="likes" prop="likes" />
            <el-table-column label="state" prop="state" />
            <el-table-column label="operate" width="150">
              <template #default="{ row, $index }">
                <el-button
                  :icon="Edit"
                  circle
                  plain
                  type="primary"
                  @click="onEditChannel(row, props.row.category)"
                ></el-button>
                <el-button
                  :icon="Delete"
                  circle
                  plain
                  type="danger"
                  @click="onDelChanel(row, $index)"
                ></el-button>
              </template>
            </el-table-column>
          </el-table>
          <h3 class="h-6 bg-gray-300"></h3>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="nothing here" />
      </template>
    </el-table>
  </page-container>
  <channel-edit ref="dialog" @success="onSuccess"></channel-edit>
  <router-view></router-view>
</template>
<style scoped>
.addition {
  background-color: #a9d1ff;
  display: inline-flex;
  align-items: center;
  height: 32px;
  padding: 8px 15px;
  border-radius: 4px;
  margin-right: 4px;
  justify-content: center;
}
</style>
