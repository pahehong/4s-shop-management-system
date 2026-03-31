
# 4S店管理系统项目依赖关系说明

## 项目结构概览

```
4s-shop-management-system/
├── backend/                 # 后端微服务
│   ├── pom.xml             # 根pom，管理所有后端模块
│   └── services/
│       ├── api-gateway/    # API网关
│       ├── customer-service/ # 客户服务
│       ├── repair-service/   # 维修服务
│       ├── inventory-service/ # 库存服务
│       ├── finance-service/   # 财务服务
│       ├── auth-service/     # 认证服务
│       └── common/          # 公共模块
├── web-frontend/           # Web前端（管理后台）
│   └── package.json
├── desktop-app/            # 桌面应用
│   └── package.json
└── mobile-app/             # 移动应用（微信小程序）
    └── project.config.json
```

## 后端依赖关系

### 1. 公共模块 (common)
- **被依赖者**: 所有其他后端服务
- **功能**: 提供公共实体、DTO、工具类、配置等
- **依赖**: Spring Boot基础组件、Lombok、Jackson等

### 2. 认证服务 (auth-service)
- **依赖**: common模块
- **被依赖者**: 无（独立服务）
- **功能**: 用户认证、授权、JWT令牌管理

### 3. API网关 (api-gateway)
- **依赖**: common模块
- **被依赖者**: 所有前端应用
- **功能**: 统一入口、路由、限流、认证

### 4. 客户服务 (customer-service)
- **依赖**: common模块, auth-service
- **被依赖者**: api-gateway
- **功能**: 客户信息管理、会员体系、车辆档案

### 5. 维修服务 (repair-service)
- **依赖**: common模块, auth-service, customer-service
- **被依赖者**: api-gateway
- **功能**: 工单管理、维修进度跟踪

### 6. 库存服务 (inventory-service)
- **依赖**: common模块, auth-service
- **被依赖者**: api-gateway
- **功能**: 配件管理、库存管理、采购管理

### 7. 财务服务 (finance-service)
- **依赖**: common模块, auth-service, customer-service, repair-service
- **被依赖者**: api-gateway
- **功能**: 收费管理、财务报表、支付集成

## 前端依赖关系

### 1. Web前端 (管理后台)
- **依赖**: 后端API服务
- **技术栈**: React 18, TypeScript, Ant Design
- **功能**: 管理员界面，提供完整的4S店管理功能

### 2. 桌面应用
- **依赖**: 后端API服务
- **技术栈**: Electron, React, Ant Design
- **功能**: 桌面版管理工具，支持离线操作

### 3. 移动应用 (微信小程序)
- **依赖**: 后端API服务
- **技术栈**: 原生微信小程序框架
- **功能**: 客户端功能，预约、查询、支付等

## 服务间调用关系

### 1. API网关调用关系
```
API Gateway
├── Customer Service
├── Repair Service
├── Inventory Service
├── Finance Service
└── Auth Service
```

### 2. 服务间内部调用
```
Customer Service: 独立服务
Repair Service: 依赖Customer Service获取客户信息
Inventory Service: 独立服务
Finance Service: 依赖Customer Service和Repair Service
Auth Service: 被其他服务依赖
```

## 数据库依赖关系

### 1. 共享数据库
- 所有后端服务共享同一个PostgreSQL数据库实例
- 通过不同的Schema或表前缀进行逻辑隔离

### 2. 缓存依赖
- 所有后端服务共享Redis缓存实例
- 用于会话管理、数据缓存、分布式锁等

## 部署依赖关系

### 1. 基础设施依赖
- PostgreSQL数据库
- Redis缓存
- RabbitMQ消息队列
- Elasticsearch搜索引擎

### 2. 服务启动顺序
1. 数据库和缓存服务
2. 认证服务
3. 公共服务（API网关）
4. 业务服务（客户、维修、库存、财务）
5. 前端应用

## 开发环境依赖

### 1. 构建工具
- Maven (后端)
- npm/yarn (前端)

### 2. 运行时环境
- Java 17 (后端)
- Node.js 14+ (前端)
- Docker (容器化部署)

## 安全依赖关系

### 1. 认证流程
```
前端应用
├──→ API网关 (验证JWT)
    ├──→ Auth Service (验证凭据)
    └──→ 业务服务 (通过网关转发)
```

### 2. 权限控制
- 所有服务通过API网关进行统一权限控制
- 基于角色的访问控制(RBAC)
- 细粒度的资源权限管理
