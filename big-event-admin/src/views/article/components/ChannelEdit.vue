<script setup>
import { ref } from 'vue'
import {
  addArticleChannelService,
  updateArticleChannelService
} from '@/api/article.js'
import { ElMessage } from 'element-plus'
const dialogVisible = ref(false)

const formRef = ref()

const formModel = ref({
  articleUID: '',
  title: '',
  category: '',
  description: ''
})

const categoryList_ = ref([])

const rules = {
  title: [
    { required: true, message: '请输入标题', trigger: 'blur' },
    {
      pattern: /^\S{2,30}$/,
      message: '标题必须是2-30位的非空字符',
      trigger: 'blur' | 'change'
    }
  ],
  category: [
    { required: true, message: '请输入文章类别', trigger: 'blur' },
    {
      pattern: /^\S{1,10}$/,
      message: '类别必须是1-10位的非空字符',
      trigger: 'blur' | 'change'
    }
  ],
  description: [
    { required: true, message: '请输入文章描述', trigger: 'blur' },
    {
      pattern: /^\S+$/,
      message: '描述不能为空!',
      trigger: 'blur' | 'change'
    }
  ]
}

const open = async (row, category, categoryList) => {
  dialogVisible.value = true
  formModel.value = { ...row }
  formModel.value.category = category
  categoryList_.value = { ...categoryList }
}

const emit = defineEmits(['success'])

const onSubmit = async () => {
  await formRef.value.validate()
  formModel.value.articleUID
    ? await updateArticleChannelService(formModel.value)
    : await addArticleChannelService(formModel.value)
  ElMessage({
    type: 'success',
    message: formModel.value.articleUID ? '编辑成功' : '添加成功'
  })
  emit('success')
  dialogVisible.value = false
}

defineExpose({
  open
})
</script>

<template>
  <el-dialog
    v-model="dialogVisible"
    :title="formModel.articleUID ? '修改文章' : '添加文章'"
    width="30%"
  >
    <el-form
      ref="formRef"
      :model="formModel"
      :rules="rules"
      label-width="100px"
      style="padding-right: 30px"
    >
      <el-form-item label="文章标题" prop="title">
        <el-input
          v-model="formModel.title"
          minlength="1"
          maxlength="15"
        ></el-input>
      </el-form-item>
      <el-form-item label="文章类别">
        <el-select
          v-model="formModel.category"
          filterable
          allow-create
          placeholder=""
        >
          <el-option
            v-for="item in categoryList_"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="文章简要" prop="description">
        <el-input
          v-model="formModel.description"
          maxlength="100"
          autosize
          show-word-limit
          type="textarea"
          resize="none"
        ></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="onSubmit"> 确认 </el-button>
      </span>
    </template>
  </el-dialog>
</template>
