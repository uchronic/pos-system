<template>
  <div>
    <el-card>
      <template #header>
        <div style="display:flex;justify-content:space-between;align-items:center">
          <span>商品管理</span>
          <el-button type="primary" @click="openDialog()">新增商品</el-button>
        </div>
      </template>
      <el-form inline style="margin-bottom:16px">
        <el-form-item label="商品名称"><el-input v-model="query.name" placeholder="搜索商品" clearable /></el-form-item>
        <el-form-item><el-button type="primary" @click="loadData">查询</el-button></el-form-item>
      </el-form>
      <el-table :data="tableData" stripe border v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="name" label="商品名称" min-width="120" />
        <el-table-column prop="barcode" label="条码" width="130" />
        <el-table-column prop="categoryName" label="分类" width="100" />
        <el-table-column prop="price" label="售价" width="90">
          <template #default="{ row }">&yen;{{ row.price }}</template>
        </el-table-column>
        <el-table-column prop="costPrice" label="成本价" width="90">
          <template #default="{ row }">&yen;{{ row.costPrice }}</template>
        </el-table-column>
        <el-table-column prop="stock" label="库存" width="80" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }"><el-tag :type="row.status===1?'success':'info'">{{ row.status===1?'上架':'下架' }}</el-tag></template>
        </el-table-column>
        <el-table-column label="操作" width="160" fixed="right">
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

    <el-dialog v-model="dialogVisible" :title="form.id?'编辑商品':'新增商品'" width="600px">
      <el-form :model="form" label-width="80px">
        <el-row :gutter="16">
          <el-col :span="12"><el-form-item label="商品名称"><el-input v-model="form.name" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="条码"><el-input v-model="form.barcode" /></el-form-item></el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12"><el-form-item label="售价"><el-input-number v-model="form.price" :min="0" :precision="2" style="width:100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="成本价"><el-input-number v-model="form.costPrice" :min="0" :precision="2" style="width:100%" /></el-form-item></el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12"><el-form-item label="库存"><el-input-number v-model="form.stock" :min="0" style="width:100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="状态"><el-select v-model="form.status" style="width:100%"><el-option :value="1" label="上架" /><el-option :value="0" label="下架" /></el-select></el-form-item></el-col>
        </el-row>
        <el-form-item label="描述"><el-input v-model="form.description" type="textarea" :rows="3" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getProducts, createProduct, updateProduct, deleteProduct } from '../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = ref({ name: '', page: 0, size: 10 })
const dialogVisible = ref(false)
const form = ref({})

const loadData = async () => {
  loading.value = true
  try {
    const res = await getProducts(query.value)
    tableData.value = res.data.content
    total.value = res.data.totalElements
  } finally { loading.value = false }
}

const openDialog = (row) => {
  form.value = row ? { ...row } : { name: '', barcode: '', price: 0, costPrice: 0, stock: 0, status: 1, description: '' }
  dialogVisible.value = true
}

const handleSave = async () => {
  try {
    if (form.value.id) await updateProduct(form.value.id, form.value)
    else await createProduct(form.value)
    ElMessage.success('保存成功')
    dialogVisible.value = false
    loadData()
  } catch (e) { ElMessage.error(e.message) }
}

const handleDelete = async (id) => {
  try {
    await deleteProduct(id)
    ElMessage.success('删除成功')
    loadData()
  } catch (e) { ElMessage.error(e.message) }
}

onMounted(loadData)
</script>
