const mockData = {
  dashboard: { productCount: 10, memberCount: 4, orderCount: 3, storeCount: 3, todaySales: '250.50', totalSales: '18650.00' },
  products: {
    content: [
      { id: 1, name: '农夫山泉550ml', barcode: '6921168509256', categoryId: 1, categoryName: '食品饮料', price: 2.00, costPrice: 1.20, marketPrice: 2.50, stock: 500, status: 1, imageUrl: '/images/product-water.jpg' },
      { id: 2, name: '可口可乐330ml', barcode: '6902083883065', categoryId: 1, categoryName: '食品饮料', price: 3.00, costPrice: 1.80, marketPrice: 3.50, stock: 300, status: 1, imageUrl: '/images/product-cola.jpg' },
      { id: 3, name: '伊利纯牛奶250ml', barcode: '6907992500218', categoryId: 1, categoryName: '食品饮料', price: 4.50, costPrice: 3.00, marketPrice: 5.00, stock: 200, status: 1, imageUrl: '/images/product-milk.jpg' },
      { id: 4, name: '红富士苹果(斤)', barcode: '2000000000001', categoryId: 2, categoryName: '生鲜水果', price: 6.80, costPrice: 4.00, marketPrice: 8.00, stock: 150, status: 1, imageUrl: '/images/product-apple.jpg' },
      { id: 5, name: '进口香蕉(斤)', barcode: '2000000000002', categoryId: 2, categoryName: '生鲜水果', price: 5.50, costPrice: 3.50, marketPrice: 7.00, stock: 120, status: 1, imageUrl: '/images/product-banana.jpg' },
      { id: 6, name: '维达抽纸3层', barcode: '6903266731087', categoryId: 3, categoryName: '日用百货', price: 12.90, costPrice: 8.00, marketPrice: 15.90, stock: 80, status: 1, imageUrl: '/images/product-tissue.jpg' },
      { id: 7, name: '舒肤佳香皂', barcode: '6903148040329', categoryId: 6, categoryName: '美妆个护', price: 6.50, costPrice: 4.00, marketPrice: 8.00, stock: 100, status: 1, imageUrl: '/images/product-soap.jpg' },
      { id: 8, name: '华为充电器', barcode: '6941487210012', categoryId: 4, categoryName: '数码家电', price: 59.00, costPrice: 35.00, marketPrice: 79.00, stock: 50, status: 1, imageUrl: '/images/product-charger.jpg' },
      { id: 9, name: '纯棉T恤(男)', barcode: '3000000000001', categoryId: 5, categoryName: '服装鞋帽', price: 89.00, costPrice: 45.00, marketPrice: 129.00, stock: 60, status: 1, imageUrl: '/images/product-tshirt.jpg' },
      { id: 10, name: '百事可乐500ml', barcode: '6920459950180', categoryId: 1, categoryName: '食品饮料', price: 3.50, costPrice: 2.00, marketPrice: 4.00, stock: 280, status: 1, imageUrl: '/images/product-pepsi.jpg' }
    ], totalElements: 10, totalPages: 1, page: 0, size: 10
  },
  categories: [
    { id: 1, name: '食品饮料', sortOrder: 1, status: 1 }, { id: 2, name: '生鲜水果', sortOrder: 2, status: 1 },
    { id: 3, name: '日用百货', sortOrder: 3, status: 1 }, { id: 4, name: '数码家电', sortOrder: 4, status: 1 },
    { id: 5, name: '服装鞋帽', sortOrder: 5, status: 1 }, { id: 6, name: '美妆个护', sortOrder: 6, status: 1 }
  ],
  members: {
    content: [
      { id: 1, name: '张小明', phone: '13811111111', memberNo: 'VIP001', levelId: 1, levelName: '普通会员', balance: 100.00, points: 200, status: 1 },
      { id: 2, name: '李小红', phone: '13822222222', memberNo: 'VIP002', levelId: 2, levelName: '银卡会员', balance: 500.00, points: 1200, status: 1 },
      { id: 3, name: '王大力', phone: '13833333333', memberNo: 'VIP003', levelId: 3, levelName: '金卡会员', balance: 2000.00, points: 6000, status: 1 },
      { id: 4, name: '赵雅雅', phone: '13844444444', memberNo: 'VIP004', levelId: 4, levelName: '钻石会员', balance: 10000.00, points: 25000, status: 1 }
    ], totalElements: 4, totalPages: 1, page: 0, size: 10
  },
  memberLevels: [
    { id: 1, name: '普通会员', level: 0, discount: 1.00, pointsRate: 1, minAmount: 0, status: 1 },
    { id: 2, name: '银卡会员', level: 1, discount: 0.98, pointsRate: 2, minAmount: 1000, status: 1 },
    { id: 3, name: '金卡会员', level: 2, discount: 0.95, pointsRate: 3, minAmount: 5000, status: 1 },
    { id: 4, name: '钻石会员', level: 3, discount: 0.90, pointsRate: 5, minAmount: 20000, status: 1 }
  ],
  orders: {
    content: [
      { id: 1, orderNo: 'ORD20260528001', memberId: 1, memberName: '张小明', storeId: 1, cashierName: '李四', totalAmount: 25.00, discountAmount: 0.00, payAmount: 25.00, payType: 'WECHAT', status: 1, itemCount: 3, createdAt: '2026-05-28 09:15:00' },
      { id: 2, orderNo: 'ORD20260528002', memberId: 2, memberName: '李小红', storeId: 1, cashierName: '李四', totalAmount: 89.00, discountAmount: 0.00, payAmount: 89.00, payType: 'ALIPAY', status: 1, itemCount: 1, createdAt: '2026-05-28 10:30:00' },
      { id: 3, orderNo: 'ORD20260528003', memberId: 3, memberName: '王大力', storeId: 2, cashierName: '王五', totalAmount: 156.50, discountAmount: 20.00, payAmount: 136.50, payType: 'CASH', status: 2, itemCount: 5, createdAt: '2026-05-28 14:00:00' }
    ], totalElements: 3, totalPages: 1, page: 0, size: 10
  },
  coupons: [
    { id: 1, name: '新人优惠券', type: 'FULL_REDUCE', minAmount: 50.00, value: 10.00, totalCount: 1000, usedCount: 120, status: 1 },
    { id: 2, name: '满100减20', type: 'FULL_REDUCE', minAmount: 100.00, value: 20.00, totalCount: 500, usedCount: 80, status: 1 },
    { id: 3, name: '8折优惠券', type: 'DISCOUNT', minAmount: 0, value: 0.80, totalCount: 200, usedCount: 50, status: 1 },
    { id: 4, name: '5元现金券', type: 'FIXED', minAmount: 0, value: 5.00, totalCount: 2000, usedCount: 500, status: 1 }
  ],
  suppliers: {
    content: [
      { id: 1, name: '华润万家供应链', contact: '赵经理', phone: '010-88881111', address: '北京市大兴区物流园A区', status: 1 },
      { id: 2, name: '中粮集团', contact: '钱经理', phone: '010-88882222', address: '北京市朝阳区中粮大厦', status: 1 },
      { id: 3, name: '宝洁日化代理', contact: '孙经理', phone: '021-66661111', address: '上海市浦东新区张江路100号', status: 1 }
    ], totalElements: 3, totalPages: 1, page: 0, size: 10
  },
  stores: [
    { id: 1, name: '总店', code: 'STORE001', contact: '张经理', phone: '13800001111', address: '北京市朝阳区建国路88号', status: 1 },
    { id: 2, name: '东区分店', code: 'STORE002', contact: '李店长', phone: '13800002222', address: '北京市东城区东直门外大街18号', status: 1 },
    { id: 3, name: '西区分店', code: 'STORE003', contact: '王店长', phone: '13800003333', address: '北京市海淀区中关村大街27号', status: 1 }
  ],
  employees: [
    { id: 1, name: '管理员', username: 'admin', phone: '13900000000', storeId: 1, role: 'ADMIN', status: 1 },
    { id: 2, name: '张三', username: 'zhangsan', phone: '13900001111', storeId: 1, role: 'MANAGER', status: 1 },
    { id: 3, name: '李四', username: 'lisi', phone: '13900002222', storeId: 1, role: 'CASHIER', status: 1 },
    { id: 4, name: '王五', username: 'wangwu', phone: '13900003333', storeId: 2, role: 'CASHIER', status: 1 }
  ],
  pickupCards: [
    { id: 1, cardNo: 'PC20260528001', productName: '生日蛋糕8寸', quantity: 1, balance: 198.00, initialAmount: 198.00, status: 0, expireTime: '2026-12-31' },
    { id: 2, cardNo: 'PC20260528002', productName: '水果礼盒', quantity: 1, balance: 128.00, initialAmount: 128.00, status: 0, expireTime: '2026-12-31' },
    { id: 3, cardNo: 'PC20260528003', productName: '进口红酒', quantity: 2, balance: 0, initialAmount: 396.00, status: 1, expireTime: '2026-12-31' }
  ],
  inventoryRecords: {
    content: [
      { id: 1, productId: 1, productName: '农夫山泉550ml', type: 'IN', quantity: 200, reason: '常规补货', operatorName: '张三', createdAt: '2026-05-28 08:00:00' },
      { id: 2, productId: 2, productName: '可口可乐330ml', type: 'IN', quantity: 150, reason: '常规补货', operatorName: '张三', createdAt: '2026-05-28 08:05:00' },
      { id: 3, productId: 1, productName: '农夫山泉550ml', type: 'OUT', quantity: 10, reason: '门店调拨', operatorName: '李四', createdAt: '2026-05-28 10:00:00' },
      { id: 4, productId: 8, productName: '华为充电器', type: 'IN', quantity: 30, reason: '新品入库', operatorName: '张三', createdAt: '2026-05-28 11:00:00' },
      { id: 5, productId: 4, productName: '红富士苹果(斤)', type: 'OUT', quantity: 20, reason: '损耗出库', operatorName: '王五', createdAt: '2026-05-28 15:00:00' }
    ], totalElements: 5, totalPages: 1, page: 0, size: 10
  }
}
export default mockData
