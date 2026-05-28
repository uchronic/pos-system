# POS 收银管理系统

一套完整的零售收银管理系统，涵盖商品管理、会员管理、营销管理、进销存、连锁门店管理等核心业务模块。

## 系统架构

```
┌─────────────────────────────────────────────────────────────┐
│                        前端 (Vue 3)                          │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────────┐   │
│  │ Element   │ │  Vue     │ │  Vue     │ │    ECharts   │   │
│  │ Plus UI   │ │ Router   │ │ Axios    │ │   图表       │   │
│  └──────────┘ └──────────┘ └──────────┘ └──────────────┘   │
└─────────────────────────┬───────────────────────────────────┘
                          │ HTTP API
┌─────────────────────────┴───────────────────────────────────┐
│                     后端 (Spring Boot 3)                      │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────────┐   │
│  │ Spring   │ │ Spring   │ │ Spring   │ │  SpringDoc   │   │
│  │ Web MVC  │ │ Data JPA │ │ Boot     │ │  OpenAPI     │   │
│  └──────────┘ └──────────┘ └──────────┘ └──────────────┘   │
└─────────────────────────┬───────────────────────────────────┘
                          │
              ┌───────────┴───────────┐
              │    H2 内存数据库       │
              │   (可替换 MySQL)       │
              └───────────────────────┘
```

## 功能模块

| 模块 | 说明 | 状态 |
|------|------|------|
| 仪表盘 | 销售统计、快捷入口、系统信息 | ✅ |
| 商品管理 | 商品CRUD、条码、分类、价格管理 | ✅ |
| 商品分类 | 分类树管理、排序 | ✅ |
| 会员管理 | 会员信息、余额、积分 | ✅ |
| 会员等级 | 等级折扣、积分倍率、升级门槛 | ✅ |
| 订单管理 | 订单查询、状态流转、支付 | ✅ |
| 营销管理 | 优惠券（满减/折扣/固定） | ✅ |
| 进销存 | 入库、出库、库存变动记录 | ✅ |
| 提货卡 | 提货卡发放、核销 | ✅ |
| 供应商管理 | 供应商信息维护 | ✅ |
| 连锁门店 | 多门店管理 | ✅ |
| 员工管理 | 员工信息、角色权限 | ✅ |
| 登录认证 | 用户登录、Token 鉴权 | ✅ |

### 待扩展模块

| 模块 | 说明 |
|------|------|
| 积分管理 | 积分规则、积分商城、兑换 |
| 支付渠道 | 微信支付、支付宝对接 |
| 微信平台 | 小程序商城对接 |
| 影院对接 | 影院票务系统集成 |
| 蛋糕对接 | 蛋糕预订系统集成 |
| 财务报表 | 销售报表、利润分析 |
| 团购模块 | 团购活动、拼团 |

## 技术栈

### 后端
- **Java 21** + **Spring Boot 3.2**
- **Spring Data JPA** — ORM 持久化
- **H2 Database** — 内存数据库（零配置，开箱即用）
- **SpringDoc OpenAPI** — Swagger API 文档
- **Maven** — 项目构建

### 前端
- **Vue 3** — 响应式框架
- **Vite** — 极速构建工具
- **Element Plus** — UI 组件库
- **Vue Router** — 路由管理
- **Axios** — HTTP 请求

## 快速开始

### 环境要求
- JDK 21+
- Node.js 18+
- npm 9+

### 启动后端

```bash
cd pos-system

# Windows
mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

后端启动后访问：
- API 服务: http://localhost:8080
- Swagger 文档: http://localhost:8080/swagger-ui.html
- H2 控制台: http://localhost:8080/h2-console

### 启动前端

```bash
cd pos-system/frontend

npm install
npm run dev
```

前端启动后访问：http://localhost:3000

### 演示账号
- 用户名: `admin`
- 密码: `admin123`

## API 接口概览

| 模块 | 接口前缀 | 说明 |
|------|---------|------|
| 登录 | `POST /api/auth/login` | 用户登录 |
| 仪表盘 | `GET /api/dashboard` | 统计数据 |
| 商品 | `/api/products` | CRUD |
| 分类 | `/api/categories` | CRUD |
| 会员 | `/api/members` | CRUD |
| 等级 | `/api/member-levels` | CRUD |
| 订单 | `/api/orders` | CRUD + 支付/取消 |
| 优惠券 | `/api/coupons` | CRUD |
| 供应商 | `/api/suppliers` | CRUD |
| 门店 | `/api/stores` | CRUD |
| 员工 | `/api/employees` | CRUD |
| 提货卡 | `/api/pickup-cards` | CRUD + 核销 |
| 进销存 | `/api/inventory` | 入库/出库/查询 |

## 项目结构

```
pos-system/
├── pom.xml                          # Maven 配置
├── mvnw.cmd                         # Maven Wrapper (Windows)
├── src/main/java/com/pos/
│   ├── PosApplication.java          # 启动类
│   ├── common/                      # 通用响应封装
│   ├── config/                      # 配置类 (CORS, OpenAPI, 数据初始化)
│   ├── entity/                      # 实体类 (12个)
│   ├── repository/                  # 数据访问层 (10个)
│   └── controller/                  # 控制器 (12个)
├── src/main/resources/
│   └── application.yml              # 应用配置
└── frontend/                        # Vue 3 前端
    ├── package.json
    ├── vite.config.js
    ├── index.html
    └── src/
        ├── main.js                  # 入口
        ├── App.vue                  # 根组件
        ├── router/index.js          # 路由配置
        ├── api/index.js             # API 封装
        ├── layouts/MainLayout.vue   # 主布局
        └── views/                   # 页面组件 (12个)
```

## 数据库说明

系统默认使用 H2 内存数据库，启动时自动创建表结构并初始化演示数据。如需切换为 MySQL：

1. 修改 `pom.xml`，添加 MySQL 驱动依赖
2. 修改 `application.yml` 中的数据源配置
3. 将 `ddl-auto` 改为 `update` 或使用 Flyway 管理迁移

## 许可证

MIT License
