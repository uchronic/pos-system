<template>
  <el-container class="layout-container">
    <el-aside :width="isCollapse ? '64px' : '220px'" class="layout-aside">
      <div class="logo">
        <el-icon size="24"><Shop /></el-icon>
        <span v-show="!isCollapse">POS收银系统</span>
      </div>
      <el-menu :default-active="route.path" router :collapse="isCollapse" background-color="#304156" text-color="#bfcbd9" active-text-color="#409eff">
        <el-menu-item index="/dashboard"><el-icon><DataAnalysis /></el-icon><span>仪表盘</span></el-menu-item>
        <el-sub-menu index="goods">
          <template #title><el-icon><Goods /></el-icon><span>商品管理</span></template>
          <el-menu-item index="/products">商品列表</el-menu-item>
          <el-menu-item index="/categories">商品分类</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="member">
          <template #title><el-icon><User /></el-icon><span>会员管理</span></template>
          <el-menu-item index="/members">会员列表</el-menu-item>
          <el-menu-item index="/member-levels">会员等级</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/orders"><el-icon><Document /></el-icon><span>订单管理</span></el-menu-item>
        <el-menu-item index="/coupons"><el-icon><Ticket /></el-icon><span>营销管理</span></el-menu-item>
        <el-menu-item index="/inventory"><el-icon><Box /></el-icon><span>进销存</span></el-menu-item>
        <el-menu-item index="/pickup-cards"><el-icon><CreditCard /></el-icon><span>提货卡</span></el-menu-item>
        <el-menu-item index="/suppliers"><el-icon><Van /></el-icon><span>供应商管理</span></el-menu-item>
        <el-sub-menu index="chain">
          <template #title><el-icon><OfficeBuilding /></el-icon><span>连锁管理</span></template>
          <el-menu-item index="/stores">门店管理</el-menu-item>
          <el-menu-item index="/employees">员工管理</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="layout-header">
        <div class="header-left">
          <el-icon class="collapse-btn" @click="isCollapse = !isCollapse"><Fold v-if="!isCollapse" /><Expand v-else /></el-icon>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-if="route.meta.title">{{ route.meta.title }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-icon><UserFilled /></el-icon>
              {{ userName }}
              <el-icon><ArrowDown /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-main class="layout-main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()
const isCollapse = ref(false)
const userName = computed(() => {
  try { return JSON.parse(localStorage.getItem('user'))?.name || '管理员' } catch { return '管理员' }
})

const handleCommand = (cmd) => {
  if (cmd === 'logout') {
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    router.push('/login')
  }
}
</script>

<style scoped>
.layout-container { height: 100vh; }
.layout-aside { background: #304156; transition: width 0.3s; overflow: hidden; }
.logo { height: 60px; display: flex; align-items: center; justify-content: center; gap: 8px; color: #fff; font-size: 16px; font-weight: bold; background: #263445; }
.layout-header { display: flex; align-items: center; justify-content: space-between; background: #fff; box-shadow: 0 1px 4px rgba(0,0,0,0.08); padding: 0 20px; }
.header-left { display: flex; align-items: center; gap: 16px; }
.collapse-btn { cursor: pointer; font-size: 20px; color: #606266; }
.user-info { display: flex; align-items: center; gap: 6px; cursor: pointer; color: #606266; }
.layout-main { background: #f0f2f5; padding: 20px; }
.el-menu { border-right: none; }
</style>
