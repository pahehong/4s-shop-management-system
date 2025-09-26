# 4S店管理系统解决方案

## 1. 系统概述

本方案旨在设计一个现代化的4S店管理系统，支持Windows桌面应用和微信小程序，能够直接部署在腾讯EdgeOne和微信小程序平台，具备高可维护性和可扩展性。

## 2. 系统架构

### 2.1 整体架构设计

系统采用微服务架构，分为以下几个层次：

```
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                        │
├─────────────────────────────────────────────────────────────┤
│  Windows Desktop App  │  WeChat Mini Program  │  Admin Web  │
├─────────────────────────────────────────────────────────────┤
│                    API Gateway Layer                         │
├─────────────────────────────────────────────────────────────┤
│  Customer Service  │  Repair Service  │  Inventory  │  ...  │
│     Module         │     Module       │   Module    │       │
├─────────────────────────────────────────────────────────────┤
│                    Data Access Layer                         │
├─────────────────────────────────────────────────────────────┤
│                    Database Layer                            │
└─────────────────────────────────────────────────────────────┘
```

### 2.2 技术架构

- **前端技术栈**：
  - Windows桌面应用：Electron + React + TypeScript
  - 微信小程序：原生微信小程序框架或Taro框架
  - 管理后台：React + TypeScript + Ant Design

- **后端技术栈**：
  - 主框架：Spring Boot 3.x + Java 17
  - API网关：Spring Cloud Gateway
  - 微服务通信：RESTful API + WebSocket
  - 认证授权：JWT + OAuth2

- **数据存储**：
  - 主数据库：PostgreSQL（事务性数据）
  - 缓存：Redis（会话、热点数据）
  - 文件存储：腾讯云COS（图片、文档）

- **通信方式**：
  - 前后端分离：RESTful API + WebSocket实时通信
  - 微服务间通信：同步HTTP调用 + 异步消息队列（RabbitMQ）

## 3. 技术栈选择

### 3.1 前端技术栈

#### Windows桌面应用
- **Electron**：跨平台桌面应用框架
- **React 18**：UI组件库
- **TypeScript**：类型安全
- **Ant Design**：UI组件库
- **Redux Toolkit**：状态管理

#### 微信小程序
- **原生小程序框架**或**Taro**：多端统一开发
- **TypeScript**：类型安全
- **微信小程序云开发**：后端服务

#### 管理后台
- **React 18**：前端框架
- **TypeScript**：类型安全
- **Ant Design Pro**：企业级UI框架
- **Umi**：企业级前端应用框架

### 3.2 后端技术栈

#### 核心框架
- **Spring Boot 3.x**：微服务基础框架
- **Spring Cloud**：微服务治理
- **Spring Security**：安全框架
- **MyBatis-Plus**：持久层框架

#### 数据存储
- **PostgreSQL 14+**：主数据库
- **Redis 7+**：缓存和会话存储
- **MongoDB**：非结构化数据存储

#### 消息队列与中间件
- **RabbitMQ**：消息队列
- **Elasticsearch**：全文搜索

#### 部署与运维
- **Docker**：容器化
- **Kubernetes**：容器编排
- **Nginx**：反向代理

## 4. 功能模块设计

根据需求文档，系统包含以下核心功能模块：

### 4.1 客户管理与关系维护模块

#### 4.1.1 客户基础信息管理
- 客户基本信息录入与维护（姓名、性别、年龄、电话号码、出生年月日、家庭地址、公司名称、公司地址等基础资料录入与维护）
- 身份验证（身份证/驾驶证信息采集与实名认证功能，电话号码+车牌号+身份证+姓名方式方便客户在车主端APP注册登录和预约看车或维修管理）
- 数据安全（敏感信息加密存储和访问权限分级控制）

#### 4.1.2 会员分级标签系统
- 价值分级（基于消费金额/频次分为VIP/优质/普通客户）
- 智能标签（30+标准标签+自定义标签，支持多标签组合查询）
- 智能推送（根据标签自动推送服务/营销活动/流失预警）

#### 4.1.3 车辆档案管理
- 车辆信息（车辆品牌、VIN码、车型、VIN码、车牌号码、购买日期、行驶里程、发动机号、颜色、出厂日期等）
- 维修记录（保养记录、维修记录、改装记录、配件更换记录、保险理赔记录，年审记录等）
- 保险质保（保险记录+原厂质保跟踪+延保管理）
- 数据分析（故障线索统计+维修项目推荐引擎）

