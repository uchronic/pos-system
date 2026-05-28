<template>
  <div>
    <el-card>
      <template #header>
        <div style="display:flex;justify-content:space-between;align-items:center">
          <span>提货卡管理</span>
          <el-button type="primary" @click="openDialog()">新增提货卡</el-button>
        </div>
      </template>
      <el-table :data="tableData" stripe border v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="cardNo" label="卡号" width="200" />
        <el-table-column prop="productName" label="商品" min-width="120" />
        <el-table-column prop="quantity" label="数量" width="80" />
        <el-table-column prop="balance" label="余额" width="100">
          <template #default="{ row }">&yen;{{ row.balance }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="90">
          <template #default="{ row }">
            <el-tag :type="statusType[row.status]">{{ statusMap[row.status] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="expireTime" label="过期时间" width="170" />
        <el-table-column label="操作" width="160">
          <template #default="{ row }">
            <el-button v-if="row.status===0" link type="success" @click="handleRedeem(row.cardNo)">核销</el-button>
            <el-popconfirm title="确定删除?" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog v-model="dialogVisible" title="新增提货卡" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="卡号"><el-input v-model="form.cardNo" /></el-form-item>
        <el-form-item label="密码"><el-input v-model="form.password" /></el-form-item>
        <el-form-item label="商品名称"><el-input v-model="form.productName" /></el-form-item>
        <el-form-item label="数量"><el-input-number v-model="form.quantity" :min="1" style="width:100%" /></el-form-item>
        <el-form-item label="余额"><el-input-number v-model="form.balance" :min="0" :precision="2" style="width:100%" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSave">保存</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getPickupCards, createPickupCard, deletePickupCard, redeemPickupCard } from '../api'

const loading = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const form = ref({})
const statusMap = { 0: '未使用', 1: '已使用', 2: '已过期' }
const statusType = { 0: 'success', 1: 'info', 2: 'danger' }

const loadData = async () => {
  loading.value = true
  try { const res = await getPickupCards(); tableData.value = res.data } finally { loading.value = false }
}
const openDialog = () => { form.value = { cardNo: '', password: '', productName: '', quantity: 1, balance: 0 }; dialogVisible.value = true }
const handleSave = async () => {
  try { await createPickupCard(form.value); ElMessage.success('保存成功'); dialogVisible.value = false; loadData() } catch (e) { ElMessage.error(e.message) }
}
const handleRedeem = async (cardNo) => {
  try { await redeemPickupCard(cardNo); ElMessage.success('核销成功'); loadData() } catch (e) { ElMessage.error(e.message) }
}
const handleDelete = async (id) => { try { await deletePickupCard(id); ElMessage.success('删除成功'); loadData() } catch (e) { ElMessage.error(e.message) } }

onMounted(loadData)
</script>
