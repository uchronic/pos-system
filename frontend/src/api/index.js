import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
  timeout: 10000
})

api.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})

api.interceptors.response.use(
  res => {
    if (res.data.code !== 200) {
      return Promise.reject(new Error(res.data.message))
    }
    return res.data
  },
  err => Promise.reject(err)
)

// Auth
export const login = (data) => api.post('/auth/login', data)

// Dashboard
export const getDashboard = () => api.get('/dashboard')

// Products
export const getProducts = (params) => api.get('/products', { params })
export const getProduct = (id) => api.get(`/products/${id}`)
export const createProduct = (data) => api.post('/products', data)
export const updateProduct = (id, data) => api.put(`/products/${id}`, data)
export const deleteProduct = (id) => api.delete(`/products/${id}`)

// Categories
export const getCategories = () => api.get('/categories')
export const createCategory = (data) => api.post('/categories', data)
export const updateCategory = (id, data) => api.put(`/categories/${id}`, data)
export const deleteCategory = (id) => api.delete(`/categories/${id}`)

// Members
export const getMembers = (params) => api.get('/members', { params })
export const createMember = (data) => api.post('/members', data)
export const updateMember = (id, data) => api.put(`/members/${id}`, data)
export const deleteMember = (id) => api.delete(`/members/${id}`)

// Member Levels
export const getMemberLevels = () => api.get('/member-levels')
export const createMemberLevel = (data) => api.post('/member-levels', data)
export const updateMemberLevel = (id, data) => api.put(`/member-levels/${id}`, data)
export const deleteMemberLevel = (id) => api.delete(`/member-levels/${id}`)

// Orders
export const getOrders = (params) => api.get('/orders', { params })
export const getOrderDetail = (id) => api.get(`/orders/${id}`)
export const getOrderItems = (id) => api.get(`/orders/${id}/items`)
export const createOrder = (data) => api.post('/orders', data)
export const payOrder = (id) => api.put(`/orders/${id}/pay`)
export const cancelOrder = (id) => api.put(`/orders/${id}/cancel`)

// Coupons
export const getCoupons = () => api.get('/coupons')
export const createCoupon = (data) => api.post('/coupons', data)
export const updateCoupon = (id, data) => api.put(`/coupons/${id}`, data)
export const deleteCoupon = (id) => api.delete(`/coupons/${id}`)

// Suppliers
export const getSuppliers = (params) => api.get('/suppliers', { params })
export const createSupplier = (data) => api.post('/suppliers', data)
export const updateSupplier = (id, data) => api.put(`/suppliers/${id}`, data)
export const deleteSupplier = (id) => api.delete(`/suppliers/${id}`)

// Stores
export const getStores = () => api.get('/stores')
export const createStore = (data) => api.post('/stores', data)
export const updateStore = (id, data) => api.put(`/stores/${id}`, data)
export const deleteStore = (id) => api.delete(`/stores/${id}`)

// Employees
export const getEmployees = () => api.get('/employees')
export const createEmployee = (data) => api.post('/employees', data)
export const updateEmployee = (id, data) => api.put(`/employees/${id}`, data)
export const deleteEmployee = (id) => api.delete(`/employees/${id}`)

// Pickup Cards
export const getPickupCards = () => api.get('/pickup-cards')
export const redeemPickupCard = (cardNo) => api.put(`/pickup-cards/${cardNo}/redeem`)
export const createPickupCard = (data) => api.post('/pickup-cards', data)
export const deletePickupCard = (id) => api.delete(`/pickup-cards/${id}`)

// Inventory
export const getInventoryRecords = (params) => api.get('/inventory/records', { params })
export const stockIn = (data) => api.post('/inventory/stock-in', data)
export const stockOut = (data) => api.post('/inventory/stock-out', data)

export default api
