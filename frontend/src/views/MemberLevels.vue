<template>
  <div>
    <el-card>
      <template #header>
        <div style="display:flex;justify-content:space-between;align-items:center">
          <span>会员等级</span>
          <el-button type="primary" @click="openDialog()">新增等级</el-button>
        </div>
      </template>
      <el-table :data="tableData" stripe border v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="name" label="等级名称" />
        <el-table-column prop="level" label="等级" width="80" />
        <el-table-column prop="discount" label="折扣" width="80" />
        <el-table-column prop="pointsRate" label="积分倍率" width="100" />
        <el-table-column prop="minAmount" label="升级门槛" width="120">
          <template #default="{ row }">&yen;{{ row.minAmount }}</template>
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
    <el-dialog v-model="dialogVisible" :title="form.id?'编辑等级':'新增等级'" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="等级名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="等级值"><el-input-number v-model="form.level" :min="0" style="width:100%" /></el-form-item>
        <el-form-item label="折扣"><el-input-number v-model="form.discount" :min="0" :max="1" :step="0.01" :precision="2" style="width:100%" /></el-form-item>
        <el-form-item label="积分倍率"><el-input-number v-model="form.pointsRate" :min="1" style="width:100%" /></el-form-item>
        <el-form-item label="升级门槛"><el-input-number v-model="form.minAmount" :min="0" :precision="2" style="width:100%" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSave">保存</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getMemberLevels, createMemberLevel, updateMemberLevel, deleteMemberLevel } from '../api'

const loading = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const form = ref({})

const loadData = async () => {
  loading.value = true
  try { const res = await getMemberLevels(); tableData.value = res.data } finally { loading.value = false }
}
const openDialog = (row) => { form.value = row ? { ...row } : { name: '', level: 0, discount: 1, pointsRate: 1, minAmount: 0 }; dialogVisible.value = true }
const handleSave = async () => {
  try { form.value.id ? await updateMemberLevel(form.value.id, form.value) : await createMemberLevel(form.value); ElMessage.success('保存成功'); dialogVisible.value = false; loadData() } catch (e) { ElMessage.error(e.message) }
}
const handleDelete = async (id) => { try { await deleteMemberLevel(id); ElMessage.success('删除成功'); loadData() } catch (e) { ElMessage.error(e.message) } }

onMounted(loadData)
</script>
