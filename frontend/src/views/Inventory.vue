<template>
  <div>
    <el-card>
      <template #header>
        <div style="display:flex;justify-content:space-between;align-items:center">
          <span>进销存管理</span>
          <div>
            <el-button type="primary" @click="openDialog('IN')">入库</el-button>
            <el-button type="warning" @click="openDialog('OUT')">出库</el-button>
          </div>
        </div>
      </template>
      <el-form inline style="margin-bottom:16px">
        <el-form-item label="类型">
          <el-select v-model="query.type" clearable placeholder="全部" style="width:120px">
            <el-option value="IN" label="入库" /><el-option value="OUT" label="出库" />
          </el-select>
        </el-form-item>
        <el-form-item><el-button type="primary" @click="loadData">查询</el-button></el-form-item>
      </el-form>
      <el-table :data="tableData" stripe border v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="productName" label="商品" min-width="120" />
        <el-table-column prop="type" label="类型" width="80">
          <template #default="{ row }"><el-tag :type="row.type==='IN'?'success':'warning'">{{ row.type==='IN'?'入库':'出库' }}</el-tag></template>
        </el-table-column>
        <el-table-column prop="quantity" label="数量" width="80" />
        <el-table-column prop="reason" label="原因" min-width="150" />
        <el-table-column prop="operatorName" label="操作人" width="100" />
        <el-table-column prop="createdAt" label="时间" width="170" />
      </el-table>
      <el-pagination style="margin-top:16px;justify-content:flex-end" v-model:current-page="query.page" v-model:page-size="query.size" :total="total" :page-sizes="[10,20,50]" layout="total,sizes,prev,pager,next" @change="loadData" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="dialogType==='IN'?'入库操作':'出库操作'" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="商品ID"><el-input-number v-model="form.productId" :min="1" style="width:100%" /></el-form-item>
        <el-form-item label="商品名称"><el-input v-model="form.productName" /></el-form-item>
        <el-form-item label="数量"><el-input-number v-model="form.quantity" :min="1" style="width:100%" /></el-form-item>
        <el-form-item label="原因"><el-input v-model="form.reason" type="textarea" /></el-form-item>
        <el-form-item label="操作人"><el-input v-model="form.operatorName" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSave">确认</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getInventoryRecords, stockIn, stockOut } from '../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = ref({ type: '', page: 0, size: 10 })
const dialogVisible = ref(false)
const dialogType = ref('IN')
const form = ref({})

const loadData = async () => {
  loading.value = true
  try { const res = await getInventoryRecords(query.value); tableData.value = res.data.content; total.value = res.data.totalElements } finally { loading.value = false }
}
const openDialog = (type) => { dialogType.value = type; form.value = { productId: 1, productName: '', quantity: 1, reason: '', operatorName: '管理员' }; dialogVisible.value = true }
const handleSave = async () => {
  try {
    if (dialogType.value === 'IN') await stockIn(form.value)
    else await stockOut(form.value)
    ElMessage.success('操作成功')
    dialogVisible.value = false
    loadData()
  } catch (e) { ElMessage.error(e.message) }
}

onMounted(loadData)
</script>
