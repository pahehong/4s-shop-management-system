
# 4S店管理系统项目骨架结构

## 项目整体结构

```
4s-shop-management-system/
├── README.md
├── .gitignore
├── .editorconfig
├── .gitattributes
├── docker-compose.yml
├── docker/
│   ├── backend.Dockerfile
│   ├── frontend.Dockerfile
│   └── db.Dockerfile
├── backend/                    # 后端微服务
│   ├── pom.xml
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── .mvn/wrapper/
│   └── services/
│       ├── api-gateway/        # API网关
│       │   ├── pom.xml
│       │   └── src/main/
│       │       ├── java/com/4s/gateway/
│       │       └── resources/
│       ├── customer-service/   # 客户服务
│       │   ├── pom.xml
│       │   └── src/main/
│       │       ├── java/com/4s/customer/
│       │       └── resources/
│       ├── repair-service/     # 维修服务
│       │   ├── pom.xml
│       │   └── src/main/
│       │       ├── java/com/4s/repair/
│       │       └── resources/
│       ├── inventory-service/  # 库存服务
│       │   ├── pom.xml
│       │   └── src/main/
│       │       ├── java/com/4s/inventory/
│       │       └── resources/
│       ├── finance-service/    # 财务服务
│       │   ├── pom.xml
│       │   └── src/main/
│       │       ├── java/com/4s/finance/
│       │       └── resources/
│       ├── common/             # 公共模块
│       │   ├── pom.xml
│       │   └── src/main/
│       │       └── java/com/4s/common/
│       │           ├── dto/
│       │           ├── exception/
│       │           ├── util/
│       │           └── config/
│       └── auth-service/       # 认证服务
│           ├── pom.xml
│           └── src/main/
│               ├── java/com/4s/auth/
│               └── resources/
├── frontend/                   # 前端项目
│   ├── admin-web/             # 管理后台
│   │   ├── package.json
│   │   ├── package-lock.json
│   │   ├── .gitignore
│   │   ├── .env
│   │   ├── public/
│   │   ├── src/
│   │   │   ├── components/
│   │   │   ├── pages/
│   │   │   ├── services/
│   │   │   ├── utils/
│   │   │   ├── store/
│   │   │   └── App.tsx
│   │   └── tsconfig.json
│   ├── desktop-app/           # 桌面应用
│   │   ├── package.json
│   │   ├── package-lock.json
│   │   ├── .gitignore
│   │   ├── src/
│   │   │   ├── main/
│   │   │   ├── renderer/
│   │   │   └── preload/
│   │   ├── public/
│   │   └── electron-builder.json
│   └── wechat-mini/           # 微信小程序
│       ├── project.config.json
│       ├── app.js
│       ├── app.json
│       ├── app.wxss
│       ├── pages/
│       │   ├── index/
│       │   ├── profile/
│       │   ├── booking/
│       │   └── service/
│       ├── components/
│       └── utils/
├── database/                  # 数据库相关
│   ├── init/
│   │   ├── 01-init.sql
│   │   ├── 02-schema.sql
│   │   ├── 03-data.sql
│   │   └── 04-indexes.sql
│   ├── migration/
│   │   ├── V1__Initial_schema.sql
│   │   ├── V2__Add_customer_level.sql
│   │   └── V3__Add_work_order_status.sql
│   └── docker-compose.db.yml
├── docs/                     # 文档
│   ├── architecture/
│   │   ├── system_architecture.md
│   │   ├── database_design.md
│   │   └── api_documentation/
│   ├── deployment/
│   │   ├── deployment_guide.md
│   │   ├── kubernetes/
│   │   └── docker/
│   └── user_manuals/
├── scripts/                  # 脚本
│   ├── deploy.sh
│   ├── backup.sh
│   ├── restore.sh
│   └── init_db.sh
├── kubernetes/              # Kubernetes配置
│   ├── deployment/
│   │   ├── api-gateway-deployment.yaml
│   │   ├── customer-service-deployment.yaml
│   │   ├── repair-service-deployment.yaml
│   │   ├── inventory-service-deployment.yaml
│   │   ├── finance-service-deployment.yaml
│   │   └── auth-service-deployment.yaml
│   ├── service/
│   │   ├── api-gateway-service.yaml
│   │   ├── customer-service-service.yaml
│   │   ├── repair-service-service.yaml
│   │   ├── inventory-service-service.yaml
│   │   ├── finance-service-service.yaml
│   │   └── auth-service-service.yaml
│   ├── ingress/
│   │   └── ingress.yaml
│   └── config/
│       ├── configmap.yaml
│       └── secret.yaml
├── .github/                 # GitHub配置
│   ├── workflows/
│   │   ├── ci.yml
│   │   ├── cd.yml
│   │   └── security.yml
│   └── dependabot.yml
├── config/                  # 配置文件
│   ├── application.yml
│   ├── application-dev.yml
│   ├── application-prod.yml
│   └── application-test.yml
├── tests/                   # 测试文件
│   ├── unit/
│   ├── integration/
│   └── e2e/
└── logs/                    # 日志目录（.gitignore）
```

