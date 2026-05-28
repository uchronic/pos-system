<template>
  <div>
    <el-card>
      <template #header>
        <div style="display:flex;justify-content:space-between;align-items:center">
          <span>供应商管理</span>
          <el-button type="primary" @click="openDialog()">新增供应商</el-button>
        </div>
      </template>
      <el-form inline style="margin-bottom:16px">
        <el-form-item label="供应商名称"><el-input v-model="query.name" placeholder="搜索" clearable /></el-form-item>
        <el-form-item><el-button type="primary" @click="loadData">查询</el-button></el-form-item>
      </el-form>
      <el-table :data="tableData" stripe border v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="name" label="供应商名称" min-width="150" />
        <el-table-column prop="contact" label="联系人" width="100" />
        <el-table-column prop="phone" label="电话" width="130" />
        <el-table-column prop="address" label="地址" min-width="200" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }"><el-tag :type="row.status===1?'success':'info'">{{ row.status===1?'正常':'禁用' }}</el-tag></template>
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
      <el-pagination style="margin-top:16px;justify-content:flex-end" v-model:current-page="query.page" v-model:page-size="query.size" :total="total" :page-sizes="[10,20,50]" layout="total,sizes,prev,pager,next" @change="loadData" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id?'编辑供应商':'新增供应商'" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="联系人"><el-input v-model="form.contact" /></el-form-item>
        <el-form-item label="电话"><el-input v-model="form.phone" /></el-form-item>
        <el-form-item label="地址"><el-input v-model="form.address" type="textarea" /></el-form-item>
        <el-form-item label="备注"><el-input v-model="form.remark" type="textarea" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSave">保存</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getSuppliers, createSupplier, updateSupplier, deleteSupplier } from '../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = ref({ name: '', page: 0, size: 10 })
const dialogVisible = ref(false)
const form = ref({})

const loadData = async () => {
  loading.value = true
  try { const res = await getSuppliers(query.value); tableData.value = res.data.content; total.value = res.data.totalElements } finally { loading.value = false }
}
const openDialog = (row) => { form.value = row ? { ...row } : { name: '', contact: '', phone: '', address: '', remark: '', status: 1 }; dialogVisible.value = true }
const handleSave = async () => {
  try { form.value.id ? await updateSupplier(form.value.id, form.value) : await createSupplier(form.value); ElMessage.success('保存成功'); dialogVisible.value = false; loadData() } catch (e) { ElMessage.error(e.message) }
}
const handleDelete = async (id) => { try { await deleteSupplier(id); ElMessage.success('删除成功'); loadData() } catch (e) { ElMessage.error(e.message) } }

onMounted(loadData)
</script>
