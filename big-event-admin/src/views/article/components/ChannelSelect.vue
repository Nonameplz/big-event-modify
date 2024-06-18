<script setup>
import { getCategories } from '@/api/article.js'
import { ref } from 'vue'

defineProps({
  modelValue: {
    type: [Number, String]
  },
  width: {
    type: String
  }
})

const categories = ref([])
const getCategoriesList = async () => {
  const res = await getCategories()
  for (let i = 0; i < res.data.length; i++) {
    categories.value.push({
      label: res.data[i],
      value: res.data[i]
    })
  }
}
getCategoriesList()

const emit = defineEmits(['update:modelValue'])
</script>

<template>
  <el-select
    :modelValue="modelValue"
    clearable
    filterable
    allow-create
    default-first-option
    @update:modelValue="emit('update:modelValue', $event)"
    :style="width"
  >
    <el-option
      v-for="item in categories"
      :key="item.value"
      :label="item.label"
      :value="item.value"
    />
  </el-select>
</template>

<style scoped></style>
