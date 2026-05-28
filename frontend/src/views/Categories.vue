<template>
  <div>
    <el-card>
      <template #header>
        <div style="display:flex;justify-content:space-between;align-items:center">
          <span>商品分类</span>
          <el-button type="primary" @click="openDialog()">新增分类</el-button>
        </div>
      </template>
      <el-table :data="tableData" stripe border v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="name" label="分类名称" />
        <el-table-column prop="sortOrder" label="排序" width="80" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }"><el-tag :type="row.status===1?'success':'info'">{{ row.status===1?'启用':'禁用' }}</el-tag></template>
        </el-table-column>
        <el-table-column label="操作" width="160">
          <template #default="{ row }">
            <el-button link type="primary" @click="openDialog(row)">编辑</el-button>
            <el-popconfirm title="确定删除?" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id?'编辑分类':'新增分类'" width="400px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="分类名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="排序"><el-input-number v-model="form.sortOrder" :min="0" style="width:100%" /></el-form-item>
        <el-form-item label="状态"><el-select v-model="form.status" style="width:100%"><el-option :value="1" label="启用" /><el-option :value="0" label="禁用" /></el-select></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSave">保存</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getCategories, createCategory, updateCategory, deleteCategory } from '../api'

const loading = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const form = ref({})

const loadData = async () => {
  loading.value = true
  try { const res = await getCategories(); tableData.value = res.data } finally { loading.value = false }
}
const openDialog = (row) => { form.value = row ? { ...row } : { name: '', sortOrder: 0, status: 1 }; dialogVisible.value = true }
const handleSave = async () => {
  try { form.value.id ? await updateCategory(form.value.id, form.value) : await createCategory(form.value); ElMessage.success('保存成功'); dialogVisible.value = false; loadData() } catch (e) { ElMessage.error(e.message) }
}
const handleDelete = async (id) => { try { await deleteCategory(id); ElMessage.success('删除成功'); loadData() } catch (e) { ElMessage.error(e.message) } }

onMounted(loadData)
</script>