#### 4.1.4 会员积分体系
- 积分规则（消费积分+活动奖励+有效期管理）
- 等级权益（白银/黄金/铂金等级专属权益配置）
- 积分商城（线上线下兑换渠道+核销记录）

#### 4.1.5 客户关怀计划
- 智能提醒（保养/保险/年检到期自动提醒）
- 专属服务（生日祝福+节假关怀活动）
- 投诉处理（多渠道接入+工单自动流转）

#### 4.1.6 营销活动管理
- 精准营销（目标客户筛选+个性化内容生成）
- 渠道整合（微信/短信/邮件等多平台统一管理）
- 效果分析（转化率统计+客户获取成本分析）

#### 4.1.7 满意度调查
- 智能问卷（自定义模板+多渠道自动发放）
- NPS分析（满意度评分计算+负面评价预警）
- 改进闭环（自动生成工单+处理进度跟踪）

### 4.2 维修与售后服务平台

#### 4.2.1 全生命周期工单管理
- 支持故障描述、检测诊断、维修方案、配件使用等全流程电子记录，实时显示工单状态并自动触发通知。

#### 4.2.2 实时维修进度跟踪
- 客户端APP/微信实时查看进度节点，技师工作看板展示当前任务队列，延期预警系统提前30分钟自动预警。

#### 4.2.3 智能化维修预约系统
- 全渠道接入（官网/APP/微信/电话等多渠道预约入口，实现客户触点全覆盖）
- 智能派工算法（基于维修车间产能、技师技能矩阵的智能派工算法，自动检测时间冲突（检测准确率99.5%））
- 客户数据联动（自动关联客户档案与VIN码信息，减少重复输入（数据关联准确率100%））
- 动态提醒机制（预约前24小时、2小时自动发送短信/微信提醒（送达率98%））

#### 4.2.4 标准化电子报表
- 200+标准化维修报表模板
- 电子签章认证（符合《电子签名法》）
- 多端实时同步（APP/微信/邮件）

#### 4.2.5 技师资源优化系统
- 技能矩阵管理（建立技师技能等级认证体系（包含50+认证项目））
- 智能排班算法（考虑技师专长、工作负荷、客户满意度系数的多目标优化）
- 绩效看板（工时完成量、客户满意度、维修质量评分、配件使用准确率）

#### 4.2.6 服务价格评估
- 自动触发机制（服务完成后2小时内发送评价请求）
- 多维度评价体系
  - 服务质量（5维度评分）
  - 时效性
  - 价格透明度
  - 环境舒适度
- 投诉处理（48小时内响应承诺，三级升级机制，整改效果跟踪）

#### 4.2.7 合规性质量管理
- 全流程电子化
  - 原厂/延保服务自动标识
  - 保修期精确计算（精确到小时）
  - 电子索赔流程（符合主机厂规范）
- 关联对接
  - 厂商结算数据自动对接
  - 维修配件全流程跟踪
- 档案管理
  - VIN码主键建立档案
  - 智能分析功能
  - OBD故障代码关联

#### 4.2.8 智能结算系统
- 动态定价引擎
  - 基础工时
  - 配件价格矩阵
  - 个性化促销策略
- 无感对接
  - 微信支付
  - 支付宝
  - 银联/会员积分
- 电子发票（实时开具电子发票，对接税控系统，支持多端推送方式）

### 4.3 库存与配件管理系统

#### 4.3.1 基础数据管理
- 配件基础数据库（包含配件编码、名称、规格、OE号、品牌、适用车型等详细信息，支持快速检索）
- 配件分类体系（采用总成/组件/零件的多级分类标准，便于库存结构化管理）
- 编码管理（支持OE编码、品牌编码和自定义编码等多编码体系，确保配件标识唯一性）

#### 4.3.2 库存管理
- 库存实时监控（可视化展示各仓库库存数量、位置状态，支持多维度筛选）
- 库存预警系统（设置最小/最大库存阈值，自动触发补货提醒和采购建议）
- 周转质量分析（计算配件周转周期，识别呆滞库存并提供处理建议）