## 各模块详细说明

### 1. 后端服务 (backend/)

#### 1.1 API网关 (api-gateway/)
- **技术栈**: Spring Cloud Gateway + Spring Boot
- **功能**:
  - 统一入口管理
  - 路由转发
  - 负载均衡
  - 限流熔断
  - 认证授权

#### 1.2 客户服务 (customer-service/)
- **功能模块**:
  - 客户信息管理
  - 会员分级标签系统
  - 车辆档案管理
  - 会员积分体系
  - 客户关怀计划
  - 营销活动管理
  - 满意度调查

#### 1.3 维修服务 (repair-service/)
- **功能模块**:
  - 全生命周期工单管理
  - 实时维修进度跟踪
  - 智能化维修预约系统
  - 标准化电子报表
  - 技师资源优化系统
  - 服务价格评估
  - 合规性质量管理
  - 智能结算系统

#### 1.4 库存服务 (inventory-service/)
- **功能模块**:
  - 基础数据管理
  - 库存管理
  - 采购管理
  - 供应商管理
  - 配件价格策略
  - 进销存跟踪
  - 财务业务管理

#### 1.5 财务服务 (finance-service/)
- **功能模块**:
  - 核心业务管理系统
  - 收费与支付管理
  - 预算与绩效管理
  - 报表与决策支持

#### 1.6 认证服务 (auth-service/)
- **功能模块**:
  - 用户认证
  - 权限管理
  - JWT令牌管理
  - OAuth2集成

#### 1.7 公共模块 (common/)
- **功能模块**:
  - DTO定义
  - 异常处理
  - 工具类
  - 配置类
  - 通用实体

### 2. 前端项目 (frontend/)

#### 2.1 管理后台 (admin-web/)
- **技术栈**: React 18 + TypeScript + Ant Design Pro + Umi
- **功能模块**:
  - 客户管理界面
  - 维修工单管理
  - 库存管理界面
  - 财务报表
  - 员工管理
  - 权限管理
  - 系统配置

#### 2.2 桌面应用 (desktop-app/)
- **技术栈**: Electron + React + TypeScript
- **功能模块**:
  - 离线数据同步
  - 本地数据缓存
  - 桌面通知
  - 打印功能
  - 离线操作支持

#### 2.3 微信小程序 (wechat-mini/)
- **技术栈**: 原生小程序或Taro框架
- **功能模块**:
  - 客户端功能
  - 预约服务
  - 进度跟踪
  - 消息推送
  - 支付功能

### 3. 数据库 (database/)

#### 3.1 初始化脚本
- 数据库结构创建
- 基础数据初始化
- 索引创建
- 存储过程定义

#### 3.2 迁移脚本
- Flyway数据库迁移
- 版本控制
- 回滚机制

### 4. 部署配置

#### 4.1 Docker配置
- 各服务Dockerfile
- docker-compose配置
- 网络配置
- 数据卷配置

#### 4.2 Kubernetes配置
- 部署配置文件
- 服务配置文件
- Ingress配置
- ConfigMap和Secret

### 5. CI/CD配置

#### 5.1 GitHub Actions
- 代码检查
- 单元测试
- 集成测试
- 自动部署

#### 5.2 脚本
- 部署脚本
- 备份脚本
- 恢复脚本
- 初始化脚本

## 技术栈说明

### 后端技术栈
- **语言**: Java 17
- **框架**: Spring Boot 3.x, Spring Cloud
- **持久层**: MyBatis-Plus
- **数据库**: PostgreSQL 14+
- **缓存**: Redis 7+
- **消息队列**: RabbitMQ
- **搜索**: Elasticsearch

### 前端技术栈
- **管理后台**: React 18 + TypeScript + Ant Design Pro
- **桌面应用**: Electron + React + TypeScript
- **小程序**: 原生小程序或Taro

### 部署技术栈
- **容器化**: Docker
- **编排**: Kubernetes
- **CI/CD**: GitHub Actions
- **监控**: Prometheus + Grafana

## 开发规范

### 代码规范
- 遵循Google Java Style Guide
- 使用Checkstyle进行代码检查
- 统一的命名规范
- 详细的代码注释

### 提交规范
- 遵循Conventional Commits
- 提交信息格式化
- 分支命名规范

### 文档规范
- API文档使用Swagger
- 代码文档使用Javadoc
- 项目文档使用Markdown
