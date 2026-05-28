import axios from 'axios'
import mockData from './mock'

const api = axios.create({ baseURL: '/api', timeout: 3000 })
let useMock = false
const mock = (data) => Promise.resolve({ code: 200, data, message: 'success' })

function request(apiPromise, fallbackData) {
  if (useMock) return mock(fallbackData)
  return apiPromise.catch(() => { useMock = true; console.log('[POS] 演示模式'); return mock(fallbackData) })
}

api.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})
api.interceptors.response.use(res => { if (res.data.code !== 200) return Promise.reject(new Error(res.data.message)); return res.data }, err => Promise.reject(err))

export const login = (data) => {
  if (useMock) return data.username === 'admin' && data.password === 'admin123' ? mock({ token: 'demo-token', userId: 1, username: 'admin', name: '管理员', role: 'ADMIN', storeId: 1 }) : Promise.reject(new Error('用户名或密码错误'))
  return request(api.post('/auth/login', data), { token: 'demo-token', userId: 1, username: 'admin', name: '管理员', role: 'ADMIN', storeId: 1 })
}
export const getDashboard = () => request(api.get('/dashboard'), mockData.dashboard)
export const getProducts = (params) => request(api.get('/products', { params }), mockData.products)
export const createProduct = (data) => request(api.post('/products', data), { ...data, id: Date.now() })
export const updateProduct = (id, data) => request(api.put(`/products/${id}`, data), data)
export const deleteProduct = (id) => request(api.delete(`/products/${id}`), null)
export const getCategories = () => request(api.get('/categories'), mockData.categories)
export const createCategory = (data) => request(api.post('/categories', data), { ...data, id: Date.now() })
export const updateCategory = (id, data) => request(api.put(`/categories/${id}`, data), data)
export const deleteCategory = (id) => request(api.delete(`/categories/${id}`), null)
export const getMembers = (params) => request(api.get('/members', { params }), mockData.members)
export const createMember = (data) => request(api.post('/members', data), { ...data, id: Date.now() })
export const updateMember = (id, data) => request(api.put(`/members/${id}`, data), data)
export const deleteMember = (id) => request(api.delete(`/members/${id}`), null)
export const getMemberLevels = () => request(api.get('/member-levels'), mockData.memberLevels)
export const createMemberLevel = (data) => request(api.post('/member-levels', data), { ...data, id: Date.now() })
export const updateMemberLevel = (id, data) => request(api.put(`/member-levels/${id}`, data), data)
export const deleteMemberLevel = (id) => request(api.delete(`/member-levels/${id}`), null)
export const getOrders = (params) => request(api.get('/orders', { params }), mockData.orders)
export const getOrderDetail = (id) => request(api.get(`/orders/${id}`), mockData.orders.content.find(o => o.id === id))
export const getOrderItems = (id) => request(api.get(`/orders/${id}/items`), [])
export const createOrder = (data) => request(api.post('/orders', data), { ...data, id: Date.now() })
export const payOrder = (id) => request(api.put(`/orders/${id}/pay`), { id, status: 1 })
export const cancelOrder = (id) => request(api.put(`/orders/${id}/cancel`), { id, status: 4 })
export const getCoupons = () => request(api.get('/coupons'), mockData.coupons)
export const createCoupon = (data) => request(api.post('/coupons', data), { ...data, id: Date.now() })
export const updateCoupon = (id, data) => request(api.put(`/coupons/${id}`, data), data)
export const deleteCoupon = (id) => request(api.delete(`/coupons/${id}`), null)
export const getSuppliers = (params) => request(api.get('/suppliers', { params }), mockData.suppliers)
export const createSupplier = (data) => request(api.post('/suppliers', data), { ...data, id: Date.now() })
export const updateSupplier = (id, data) => request(api.put(`/suppliers/${id}`, data), data)
export const deleteSupplier = (id) => request(api.delete(`/suppliers/${id}`), null)
export const getStores = () => request(api.get('/stores'), mockData.stores)
export const createStore = (data) => request(api.post('/stores', data), { ...data, id: Date.now() })
export const updateStore = (id, data) => request(api.put(`/stores/${id}`, data), data)
export const deleteStore = (id) => request(api.delete(`/stores/${id}`), null)
export const getEmployees = () => request(api.get('/employees'), mockData.employees)
export const createEmployee = (data) => request(api.post('/employees', data), { ...data, id: Date.now() })
export const updateEmployee = (id, data) => request(api.put(`/employees/${id}`, data), data)
export const deleteEmployee = (id) => request(api.delete(`/employees/${id}`), null)
export const getPickupCards = () => request(api.get('/pickup-cards'), mockData.pickupCards)
export const redeemPickupCard = (cardNo) => request(api.put(`/pickup-cards/${cardNo}/redeem`), { cardNo, status: 1 })
export const createPickupCard = (data) => request(api.post('/pickup-cards', data), { ...data, id: Date.now() })
export const deletePickupCard = (id) => request(api.delete(`/pickup-cards/${id}`), null)
export const getInventoryRecords = (params) => request(api.get('/inventory/records', { params }), mockData.inventoryRecords)
export const stockIn = (data) => request(api.post('/inventory/stock-in', data), { ...data, id: Date.now(), type: 'IN' })
export const stockOut = (data) => request(api.post('/inventory/stock-out', data), { ...data, id: Date.now(), type: 'OUT' })
export default api