#### 4.3.3 采购管理
- 智能采购需求分析（基于历史销售数据和库存状况自动生成采购建议）
- 供应商评价体系（从质量、交期、价格、服务等多维度供应商绩效）
- 采购全流程管理（支持订单生成、审批、跟踪、到货确认全流程数字化管理）

#### 4.3.4 供应商管理
- 供应商档案（完整记录基本信息、资质资料、联系人、付款条件等关键数据）
- 供应商分类（按配件类别、合作等级进行多维度分类管理）
- 电子化协同（采购订单在线协同，实时跟踪交期和到货状态）

#### 4.3.5 配件价格策略
- 多层级价格体系（支持采购价、零售价、最低售价等多重价格设置）
- 灵活调价机制（动态调价、促销调价、批量折扣等多维度价格策略）
- 价格审批流程（多级价格调整审批机制，确保价格变更合规）

#### 4.3.6 进销存跟踪
- 全流程记录（完整记录采购入库、销售出库、调拨等所有库存变动）
- 移动端盘点（支持手机/PAD进行实地盘点，实时同步盘点数据）
- 差异分析（自动生成盘点差异报告，分析损耗原因并提出改进建议）

#### 4.3.7 财务业务管理
- 采购付款管理（与财务系统对接，自动生成付款申请与采购订单）
- 销售收费核算（计算配件销售应收费用项，支持多种收费方式）
- 成本利润分析（分析配件销售毛利率，标识高利润和亏损品类）

### 4.4 财务与绩效管理系统

#### 4.4.1 核心业务管理系统
- 业务业务一体化设计
  - 销售订单自动对账：对接DMS系统实现订单、收费、发票三单匹配，减少人工核对工作量
  - 售后结算管理：维修工单与配件出库自动关联，支持多种结算方式（现金/刷卡/保险理赔）
  - 库存资金占用分析：按车型/配件自动计算周转资金需求，优化库存结构
- 资金流监控
  - 收支分类管理：区分12类资金流（销售/售后/采购等），实现资金流向透明化
  - 银行流水智能匹配：OCR识别技术实现95%自动对账，大幅减少人工操作
  - 现金流预测：基于历史数据建模，预测未来30天资金缺口，提前预警
- 智能成本控制体系
  - 单车全成本核算：精确计算采购、物流、整备等全链路成本
  - 工时分配标准：按工种/技能等级设置差异化工时标准
  - 配件采购比价：自动记录供应商报价历史，生成比价分析报告

#### 4.4.2 收费与支付管理
- 多渠道收费管理
  - POS/现金/转账/移动支付多渠道自动归类
  - 大额交易预警（单笔超5万元自动触发风控审核）
  - 电子收据自动生成与客户推送
- 智能对账系统
  - 支持15家主流银行流水自动匹配
  - 差异交易智能处理方案
  - 资金流向追溯查询
- 业务场景全覆盖
  - 新车销售：定金管理、金融分期收费、合规证件管控
  - 售后服务：工单关联收费、保险理赔核算、会员储值管理
  - 衍生业务：保险分期、精品加装分账

#### 4.4.3 预算与绩效管理
- 预算编制体系
  - 新车销售预算（分品牌/车型）
  - 售后产品预算（分业务类型）
  - 营销费用ROI预测
  - 13周现金流滚动预测
- 当工绩效考核
  - 销售顾问：成交转化率、金融渗透率、客户NPS
  - 服务顾问：工单产值、返修率、一次修复率
  - 技师：工时完成量、客户满意度、维修质量评分
- 薪金提成计算
  - 底薪+提成/团队提成/团队分项等多种模式
  - 车型系数数据库与KPI权重调整
  - 个人预扣税与电子签收

#### 4.4.4 报表与决策支持
- 报表自动生成
  - 资产负债表/利润表/现金流量表
  - 单店经营效益表（分业务板块）
  - 库存周转分析表
  - 移动端PDF/Excel推送
- 高级分析工具
  - 边际利润分析（保本点计算）
  - 折旧摊销敏感性分析
  - 成本结构解构（固定/变动成本）
- 可视化经营驾驶舱
  - 经营关键指标Dashboard
  - 异常指标预警与下钻分析
  - 库存采购建议与促销效果评估

### 4.5 综合办公与系统集成模块

