<template>
  <div>
    <el-card>
      <template #header>
        <div style="display:flex;justify-content:space-between;align-items:center">
          <span>门店管理</span>
          <el-button type="primary" @click="openDialog()">新增门店</el-button>
        </div>
      </template>
      <el-table :data="tableData" stripe border v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="name" label="门店名称" min-width="120" />
        <el-table-column prop="code" label="门店编码" width="100" />
        <el-table-column prop="contact" label="负责人" width="80" />
        <el-table-column prop="phone" label="电话" width="130" />
        <el-table-column prop="address" label="地址" min-width="200" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }"><el-tag :type="row.status===1?'success':'info'">{{ row.status===1?'营业':'关闭' }}</el-tag></template>
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
    <el-dialog v-model="dialogVisible" :title="form.id?'编辑门店':'新增门店'" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="门店名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="门店编码"><el-input v-model="form.code" /></el-form-item>
        <el-form-item label="负责人"><el-input v-model="form.contact" /></el-form-item>
        <el-form-item label="电话"><el-input v-model="form.phone" /></el-form-item>
        <el-form-item label="地址"><el-input v-model="form.address" type="textarea" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSave">保存</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getStores, createStore, updateStore, deleteStore } from '../api'

const loading = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const form = ref({})

const loadData = async () => {
  loading.value = true
  try { const res = await getStores(); tableData.value = res.data } finally { loading.value = false }
}
const openDialog = (row) => { form.value = row ? { ...row } : { name: '', code: '', contact: '', phone: '', address: '', status: 1 }; dialogVisible.value = true }
const handleSave = async () => {
  try { form.value.id ? await updateStore(form.value.id, form.value) : await createStore(form.value); ElMessage.success('保存成功'); dialogVisible.value = false; loadData() } catch (e) { ElMessage.error(e.message) }
}
const handleDelete = async (id) => { try { await deleteStore(id); ElMessage.success('删除成功'); loadData() } catch (e) { ElMessage.error(e.message) } }

onMounted(loadData)
</script>
