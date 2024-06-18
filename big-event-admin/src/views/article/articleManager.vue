<script setup>
import PageContainer from '@/components/pageContainer.vue'
import { Delete, Edit } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { artDelChannelService, getArticles } from '@/api/article.js'
import { jwtDecode } from 'jwt-decode'
import ChannelSelect from '@/views/article/components/ChannelSelect.vue'
import ArticleEdit from '@/views/article/components/ArticleEdit.vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const isLoading = ref(false)
const articleList = ref([])
const selectForm = ref({
  pageNum: 1,
  pageSize: 5,
  category: '',
  state: ''
})
const total = ref()
const articleEditRef = ref()

const getArticleList = async () => {
  isLoading.value = true
  articleList.value = []
  const res = await getArticles(selectForm.value)
  for (let i = 0; i < res.data.length - 1; i++) {
    articleList.value.push(jwtDecode(res.data[i]))
    articleList.value[i].state =
      articleList.value[i].state === 0 ? '未发布' : '已发布'
  }
  total.value = parseInt(res.data[res.data.length - 1])
  isLoading.value = false
}
getArticleList()

const onSearch = () => {
  selectForm.value.pageNum = 1
  getArticleList()
}

const onReset = () => {
  selectForm.value.pageNum = 1
  selectForm.value.category = ''
  selectForm.value.state = ''
  getArticleList()
}

const onSizeChange = (size) => {
  selectForm.value.pageNum = 1
  selectForm.value.pagesize = size
  getArticleList()
}
const onCurrentChange = (page) => {
  selectForm.value.pagenum = page
  getArticleList()
}

const onAddArticle = () => {
  articleEditRef.value.open({})
}
const onEditArticle = (row) => {
  articleEditRef.value.open(row)
}
const onDeleteArticle = async (row) => {
  await ElMessageBox.confirm('你确认删除该文章吗？', '温馨提示', {
    type: 'warning',
    confirmButtonText: '确认',
    cancelButtonText: '取消'
  })
  await artDelChannelService(row.articleUID)
  ElMessage({ type: 'success', message: '删除成功' })
  await getArticles(selectForm.value)
}

const onSuccess = (type) => {
  if (type === 'add') {
    // 如果是添加，需要跳转渲染最后一页，编辑直接渲染当前页
    selectForm.value.pagenum = Math.ceil(
      (total.value + 1) / selectForm.value.pageSize
    )
  }
  getArticleList()
}
</script>

<template>
  <page-container title="文章管理">
    <template #extra>
      <el-button type="primary" @click="onAddArticle">添加文章</el-button>
    </template>
    <el-form inline style="width: 100%">
      <el-form-item label="文章分类：">
        <channel-select
          v-model="selectForm.category"
          style="width: 240px"
        ></channel-select>
      </el-form-item>
      <el-form-item label="发布状态：">
        <el-select v-model="selectForm.state" clearable style="width: 240px">
          <el-option label="已发布" value="已发布"></el-option>
          <el-option label="未发布" value="未发布"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSearch">搜索</el-button>
        <el-button @click="onReset">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="articleList" v-loading="isLoading" style="width: 100%">
      <el-table-column label="文章标题" width="200">
        <template #default="{ row }">
          <el-link type="primary" :underline="false">{{ row.title }}</el-link>
        </template>
      </el-table-column>
      <el-table-column label="分类" prop="category"></el-table-column>
      <el-table-column label="发表时间" prop="createTime"> </el-table-column>
      <el-table-column label="最后修改时间" prop="modifyTime">
      </el-table-column>
      <el-table-column label="状态" prop="state"></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button
            :icon="Edit"
            circle
            plain
            type="primary"
            @click="onEditArticle(row)"
          ></el-button>
          <el-button
            :icon="Delete"
            circle
            plain
            type="danger"
            @click="onDeleteArticle(row)"
          ></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
    <el-pagination
      v-model:current-page="selectForm.pageNum"
      v-model:page-size="selectForm.pageSize"
      :page-sizes="[5, 10, 15, 20]"
      :background="true"
      layout="jumper ,total, sizes, prev, pager, next"
      :total="total"
      @size-change="onSizeChange"
      @current-change="onCurrentChange"
      style="margin: 20px 5px; justify-content: flex-end"
    />

    <article-edit ref="articleEditRef" @success="onSuccess"></article-edit>
  </page-container>
</template>

<style scoped></style>