#### 4.5.1 员工管理功能模块
- 员工信息管理
  - 基础信息管理（工号、部门、岗位、联系方式等）
  - 技能资质档案（维修资质认证、培训记录）
  - 考勤打卡记录
  - 薪资福利信息
- 考勤管理
  - 排班管理（支持维修、销售等不同岗位排班）
  - 打卡记录（支持指纹、人脸识别等多种方式）
  - 请假/调休申请
  - 加班管理
  - 考勤异常处理
- 绩效管理
  - KPI指标设定（维修量、满意度等）
  - 月度/季度考核
  - 绩效奖金计算
  - 绩效面谈记录

#### 4.5.2 权限管理功能模块
- 角色权限管理
  - 岗位权限模板（销售顾问/维修技师/财务等）
  - 部门权限划分
  - 数据防访问权限控制
  - 功能操作权限配置
- 用户管理
  - 用户账号创建与注销
  - 权限分配与调整
  - 多角色支持
  - 权限继承设置
- 安全审计
  - 操作日志记录
  - 异常访问报警
  - 权限变更追踪
  - 定期权限复核

#### 4.5.3 系统集成功能模块
- 外部系统对接接口
  - DMS系统对接
  - 主机厂系统对接
  - 财务系统集成
  - 第三方支付对接
- 数据交互
  - 实时数据同步
  - 批量数据导入导出
  - 数据格式转换
  - 数据校验机制
- API管理
  - API文档管理
  - 防访问权限控制
  - 调用监控
  - 异常处理

#### 4.5.4 其他综合办公功能
- 工作流审批
  - 采购审批
  - 费用报销
  - 请假申请
  - 维修项目变更
  - 客户折扣申请
- 数据备份恢复
  - 定时自动备份
  - 增量/全量备份
  - 多重备份机制
  - 异地容灾
- 内部通讯
  - 一对一对话
  - 部门群组
  - 全店公告
  - 任务协作

#### 4.5.5 系统安全与合规管理
- 数据加密存储与传输
- GDPR等数据保护法规合规
- 敏感数据保护
- 防火墙安全审计
- 安全漏洞管理

## 5. 数据库设计

### 5.1 核心实体关系模型

