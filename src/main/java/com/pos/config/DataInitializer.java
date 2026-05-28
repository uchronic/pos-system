package com.pos.config;

import com.pos.entity.*;
import com.pos.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProductCategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final MemberLevelRepository memberLevelRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final EmployeeRepository employeeRepository;
    private final SupplierRepository supplierRepository;
    private final CouponRepository couponRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public void run(String... args) {
        initCategories();
        initMemberLevels();
        initStores();
        initEmployees();
        initSuppliers();
        initProducts();
        initMembers();
        initCoupons();
        initOrders();
    }

    private void initCategories() {
        if (categoryRepository.count() > 0) return;
        String[][] categories = {
                {"食品饮料", "1"}, {"生鲜水果", "2"}, {"日用百货", "3"},
                {"数码家电", "4"}, {"服装鞋帽", "5"}, {"美妆个护", "6"}
        };
        for (int i = 0; i < categories.length; i++) {
            ProductCategory c = new ProductCategory();
            c.setName(categories[i][0]);
            c.setSortOrder(i + 1);
            categoryRepository.save(c);
        }
    }

    private void initMemberLevels() {
        if (memberLevelRepository.count() > 0) return;
        String[][] levels = {
                {"普通会员", "0", "1.00", "1", "0"},
                {"银卡会员", "1", "0.98", "2", "1000"},
                {"金卡会员", "2", "0.95", "3", "5000"},
                {"钻石会员", "3", "0.90", "5", "20000"}
        };
        for (String[] l : levels) {
            MemberLevel level = new MemberLevel();
            level.setName(l[0]);
            level.setLevel(Integer.parseInt(l[1]));
            level.setDiscount(new BigDecimal(l[2]));
            level.setPointsRate(Integer.parseInt(l[3]));
            level.setMinAmount(new BigDecimal(l[4]));
            memberLevelRepository.save(level);
        }
    }

    private void initStores() {
        if (storeRepository.count() > 0) return;
        String[][] stores = {
                {"总店", "STORE001", "张经理", "13800001111", "北京市朝阳区建国路88号"},
                {"东区分店", "STORE002", "李店长", "13800002222", "北京市东城区东直门外大街18号"},
                {"西区分店", "STORE003", "王店长", "13800003333", "北京市海淀区中关村大街27号"}
        };
        for (String[] s : stores) {
            Store store = new Store();
            store.setName(s[0]);
            store.setCode(s[1]);
            store.setContact(s[2]);
            store.setPhone(s[3]);
            store.setAddress(s[4]);
            storeRepository.save(store);
        }
    }

    private void initEmployees() {
        if (employeeRepository.count() > 0) return;
        Object[][] employees = {
                {"管理员", "admin", "admin123", "13900000000", 1L, "ADMIN"},
                {"张三", "zhangsan", "123456", "13900001111", 1L, "MANAGER"},
                {"李四", "lisi", "123456", "13900002222", 1L, "CASHIER"},
                {"王五", "wangwu", "123456", "13900003333", 2L, "CASHIER"}
        };
        for (Object[] e : employees) {
            Employee emp = new Employee();
            emp.setName((String) e[0]);
            emp.setUsername((String) e[1]);
            emp.setPassword((String) e[2]);
            emp.setPhone((String) e[3]);
            emp.setStoreId((Long) e[4]);
            emp.setRole((String) e[5]);
            employeeRepository.save(emp);
        }
    }

    private void initSuppliers() {
        if (supplierRepository.count() > 0) return;
        String[][] suppliers = {
                {"华润万家供应链", "赵经理", "010-88881111", "北京市大兴区物流园A区"},
                {"中粮集团", "钱经理", "010-88882222", "北京市朝阳区中粮大厦"},
                {"宝洁日化代理", "孙经理", "021-66661111", "上海市浦东新区张江路100号"}
        };
        for (String[] s : suppliers) {
            Supplier supplier = new Supplier();
            supplier.setName(s[0]);
            supplier.setContact(s[1]);
            supplier.setPhone(s[2]);
            supplier.setAddress(s[3]);
            supplierRepository.save(supplier);
        }
    }

    private void initProducts() {
        if (productRepository.count() > 0) return;
        Object[][] products = {
                {"农夫山泉550ml", "6921168509256", 1L, "食品饮料", 2.00, 1.20, 2.50, 500},
                {"可口可乐330ml", "6902083883065", 1L, "食品饮料", 3.00, 1.80, 3.50, 300},
                {"伊利纯牛奶250ml", "6907992500218", 1L, "食品饮料", 4.50, 3.00, 5.00, 200},
                {"红富士苹果(斤)", "2000000000001", 2L, "生鲜水果", 6.80, 4.00, 8.00, 150},
                {"进口香蕉(斤)", "2000000000002", 2L, "生鲜水果", 5.50, 3.50, 7.00, 120},
                {"维达抽纸3层", "6903266731087", 3L, "日用百货", 12.90, 8.00, 15.90, 80},
                {"舒肤佳香皂", "6903148040329", 6L, "美妆个护", 6.50, 4.00, 8.00, 100},
                {"华为充电器", "6941487210012", 4L, "数码家电", 59.00, 35.00, 79.00, 50},
                {"纯棉T恤(男)", "3000000000001", 5L, "服装鞋帽", 89.00, 45.00, 129.00, 60},
                {"百事可乐500ml", "6920459950180", 1L, "食品饮料", 3.50, 2.00, 4.00, 280}
        };
        for (Object[] p : products) {
            Product product = new Product();
            product.setName((String) p[0]);
            product.setBarcode((String) p[1]);
            product.setCategoryId((Long) p[2]);
            product.setCategoryName((String) p[3]);
            product.setPrice((BigDecimal) p[4]);
            product.setCostPrice((BigDecimal) p[5]);
            product.setMarketPrice((BigDecimal) p[6]);
            product.setStock((Integer) p[7]);
            productRepository.save(product);
        }
    }

    private void initMembers() {
        if (memberRepository.count() > 0) return;
        Object[][] members = {
                {"张小明", "13811111111", "VIP001", 1L, "普通会员", 100.00, 200},
                {"李小红", "13822222222", "VIP002", 2L, "银卡会员", 500.00, 1200},
                {"王大力", "13833333333", "VIP003", 3L, "金卡会员", 2000.00, 6000},
                {"赵雅雅", "13844444444", "VIP004", 4L, "钻石会员", 10000.00, 25000}
        };
        for (Object[] m : members) {
            Member member = new Member();
            member.setName((String) m[0]);
            member.setPhone((String) m[1]);
            member.setMemberNo((String) m[2]);
            member.setLevelId((Long) m[3]);
            member.setLevelName((String) m[4]);
            member.setBalance(BigDecimal.valueOf((Double) m[5]));
            member.setPoints((Integer) m[6]);
            memberRepository.save(member);
        }
    }

    private void initCoupons() {
        if (couponRepository.count() > 0) return;
        Object[][] coupons = {
                {"新人优惠券", "FULL_REDUCE", 50.00, 10.00, 1000, 120},
                {"满100减20", "FULL_REDUCE", 100.00, 20.00, 500, 80},
                {"8折优惠券", "DISCOUNT", 0.00, 0.80, 200, 50},
                {"5元现金券", "FIXED", 0.00, 5.00, 2000, 500}
        };
        for (Object[] c : coupons) {
            Coupon coupon = new Coupon();
            coupon.setName((String) c[0]);
            coupon.setType((String) c[1]);
            coupon.setMinAmount(BigDecimal.valueOf((Double) c[2]));
            coupon.setValue(BigDecimal.valueOf((Double) c[3]));
            coupon.setTotalCount((Integer) c[4]);
            coupon.setUsedCount((Integer) c[5]);
            coupon.setStartTime(LocalDateTime.now().minusDays(30));
            coupon.setEndTime(LocalDateTime.now().plusDays(60));
            couponRepository.save(coupon);
        }
    }

    private void initOrders() {
        if (orderRepository.count() > 0) return;
        Object[][] orders = {
                {"ORD20260528001", 1L, "张小明", 1L, "李四", 25.00, 0.00, 25.00, "WECHAT", 1, 3},
                {"ORD20260528002", 2L, "李小红", 1L, "李四", 89.00, 0.00, 89.00, "ALIPAY", 1, 1},
                {"ORD20260528003", 3L, "王大力", 2L, "王五", 156.50, 20.00, 136.50, "CASH", 2, 5}
        };
        for (Object[] o : orders) {
            Order order = new Order();
            order.setOrderNo((String) o[0]);
            order.setMemberId((Long) o[1]);
            order.setMemberName((String) o[2]);
            order.setStoreId((Long) o[3]);
            order.setCashierName((String) o[4]);
            order.setTotalAmount(BigDecimal.valueOf((Double) o[5]));
            order.setDiscountAmount(BigDecimal.valueOf((Double) o[6]));
            order.setPayAmount(BigDecimal.valueOf((Double) o[7]));
            order.setPayType((String) o[8]);
            order.setStatus((Integer) o[9]);
            order.setItemCount((Integer) o[10]);
            order.setPayTime(LocalDateTime.now().minusHours(2));
            orderRepository.save(order);
        }
    }
}
