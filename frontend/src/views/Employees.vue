<template>
  <div>
    <el-card>
      <template #header>
        <div style="display:flex;justify-content:space-between;align-items:center">
          <span>员工管理</span>
          <el-button type="primary" @click="openDialog()">新增员工</el-button>
        </div>
      </template>
      <el-table :data="tableData" stripe border v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="name" label="姓名" width="100" />
        <el-table-column prop="username" label="用户名" width="100" />
        <el-table-column prop="phone" label="手机号" width="130" />
        <el-table-column prop="role" label="角色" width="100">
          <template #default="{ row }"><el-tag :type="roleType[row.role]">{{ roleMap[row.role] }}</el-tag></template>
        </el-table-column>
        <el-table-column prop="storeId" label="门店ID" width="80" />
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
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id?'编辑员工':'新增员工'" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="姓名"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="用户名"><el-input v-model="form.username" /></el-form-item>
        <el-form-item label="密码"><el-input v-model="form.password" type="password" /></el-form-item>
        <el-form-item label="手机号"><el-input v-model="form.phone" /></el-form-item>
        <el-form-item label="角色"><el-select v-model="form.role" style="width:100%"><el-option value="ADMIN" label="管理员" /><el-option value="MANAGER" label="店长" /><el-option value="CASHIER" label="收银员" /></el-select></el-form-item>
        <el-form-item label="门店ID"><el-input-number v-model="form.storeId" :min="1" style="width:100%" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSave">保存</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getEmployees, createEmployee, updateEmployee, deleteEmployee } from '../api'

const loading = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const form = ref({})
const roleMap = { ADMIN: '管理员', MANAGER: '店长', CASHIER: '收银员' }
const roleType = { ADMIN: 'danger', MANAGER: 'warning', CASHIER: '' }

const loadData = async () => {
  loading.value = true
  try { const res = await getEmployees(); tableData.value = res.data } finally { loading.value = false }
}
const openDialog = (row) => { form.value = row ? { ...row } : { name: '', username: '', password: '', phone: '', role: 'CASHIER', storeId: 1, status: 1 }; dialogVisible.value = true }
const handleSave = async () => {
  try { form.value.id ? await updateEmployee(form.value.id, form.value) : await createEmployee(form.value); ElMessage.success('保存成功'); dialogVisible.value = false; loadData() } catch (e) { ElMessage.error(e.message) }
}
const handleDelete = async (id) => { try { await deleteEmployee(id); ElMessage.success('删除成功'); loadData() } catch (e) { ElMessage.error(e.message) } }

onMounted(loadData)
</script>