```sql
-- 客户信息表
CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    customer_code VARCHAR(20) UNIQUE NOT NULL, -- 客户编码
    name VARCHAR(50) NOT NULL,
    gender VARCHAR(10),
    age INTEGER,
    phone VARCHAR(20) UNIQUE NOT NULL,
    id_card VARCHAR(20), -- 身份证号
    address TEXT,
    company_name VARCHAR(100),
    company_address TEXT,
    email VARCHAR(100),
    birthday DATE,
    customer_level VARCHAR(20) DEFAULT 'NORMAL', -- VIP/优质/普通
    total_consumption DECIMAL(12,2) DEFAULT 0,
    points INTEGER DEFAULT 0,
    status VARCHAR(20) DEFAULT 'ACTIVE', -- 激活/禁用
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 车辆信息表
CREATE TABLE vehicles (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT REFERENCES customers(id),
    vin VARCHAR(17) UNIQUE NOT NULL, -- 车架号
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(100) NOT NULL,
    license_plate VARCHAR(20),
    purchase_date DATE,
    mileage DECIMAL(10,2),
    engine_number VARCHAR(50),
    color VARCHAR(20),
    production_date DATE,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 配件信息表
CREATE TABLE parts (
    id BIGSERIAL PRIMARY KEY,
    part_code VARCHAR(30) UNIQUE NOT NULL, -- 配件编码
    name VARCHAR(100) NOT NULL,
    specification TEXT,
    oe_number VARCHAR(50), -- OE号
    brand VARCHAR(50),
    category_id BIGINT REFERENCES part_categories(id),
    unit VARCHAR(20),
    unit_price DECIMAL(10,2),
    cost_price DECIMAL(10,2),
    min_stock INTEGER DEFAULT 0,
    max_stock INTEGER DEFAULT 0,
    supplier_id BIGINT REFERENCES suppliers(id),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 库存表
CREATE TABLE inventory (
    id BIGSERIAL PRIMARY KEY,
    part_id BIGINT REFERENCES parts(id),
    warehouse_id BIGINT REFERENCES warehouses(id),
    quantity INTEGER DEFAULT 0,
    location VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 工单表
CREATE TABLE work_orders (
    id BIGSERIAL PRIMARY KEY,
    order_number VARCHAR(30) UNIQUE NOT NULL,
    customer_id BIGINT REFERENCES customers(id),
    vehicle_id BIGINT REFERENCES vehicles(id),
    order_type VARCHAR(20) NOT NULL, -- 维修/保养/改装
    status VARCHAR(20) DEFAULT 'PENDING', -- 待处理/处理中/已完成/已取消
    priority VARCHAR(10) DEFAULT 'NORMAL', -- 紧急/正常/低优先级
    description TEXT,
    estimated_hours DECIMAL(5,2),
    actual_hours DECIMAL(5,2),
    estimated_cost DECIMAL(10,2),
    actual_cost DECIMAL(10,2),
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 工单配件明细表
CREATE TABLE work_order_parts (
    id BIGSERIAL PRIMARY KEY,
    work_order_id BIGINT REFERENCES work_orders(id),
    part_id BIGINT REFERENCES parts(id),
    quantity INTEGER NOT NULL,
    unit_price DECIMAL(10,2),
    total_price DECIMAL(10,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 员工信息表
CREATE TABLE employees (
    id BIGSERIAL PRIMARY KEY,
    employee_code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(50) NOT NULL,
    gender VARCHAR(10),
    phone VARCHAR(20),
    department_id BIGINT REFERENCES departments(id),
    position VARCHAR(50),
    skill_level VARCHAR(20), -- 技师等级
    hire_date DATE,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 部门信息表
CREATE TABLE departments (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT,
    parent_id BIGINT REFERENCES departments(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 供应商信息表
CREATE TABLE suppliers (
    id BIGSERIAL PRIMARY KEY,
    supplier_code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    contact_person VARCHAR(50),
    phone VARCHAR(20),
    email VARCHAR(100),
    address TEXT,
    rating DECIMAL(3,2), -- 供应商评级
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 采购订单表
CREATE TABLE purchase_orders (
    id BIGSERIAL PRIMARY KEY,
    po_number VARCHAR(30) UNIQUE NOT NULL,
    supplier_id BIGINT REFERENCES suppliers(id),
    total_amount DECIMAL(12,2),
    status VARCHAR(20) DEFAULT 'PENDING', -- 待审批/已批准/已取消/已完成
    expected_delivery_date DATE,
    actual_delivery_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 采购订单明细表
CREATE TABLE purchase_order_items (
    id BIGSERIAL PRIMARY KEY,
    po_id BIGINT REFERENCES purchase_orders(id),
    part_id BIGINT REFERENCES parts(id),
    quantity INTEGER NOT NULL,
    unit_price DECIMAL(10,2),
    total_price DECIMAL(10,2),
    received_quantity INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 收费记录表
CREATE TABLE payment_records (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT, -- 关联工单ID或销售订单ID
    order_type VARCHAR(20), -- 工单/销售订单
    customer_id BIGINT REFERENCES customers(id),
    amount DECIMAL(12,2) NOT NULL,
    payment_method VARCHAR(20), -- 现金/刷卡/微信/支付宝/银行转账
    payment_status VARCHAR(20) DEFAULT 'PENDING', -- 待支付/已支付/已退款
    transaction_id VARCHAR(100), -- 第三方交易号
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    paid_at TIMESTAMP
);

-- 用户权限表
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    employee_id BIGINT REFERENCES employees(id),
    password_hash VARCHAR(255) NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 角色表
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 用户角色关联表
CREATE TABLE user_roles (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    role_id BIGINT REFERENCES roles(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 权限表
CREATE TABLE permissions (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    resource VARCHAR(100), -- 资源类型
    action VARCHAR(50), -- 操作类型
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 角色权限关联表
CREATE TABLE role_permissions (
    id BIGSERIAL PRIMARY KEY,
    role_id BIGINT REFERENCES roles(id),
    permission_id BIGINT REFERENCES permissions(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 操作日志表
CREATE TABLE operation_logs (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    operation VARCHAR(100) NOT NULL,
    resource_type VARCHAR(50),
    resource_id BIGINT,
    old_values JSONB,
    new_values JSONB,
    ip_address INET,
    user_agent TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 5.2 数据库索引设计

```sql
-- 客户表索引
CREATE INDEX idx_customers_phone ON customers(phone);
CREATE INDEX idx_customers_level ON customers(customer_level);
CREATE INDEX idx_customers_status ON customers(status);

