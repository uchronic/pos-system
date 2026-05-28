<template>
  <div>
    <el-card>
      <template #header>
        <div style="display:flex;justify-content:space-between;align-items:center">
          <span>营销管理 - 优惠券</span>
          <el-button type="primary" @click="openDialog()">新增优惠券</el-button>
        </div>
      </template>
      <el-table :data="tableData" stripe border v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="name" label="优惠券名称" min-width="120" />
        <el-table-column prop="type" label="类型" width="100">
          <template #default="{ row }">{{ typeMap[row.type] }}</template>
        </el-table-column>
        <el-table-column prop="minAmount" label="最低消费" width="100">
          <template #default="{ row }">&yen;{{ row.minAmount }}</template>
        </el-table-column>
        <el-table-column prop="value" label="优惠值" width="100" />
        <el-table-column prop="totalCount" label="发放量" width="80" />
        <el-table-column prop="usedCount" label="已使用" width="80" />
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
    <el-dialog v-model="dialogVisible" :title="form.id?'编辑优惠券':'新增优惠券'" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="类型"><el-select v-model="form.type" style="width:100%"><el-option value="FULL_REDUCE" label="满减" /><el-option value="DISCOUNT" label="折扣" /><el-option value="FIXED" label="固定金额" /></el-select></el-form-item>
        <el-form-item label="最低消费"><el-input-number v-model="form.minAmount" :min="0" :precision="2" style="width:100%" /></el-form-item>
        <el-form-item label="优惠值"><el-input-number v-model="form.value" :min="0" :precision="2" style="width:100%" /></el-form-item>
        <el-form-item label="发放量"><el-input-number v-model="form.totalCount" :min="0" style="width:100%" /></el-form-item>
        <el-form-item label="状态"><el-select v-model="form.status" style="width:100%"><el-option :value="1" label="启用" /><el-option :value="0" label="禁用" /></el-select></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSave">保存</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getCoupons, createCoupon, updateCoupon, deleteCoupon } from '../api'

const loading = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const form = ref({})
const typeMap = { FULL_REDUCE: '满减', DISCOUNT: '折扣', FIXED: '固定金额' }

const loadData = async () => {
  loading.value = true
  try { const res = await getCoupons(); tableData.value = res.data } finally { loading.value = false }
}
const openDialog = (row) => { form.value = row ? { ...row } : { name: '', type: 'FULL_REDUCE', minAmount: 0, value: 0, totalCount: 0, status: 1 }; dialogVisible.value = true }
const handleSave = async () => {
  try { form.value.id ? await updateCoupon(form.value.id, form.value) : await createCoupon(form.value); ElMessage.success('保存成功'); dialogVisible.value = false; loadData() } catch (e) { ElMessage.error(e.message) }
}
const handleDelete = async (id) => { try { await deleteCoupon(id); ElMessage.success('删除成功'); loadData() } catch (e) { ElMessage.error(e.message) } }

onMounted(loadData)
</script>
