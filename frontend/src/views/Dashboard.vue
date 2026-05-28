<template>
  <div class="dashboard">
    <el-row :gutter="20" class="stat-cards">
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card" style="border-top: 3px solid #409eff">
          <div class="stat-content">
            <div><div class="stat-value">{{ stats.productCount || 0 }}</div><div class="stat-label">商品数量</div></div>
            <el-icon size="48" color="#409eff"><Goods /></el-icon>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card" style="border-top: 3px solid #67c23a">
          <div class="stat-content">
            <div><div class="stat-value">{{ stats.memberCount || 0 }}</div><div class="stat-label">活跃会员</div></div>
            <el-icon size="48" color="#67c23a"><User /></el-icon>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card" style="border-top: 3px solid #e6a23c">
          <div class="stat-content">
            <div><div class="stat-value">{{ stats.orderCount || 0 }}</div><div class="stat-label">已支付订单</div></div>
            <el-icon size="48" color="#e6a23c"><Document /></el-icon>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card" style="border-top: 3px solid #f56c6c">
          <div class="stat-content">
            <div><div class="stat-value">&yen;{{ stats.todaySales || '0.00' }}</div><div class="stat-label">今日销售额</div></div>
            <el-icon size="48" color="#f56c6c"><Wallet /></el-icon>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card>
          <template #header><span>系统功能模块</span></template>
          <el-row :gutter="12">
            <el-col :span="8" v-for="mod in modules" :key="mod.name">
              <div class="module-item" @click="$router.push(mod.path)">
                <el-icon :size="28" :color="mod.color"><component :is="mod.icon" /></el-icon>
                <span>{{ mod.name }}</span>
              </div>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header><span>快捷操作</span></template>
          <div class="quick-actions">
            <el-button type="primary" @click="$router.push('/products')">商品管理</el-button>
            <el-button type="success" @click="$router.push('/members')">会员管理</el-button>
            <el-button type="warning" @click="$router.push('/orders')">订单查询</el-button>
            <el-button type="danger" @click="$router.push('/inventory')">库存管理</el-button>
            <el-button @click="$router.push('/coupons')">营销活动</el-button>
            <el-button @click="$router.push('/stores')">门店管理</el-button>
          </div>
          <el-divider />
          <el-descriptions title="系统信息" :column="1" border size="small">
            <el-descriptions-item label="系统版本">v1.0.0</el-descriptions-item>
            <el-descriptions-item label="技术栈">Spring Boot 3 + Vue 3</el-descriptions-item>
            <el-descriptions-item label="数据库">H2 (Demo)</el-descriptions-item>
            <el-descriptions-item label="门店数量">{{ stats.storeCount || 0 }}</el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getDashboard } from '../api'

const stats = ref({})
const modules = [
  { name: '商品管理', icon: 'Goods', color: '#409eff', path: '/products' },
  { name: '会员管理', icon: 'User', color: '#67c23a', path: '/members' },
  { name: '订单管理', icon: 'Document', color: '#e6a23c', path: '/orders' },
  { name: '营销管理', icon: 'Ticket', color: '#f56c6c', path: '/coupons' },
  { name: '进销存', icon: 'Box', color: '#909399', path: '/inventory' },
  { name: '提货卡', icon: 'CreditCard', color: '#9b59b6', path: '/pickup-cards' },
  { name: '供应商', icon: 'Van', color: '#1abc9c', path: '/suppliers' },
  { name: '连锁门店', icon: 'OfficeBuilding', color: '#e67e22', path: '/stores' },
  { name: '员工管理', icon: 'UserFilled', color: '#3498db', path: '/employees' }
]

onMounted(async () => {
  try {
    const res = await getDashboard()
    stats.value = res.data
  } catch {}
})
</script>

<style scoped>
.stat-card { border-radius: 8px; }
.stat-content { display: flex; align-items: center; justify-content: space-between; }
.stat-value { font-size: 28px; font-weight: bold; color: #303133; }
.stat-label { font-size: 14px; color: #909399; margin-top: 4px; }
.module-item { display: flex; flex-direction: column; align-items: center; gap: 8px; padding: 16px; cursor: pointer; border-radius: 8px; transition: background 0.2s; }
.module-item:hover { background: #f5f7fa; }
.module-item span { font-size: 13px; color: #606266; }
.quick-actions { display: flex; flex-wrap: wrap; gap: 10px; }
</style>