-- 车辆表索引
CREATE INDEX idx_vehicles_vin ON vehicles(vin);
CREATE INDEX idx_vehicles_customer_id ON vehicles(customer_id);
CREATE INDEX idx_vehicles_license_plate ON vehicles(license_plate);

-- 配件表索引
CREATE INDEX idx_parts_code ON parts(part_code);
CREATE INDEX idx_parts_category ON parts(category_id);
CREATE INDEX idx_parts_supplier ON parts(supplier_id);

-- 库存表索引
CREATE INDEX idx_inventory_part_warehouse ON inventory(part_id, warehouse_id);

-- 工单表索引
CREATE INDEX idx_work_orders_customer ON work_orders(customer_id);
CREATE INDEX idx_work_orders_vehicle ON work_orders(vehicle_id);
CREATE INDEX idx_work_orders_status ON work_orders(status);
CREATE INDEX idx_work_orders_created_at ON work_orders(created_at);

-- 员工表索引
CREATE INDEX idx_employees_code ON employees(employee_code);
CREATE INDEX idx_employees_department ON employees(department_id);

-- 供应商表索引
CREATE INDEX idx_suppliers_code ON suppliers(supplier_code);

-- 收费记录表索引
CREATE INDEX idx_payments_customer ON payment_records(customer_id);
CREATE INDEX idx_payments_status ON payment_records(payment_status);
CREATE INDEX idx_payments_created_at ON payment_records(created_at);

-- 用户表索引
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_employee ON users(employee_id);

-- 操作日志表索引
CREATE INDEX idx_operation_logs_user ON operation_logs(user_id);
CREATE INDEX idx_operation_logs_created_at ON operation_logs(created_at);
```

### 5.3 数据库分区策略

对于大数据量的表，采用分区策略：

```sql
-- 操作日志按月分区
CREATE TABLE operation_logs (
    id BIGSERIAL,
    user_id BIGINT,
    operation VARCHAR(100) NOT NULL,
    resource_type VARCHAR(50),
    resource_id BIGINT,
    old_values JSONB,
    new_values JSONB,
    ip_address INET,
    user_agent TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) PARTITION BY RANGE (created_at);

