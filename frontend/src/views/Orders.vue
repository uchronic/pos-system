<template>
  <div>
    <el-card>
      <template #header><span>订单管理</span></template>
      <el-form inline style="margin-bottom:16px">
        <el-form-item label="订单状态">
          <el-select v-model="query.status" clearable placeholder="全部" style="width:120px">
            <el-option label="待支付" :value="0" /><el-option label="已支付" :value="1" />
            <el-option label="已完成" :value="2" /><el-option label="已退款" :value="3" />
            <el-option label="已取消" :value="4" />
          </el-select>
        </el-form-item>
        <el-form-item><el-button type="primary" @click="loadData">查询</el-button></el-form-item>
      </el-form>
      <el-table :data="tableData" stripe border v-loading="loading">
        <el-table-column prop="orderNo" label="订单号" width="180" />
        <el-table-column prop="memberName" label="会员" width="100" />
        <el-table-column prop="cashierName" label="收银员" width="80" />
        <el-table-column prop="totalAmount" label="总金额" width="100">
          <template #default="{ row }">&yen;{{ row.totalAmount }}</template>
        </el-table-column>
        <el-table-column prop="discountAmount" label="优惠" width="90">
          <template #default="{ row }">&yen;{{ row.discountAmount }}</template>
        </el-table-column>
        <el-table-column prop="payAmount" label="实付" width="100">
          <template #default="{ row }"><b style="color:#f56c6c">&yen;{{ row.payAmount }}</b></template>
        </el-table-column>
        <el-table-column prop="payType" label="支付方式" width="100">
          <template #default="{ row }">{{ payTypeMap[row.payType] || row.payType }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="90">
          <template #default="{ row }">
            <el-tag :type="statusType[row.status]">{{ statusMap[row.status] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createdAt" label="下单时间" width="170" />
      </el-table>
      <el-pagination style="margin-top:16px;justify-content:flex-end" v-model:current-page="query.page" v-model:page-size="query.size" :total="total" :page-sizes="[10,20,50]" layout="total,sizes,prev,pager,next" @change="loadData" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getOrders } from '../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = ref({ status: null, page: 0, size: 10 })
const statusMap = { 0: '待支付', 1: '已支付', 2: '已完成', 3: '已退款', 4: '已取消' }
const statusType = { 0: 'warning', 1: 'success', 2: '', 3: 'danger', 4: 'info' }
const payTypeMap = { WECHAT: '微信支付', ALIPAY: '支付宝', CASH: '现金', CARD: '银行卡' }

const loadData = async () => {
  loading.value = true
  try {
    const res = await getOrders(query.value)
    tableData.value = res.data.content
    total.value = res.data.totalElements
  } finally { loading.value = false }
}

onMounted(loadData)
</script>
