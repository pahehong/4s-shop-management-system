# 4S店管理系统

## 项目概述

这是一个基于微服务架构的4S店管理系统，采用monorepo结构，包含后端服务、管理后台、桌面应用和移动应用。

## 项目结构

```
4s-shop-management-system/
├── backend/                 # 后端微服务
│   ├── pom.xml             # 后端项目根pom
│   └── services/           # 各微服务模块
│       ├── api-gateway/
│       ├── customer-service/
│       ├── repair-service/
│       ├── inventory-service/
│       ├── finance-service/
│       └── auth-service/
├── web-frontend/           # Web前端（管理后台）
│   ├── package.json
│   ├── src/
│   └── public/
├── desktop-app/            # 桌面应用
│   ├── package.json
│   ├── src/
│   └── electron/
└── mobile-app/             # 移动应用（微信小程序）
    ├── project.config.json
    └── src/
```

## 技术栈

### 后端
- **语言**: Java 17
- **框架**: Spring Boot 3.x, Spring Cloud
- **数据库**: PostgreSQL
- **缓存**: Redis
- **消息队列**: RabbitMQ

### 前端
- **管理后台**: React 18 + TypeScript + Ant Design
- **桌面应用**: Electron + React
- **移动应用**: 微信小程序

## 快速开始

### 后端启动

```bash
cd backend
./mvnw spring-boot:run -pl services/api-gateway
```

### 前端启动

```bash
cd web-frontend
npm install
npm start
```

### 桌面应用启动

```bash
cd desktop-app
npm install
npm start
```

## 开发规范

- 代码提交遵循 Conventional Commits 规范
- 使用 ESLint 和 Checkstyle 进行代码检查
- 所有代码必须包含单元测试
- API 文档使用 Swagger 生成

## 部署

项目支持 Docker 和 Kubernetes 部署，相关配置文件位于 `deploy/` 目录下。