-- 收费记录按月分区
CREATE TABLE payment_records (
    id BIGSERIAL,
    order_id BIGINT,
    order_type VARCHAR(20),
    customer_id BIGINT,
    amount DECIMAL(12,2) NOT NULL,
    payment_method VARCHAR(20),
    payment_status VARCHAR(20) DEFAULT 'PENDING',
    transaction_id VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    paid_at TIMESTAMP
) PARTITION BY RANGE (created_at);
```

## 6. 部署方案

### 6.1 服务器配置

#### 6.1.1 生产环境配置
- **云服务器**：腾讯云CVM
  - CPU：8核或以上
  - 内存：16GB或以上
  - 存储：SSD云硬盘，200GB以上
  - 带宽：100Mbps或以上

- **数据库服务器**：
  - PostgreSQL：16GB内存，高性能SSD
  - Redis：8GB内存，支持持久化

- **负载均衡**：腾讯云CLB，支持HTTPS卸载

#### 6.1.2 容器化部署
- **Docker镜像**：
  - 后端服务：openjdk:17-jre-slim
  - 数据库：postgres:14-alpine
  - 缓存：redis:7-alpine
  - 消息队列：rabbitmq:3-management

- **Kubernetes集群**：
  - Master节点：2核4GB
  - Worker节点：4核8GB，根据负载动态扩展

### 6.2 安全性设计

#### 6.2.1 网络安全
- **防火墙配置**：仅开放必要端口（80, 443, 22等）
- **DDoS防护**：启用腾讯云DDoS防护
- **WAF防护**：部署Web应用防火墙

#### 6.2.2 数据安全
- **数据加密**：
  - 传输加密：TLS 1.3
  - 存储加密：敏感字段AES-256加密
- **访问控制**：
  - 基于角色的访问控制（RBAC）
  - API访问频率限制
  - 操作日志审计

#### 6.2.3 应用安全
- **认证授权**：JWT Token + OAuth2
- **输入验证**：防止SQL注入、XSS攻击
- **安全头设置**：CSP、HSTS等

### 6.3 扩展性设计

#### 6.3.1 水平扩展
- **微服务架构**：各模块独立部署，支持独立扩展
- **负载均衡**：支持多实例部署
- **数据库读写分离**：主库写入，从库读取

#### 6.3.2 垂直扩展
- **缓存策略**：Redis多级缓存
- **CDN加速**：静态资源CDN分发
- **异步处理**：消息队列解耦

### 6.4 微信小程序部署

#### 6.4.1 小程序开发
- **开发框架**：原生小程序或Taro
- **API接口**：统一API网关接入
- **安全认证**：微信登录授权

#### 6.4.2 小程序发布
- **代码审核**：遵循微信小程序规范
- **版本管理**：支持灰度发布
- **数据分析**：集成微信统计分析

### 6.5 腾讯EdgeOne部署

#### 6.5.1 EdgeOne配置
- **域名配置**：绑定自定义域名
- **SSL证书**：自动HTTPS加密
- **缓存策略**：静态资源缓存优化

#### 6.5.2 CDN加速
- **静态资源**：CSS、JS、图片等
- **动态内容**：API响应缓存
- **全球加速**：多节点部署

### 6.6 监控与运维

#### 6.6.1 系统监控
- **性能监控**：CPU、内存、磁盘、网络
- **应用监控**：响应时间、错误率、吞吐量
- **业务监控**：关键业务指标

#### 6.6.2 日志管理
- **集中日志**：ELK Stack收集分析
- **审计日志**：操作行为记录
- **告警机制**：异常自动告警

#### 6.6.3 备份策略
- **数据备份**：每日全量+实时增量
- **异地备份**：跨区域备份存储
- **恢复测试**：定期恢复演练

## 7. 开发与部署流程

### 7.1 CI/CD流程

```yaml
# .github/workflows/deploy.yml
name: CI/CD Pipeline

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'
    
    - name: Build with Maven
      run: |
        cd backend
        mvn clean test
    
    - name: Run Integration Tests
      run: |
        cd backend
        mvn verify

  build-and-push:
    needs: test
    runs-on: ubuntu-latest
    if: github.ref == 'refs/heads/main'
    steps:
    - name: Checkout
      uses: actions/checkout@v3
    
    - name: Set up Docker Buildx
      uses: docker/setup-buildx-action@v2
    
    - name: Login to Container Registry
      uses: docker/login-action@v2
      with:
        registry: ccr.ccs.tencentyun.com
        username: ${{ secrets.TENCENT_USERNAME }}
        password: ${{ secrets.TENCENT_PASSWORD }}
    
    - name: Build and push backend
      uses: docker/build-push-action@v4
      with:
        context: ./backend
        push: true
        tags: ccr.ccs.tencentyun.com/4s-shop/backend:${{ github.sha }}
        cache-from: type=gha
        cache-to: type=gha,mode=max

  deploy:
    needs: build-and-push
    runs-on: ubuntu-latest
    steps:
    - name: Deploy to Tencent Kubernetes
      run: |
        # 部署到腾讯云Kubernetes集群
        kubectl set image deployment/backend-deployment backend=ccr.ccs.tencentyun.com/4s-shop/backend:${{ github.sha }}
        kubectl rollout status deployment/backend-deployment
```

### 7.2 部署脚本

```bash
#!/bin/bash
# deploy.sh - 部署脚本

set -e

echo "开始部署4S店管理系统..."

# 拉取最新代码
git pull origin main

# 构建Docker镜像
docker-compose build

# 启动服务
docker-compose up -d

# 运行数据库迁移
docker-compose exec backend ./migrate.sh

echo "部署完成！"
```

## 8. 总结

本方案设计了一个现代化的4S店管理系统，具备以下特点：

1. **架构先进**：采用微服务架构，支持高并发和水平扩展
2. **技术领先**：使用最新的技术栈，确保系统性能和可维护性
3. **功能全面**：覆盖客户管理、维修服务、库存管理、财务管理等核心业务
4. **安全可靠**：多层次安全防护，确保数据安全和系统稳定
5. **易于部署**：支持腾讯EdgeOne和微信小程序，便于快速上线
6. **可扩展性强**：模块化设计，支持业务扩展和功能迭代

该方案可以直接部署实施，为4S店提供全方位的数字化管理解决方案。
