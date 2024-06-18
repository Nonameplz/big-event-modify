<script setup>
import { ref } from 'vue'
import ChannelSelect from '@/views/article/components/ChannelSelect.vue'
import { Plus } from '@element-plus/icons-vue'
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'
import { artPublishService } from '@/api/article.js'
import { ElMessage } from 'element-plus'

const visibleDrawer = ref(false)

const formModel = ref({
  articleUID: '',
  title: '',
  category: '',
  description: '',
  cover_image: '',
  content: '',
  state: ''
})

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

const imgUrl = ref('')
const editorRef = ref()
const onUploadFile = (uploadFile) => {
  imgUrl.value = URL.createObjectURL(uploadFile.raw)
  formModel.value.cover_image = uploadFile.raw
}

const open = (row) => {
  visibleDrawer.value = true
  formModel.value = { ...row }
  if (!row.articleUID) {
    formModel.value.cover_Image = ''
    formModel.value.content = ''
  }
  imgUrl.value = ''
}

const emit = defineEmits(['success'])
const onPublish = async (state) => {
  // 将已发布还是草稿状态，存入 state
  formModel.value.state = state

  // // 转换 formData 数据
  const formData = new FormData()

  // 将formDataRef.value的属性和值添加到FormData中
  for (let key in formModel.value) {
    formData.append(key, formModel.value[key])
  }

  if (formModel.value.articleUID) {
    console.log('编辑操作')
  } else {
    // 添加请求
    await artPublishService(formData)
    ElMessage.success('添加成功')
    visibleDrawer.value = false
    emit('success', 'add')
  }
  imgUrl.value = ''
  editorRef.value.setHTML('')
}
defineExpose({
  open
})
</script>

<template>
  <!-- 抽屉 -->
  <el-drawer
    v-model="visibleDrawer"
    :title="formModel.articleUID ? '编辑文章' : '添加文章'"
    direction="rtl"
    size="50%"
  >
    <!-- 发表文章表单 -->
    <el-form
      :model="formModel"
      :rules="rules"
      ref="formRef"
      label-width="100px"
    >
      <el-form-item label="文章标题" prop="title">
        <el-input v-model="formModel.title" placeholder="请输入标题"></el-input>
      </el-form-item>
      <el-form-item label="文章分类" prop="cate_id">
        <channel-select
          v-model="formModel.category"
          width="100%"
        ></channel-select>
      </el-form-item>
      <el-form-item label="文章封面" prop="cover_img">
        <el-upload
          class="avatar-uploader"
          :auto-upload="false"
          :show-file-list="false"
          :on-change="onUploadFile"
        >
          <img v-if="imgUrl" :src="imgUrl" class="avatar" alt="" />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item label="文章简介" prop="description">
        <el-input
          v-model="formModel.description"
          maxlength="100"
          autosize
          show-word-limit
          type="textarea"
          resize="none"
        ></el-input>
      </el-form-item>
      <el-form-item label="文章内容" prop="content">
        <div class="editor">
          <quill-editor
            theme="snow"
            ref="editorRef"
            v-model:content="formModel.content"
            contentType="html"
          >
          </quill-editor>
        </div>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onPublish('已发布')">发布</el-button>
        <el-button type="info" @click="onPublish('未发布')">草稿</el-button>
      </el-form-item>
    </el-form>
  </el-drawer>
</template>

<style lang="scss" scoped>
.avatar-uploader {
  :deep(.el-upload) {
    .avatar {
      width: 178px;
      height: 178px;
      display: block;
    }
    .el-upload {
      border: 1px dashed var(--el-border-color);
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      transition: var(--el-transition-duration-fast);
    }
    .el-upload:hover {
      border-color: var(--el-color-primary);
    }
    .el-icon.avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 178px;
      height: 178px;
      text-align: center;
    }
  }
}

.editor {
  width: 100%;
  :deep(.ql-editor) {
    min-height: 200px;
  }
}
</style>
