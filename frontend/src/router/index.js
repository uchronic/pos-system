import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  { path: '/login', name: 'Login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../layouts/MainLayout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', name: 'Dashboard', component: () => import('../views/Dashboard.vue'), meta: { title: '仪表盘' } },
      { path: 'products', name: 'Products', component: () => import('../views/Products.vue'), meta: { title: '商品管理' } },
      { path: 'categories', name: 'Categories', component: () => import('../views/Categories.vue'), meta: { title: '商品分类' } },
      { path: 'members', name: 'Members', component: () => import('../views/Members.vue'), meta: { title: '会员管理' } },
      { path: 'member-levels', name: 'MemberLevels', component: () => import('../views/MemberLevels.vue'), meta: { title: '会员等级' } },
      { path: 'orders', name: 'Orders', component: () => import('../views/Orders.vue'), meta: { title: '订单管理' } },
      { path: 'coupons', name: 'Coupons', component: () => import('../views/Coupons.vue'), meta: { title: '优惠券' } },
      { path: 'suppliers', name: 'Suppliers', component: () => import('../views/Suppliers.vue'), meta: { title: '供应商管理' } },
      { path: 'stores', name: 'Stores', component: () => import('../views/Stores.vue'), meta: { title: '门店管理' } },
      { path: 'employees', name: 'Employees', component: () => import('../views/Employees.vue'), meta: { title: '员工管理' } },
      { path: 'pickup-cards', name: 'PickupCards', component: () => import('../views/PickupCards.vue'), meta: { title: '提货卡' } },
      { path: 'inventory', name: 'Inventory', component: () => import('../views/Inventory.vue'), meta: { title: '进销存' } }
    ]
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.name !== 'Login' && !localStorage.getItem('token')) {
    next({ name: 'Login' })
  } else {
    next()
  }
})

export default router
