
# 4S店管理系统数据库设计文档

## 1. 核心实体列表

根据4S店管理系统的需求，核心实体包括：

### 1.1 客户管理实体
- **Customer** (客户信息)
- **CustomerTag** (客户标签)
- **CustomerLevel** (客户等级)
- **CustomerPoints** (客户积分)

### 1.2 车辆管理实体
- **Vehicle** (车辆信息)
- **VehicleMaintenance** (车辆保养记录)
- **VehicleRepair** (车辆维修记录)
- **VehicleInsurance** (车辆保险信息)

### 1.3 维修服务实体
- **WorkOrder** (工单信息)
- **WorkOrderItem** (工单项目)
- **WorkOrderPart** (工单配件)
- **Technician** (技师信息)
- **ServiceType** (服务类型)

### 1.4 库存管理实体
- **Part** (配件信息)
- **PartCategory** (配件分类)
- **Inventory** (库存信息)
- **Warehouse** (仓库信息)
- **Supplier** (供应商信息)
- **PurchaseOrder** (采购订单)
- **PurchaseOrderItem** (采购订单项)

### 1.5 财务管理实体
- **PaymentRecord** (支付记录)
- **Invoice** (发票信息)
- **FinancialTransaction** (财务交易)
- **PricingStrategy** (定价策略)

### 1.6 员工管理实体
- **Employee** (员工信息)
- **Department** (部门信息)
- **Position** (职位信息)
- **UserRole** (用户角色)
- **Permission** (权限信息)

### 1.7 系统管理实体
- **User** (用户信息)
- **OperationLog** (操作日志)
- **SystemConfig** (系统配置)

## 2. 完整SQL建表语句

```sql
-- 部门信息表
CREATE TABLE departments (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT,
    parent_id BIGINT REFERENCES departments(id),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 职位信息表
CREATE TABLE positions (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT,
    department_id BIGINT REFERENCES departments(id),
    level INTEGER DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 员工信息表
CREATE TABLE employees (
    id BIGSERIAL PRIMARY KEY,
    employee_code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(50) NOT NULL,
    gender VARCHAR(10),
    phone VARCHAR(20),
    email VARCHAR(100),
    department_id BIGINT REFERENCES departments(id),
    position_id BIGINT REFERENCES positions(id),
    skill_level VARCHAR(20), -- 技师等级
    hire_date DATE,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

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

-- 客户标签表
CREATE TABLE customer_tags (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT,
    tag_type VARCHAR(20) DEFAULT 'STANDARD', -- STANDARD/CUSTOM
    color VARCHAR(10) DEFAULT '#007AFF',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 客户标签关联表
CREATE TABLE customer_customer_tags (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT REFERENCES customers(id),
    tag_id BIGINT REFERENCES customer_tags(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
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
    fuel_type VARCHAR(20), -- 燃油类型
    transmission_type VARCHAR(20), -- 变速箱类型
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 配件分类表
CREATE TABLE part_categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT,
    parent_id BIGINT REFERENCES part_categories(id),
    level INTEGER DEFAULT 1,
    path VARCHAR(500), -- 分类路径，用于快速查询
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

-- 仓库信息表
CREATE TABLE warehouses (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    location VARCHAR(100),
    manager_id BIGINT REFERENCES employees(id),
    capacity INTEGER,
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
    reserved_quantity INTEGER DEFAULT 0, -- 预留数量
    available_quantity INTEGER GENERATED ALWAYS AS (quantity - reserved_quantity) STORED,
    location VARCHAR(50),
    batch_number VARCHAR(50), -- 批次号
    production_date DATE,
    expiry_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 服务类型表
CREATE TABLE service_types (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT,
    base_hours DECIMAL(5,2), -- 基础工时
    base_price DECIMAL(10,2), -- 基础价格
    category VARCHAR(50), -- 服务分类
    status VARCHAR(20) DEFAULT 'ACTIVE',
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
    scheduled_start_time TIMESTAMP, -- 预约开始时间
    scheduled_end_time TIMESTAMP, -- 预约结束时间
    assigned_technician_id BIGINT REFERENCES employees(id), -- 分配的技师
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 工单项目表
CREATE TABLE work_order_items (
    id BIGSERIAL PRIMARY KEY,
    work_order_id BIGINT REFERENCES work_orders(id),
    service_type_id BIGINT REFERENCES service_types(id),
    description TEXT,
    hours DECIMAL(5,2),
    unit_price DECIMAL(10,2),
    total_price DECIMAL(10,2),
    status VARCHAR(20) DEFAULT 'PENDING', -- 待处理/进行中/已完成
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 工单配件明细表
CREATE TABLE work_order_parts (
    id BIGSERIAL PRIMARY KEY,
    work_order_id BIGINT REFERENCES work_orders(id),
    part_id BIGINT REFERENCES parts(id),
    quantity INTEGER NOT NULL,
    unit_price DECIMAL(10,2),
    total_price DECIMAL(10,2),
    warehouse_id BIGINT REFERENCES warehouses(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 采购订单表
CREATE TABLE purchase_orders (
    id BIGSERIAL PRIMARY KEY,
    po_number VARCHAR(30) UNIQUE NOT NULL,
    supplier_id BIGINT REFERENCES suppliers(id),
    employee_id BIGINT REFERENCES employees(id), -- 采购员
    total_amount DECIMAL(12,2),
    status VARCHAR(20) DEFAULT 'PENDING', -- 待审批/已批准/已取消/已完成
    expected_delivery_date DATE,
    actual_delivery_date DATE,
    notes TEXT,
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

-- 支付记录表
CREATE TABLE payment_records (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT, -- 关联工单ID或销售订单ID
    order_type VARCHAR(20), -- 工单/销售订单
    customer_id BIGINT REFERENCES customers(id),
    amount DECIMAL(12,2) NOT NULL,
    payment_method VARCHAR(20), -- 现金/刷卡/微信/支付宝/银行转账
    payment_status VARCHAR(20) DEFAULT 'PENDING', -- 待支付/已支付/已退款
    transaction_id VARCHAR(100), -- 第三方交易号
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    paid_at TIMESTAMP
);

-- 发票信息表
CREATE TABLE invoices (
    id BIGSERIAL PRIMARY KEY,
    invoice_number VARCHAR(30) UNIQUE NOT NULL,
    work_order_id BIGINT REFERENCES work_orders(id),
    customer_id BIGINT REFERENCES customers(id),
    total_amount DECIMAL(12,2),
    tax_amount DECIMAL(12,2),
    invoice_status VARCHAR(20) DEFAULT 'DRAFT', -- 草稿/已开具/已发送/已支付
    invoice_type VARCHAR(20) DEFAULT 'NORMAL', -- 普通/专用/电子
    issued_date DATE,
    due_date DATE,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 用户信息表
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    employee_id BIGINT REFERENCES employees(id),
    password_hash VARCHAR(255) NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    last_login_at TIMESTAMP,
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

-- 系统配置表
CREATE TABLE system_configs (
    id BIGSERIAL PRIMARY KEY,
    config_key VARCHAR(100) UNIQUE NOT NULL,
    config_value TEXT,
    description TEXT,
    config_type VARCHAR(50), -- STRING/INTEGER/BOOLEAN/JSON
    updated_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 车辆保养记录表
CREATE TABLE vehicle_maintenances (
    id BIGSERIAL PRIMARY KEY,
    vehicle_id BIGINT REFERENCES vehicles(id),
    maintenance_type VARCHAR(50) NOT NULL, -- 保养类型
    maintenance_date DATE NOT NULL,
    mileage_at_service DECIMAL(10,2),
    next_maintenance_mileage DECIMAL(10,2),
    next_maintenance_date DATE,
    description TEXT,
    cost DECIMAL(10,2),
    service_advisor_id BIGINT REFERENCES employees(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 车辆维修记录表
CREATE TABLE vehicle_repairs (
    id BIGSERIAL PRIMARY KEY,
    vehicle_id BIGINT REFERENCES vehicles(id),
    repair_date DATE NOT NULL,
    fault_description TEXT,
    repair_description TEXT,
    cost DECIMAL(10,2),
    technician_id BIGINT REFERENCES employees(id),
    warranty_period INTEGER, -- 保修期（天）
    warranty_expiry_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 车辆保险信息表
CREATE TABLE vehicle_insurances (
    id BIGSERIAL PRIMARY KEY,
    vehicle_id BIGINT REFERENCES vehicles(id),
    insurance_company VARCHAR(100),
    policy_number VARCHAR(50) UNIQUE,
    insurance_type VARCHAR(50), -- 交强险/商业险
    start_date DATE,
    end_date DATE,
    premium DECIMAL(10,2), -- 保费
    coverage_amount DECIMAL(12,2), -- 保额
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## 3. 实体关系图 (ERD)

```
                    +-------------------+
                    |    customers      |
                    +-------------------+
                    | id (PK)           |
                    | customer_code     |
                    | name              |
                    | phone (UK)        |
                    | ...               |
                    +-------------------+
                            |
                            | (1:M)
                            |
                    +-------------------+
                    |     vehicles      |
                    +-------------------+
                    | id (PK)           |
                    | customer_id (FK)  |
                    | vin (UK)          |
                    | brand             |
                    | model             |
                    | ...               |
                    +-------------------+
                            |
                            | (1:M)
                            |
                    +-------------------+
                    |   work_orders     |
                    +-------------------+
                    | id (PK)           |
                    | customer_id (FK)  |
                    | vehicle_id (FK)   |
                    | order_number (UK) |
                    | status            |
                    | ...               |
                    +-------------------+
                            |
                    +-------+-------+
                    |               |
            +---------------+   +---------------+
            | work_order_items|   |work_order_parts|
            +---------------+   +---------------+
            | id (PK)       |   | id (PK)       |
            | wo_id (FK)    |   | wo_id (FK)    |
            | service_id(FK)|   | part_id (FK)  |
            | ...           |   | ...           |
            +---------------+   +---------------+
                    |               |
                    +-------+-------+
                            |
                    +---------------+
                    |     parts     |
                    +---------------+
                    | id (PK)       |
                    | part_code(UK) |
                    | name          |
                    | ...           |
                    +---------------+
                            |
                            | (1:M)
                    +---------------+
                    |   inventory   |
                    +---------------+
                    | id (PK)       |
                    | part_id (FK)  |
                    | warehouse_id(FK)|
                    | quantity      |
                    | ...           |
                    +---------------+

+-------------------+       +-------------------+
|    employees      |       |    suppliers      |
+-------------------+       +-------------------+
| id (PK)           |       | id (PK)           |
| employee_code(UK) |       | supplier_code(UK) |
| name              |       | name              |
| department_id(FK) |       | ...               |
| position_id (FK)  |       +-------------------+
| ...               |
+-------------------+
        |
        | (1:M)
+---------------+
| departments   |
+---------------+
| id (PK)       |
| name          |
| parent_id(FK) |
| ...           |
+---------------+

+---------------+       +-------------------+
|    users      |-------|      roles        |
+---------------+   |   +-------------------+
| id (PK)       |   |   | id (PK)           |
| username (UK) |   |   | name              |
| employee_id(FK)|  |   | description       |
| ...           |   |   +-------------------+
+---------------+   |           |
        |           |           | (M:N)
        | (M:N)     |           |
+---------------+   +-------+---------------+
|  user_roles   |-----------| role_permissions|
+---------------+           +---------------+
| id (PK)       |           | id (PK)       |
| user_id (FK)  |           | role_id (FK)  |
| role_id (FK)  |           | perm_id (FK)  |
+---------------+           +---------------+
                                    |
                                    | (1:M)
                            +---------------+
                            | permissions   |
                            +---------------+
                            | id (PK)       |
                            | name          |
                            | resource      |
                            | action        |
                            +---------------+
```

## 4. 索引优化建议

### 4.1 主要查询优化索引

```sql
-- 客户表索引
CREATE INDEX idx_customers_phone ON customers(phone);
CREATE INDEX idx_customers_level ON customers(customer_level);
CREATE INDEX idx_customers_status ON customers(status);
CREATE INDEX idx_customers_created_at ON customers(created_at);

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
CREATE INDEX idx_inventory_available ON inventory(available_quantity);

-- 工单表索引
CREATE INDEX idx_work_orders_customer ON work_orders(customer_id);
CREATE INDEX idx_work_orders_vehicle ON work_orders(vehicle_id);
CREATE INDEX idx_work_orders_status ON work_orders(status);
CREATE INDEX idx_work_orders_created_at ON work_orders(created_at);
CREATE INDEX idx_work_orders_assigned_tech ON work_orders(assigned_technician_id);

-- 支付记录表索引
CREATE INDEX idx_payments_customer ON payment_records(customer_id);
CREATE INDEX idx_payments_status ON payment_records(payment_status);
CREATE INDEX idx_payments_created_at ON payment_records(created_at);

-- 用户表索引
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_employee ON users(employee_id);

-- 操作日志表索引
CREATE INDEX idx_operation_logs_user ON operation_logs(user_id);
CREATE INDEX idx_operation_logs_resource ON operation_logs(resource_type, resource_id);
CREATE INDEX idx_operation_logs_created_at ON operation_logs(created_at);

-- 采购订单表索引
CREATE INDEX idx_purchase_orders_supplier ON purchase_orders(supplier_id);
CREATE INDEX idx_purchase_orders_status ON purchase_orders(status);
CREATE INDEX idx_purchase_orders_created_at ON purchase_orders(created_at);
```

### 4.2 复合查询优化索引

```sql
-- 工单状态和类型的组合查询
CREATE INDEX idx_work_orders_status_type ON work_orders(status, order_type);

-- 按日期范围查询工单
CREATE INDEX idx_work_orders_date_range ON work_orders(created_at, status);

-- 客户消费金额排序查询
CREATE INDEX idx_customers_consumption ON customers(total_consumption DESC);

-- 库存预警查询
CREATE INDEX idx_inventory_low_stock ON inventory(warehouse_id, part_id) 
WHERE quantity <= min_stock;
```

### 4.3 全文搜索索引

```sql
-- 客户姓名和电话全文搜索
CREATE INDEX idx_customers_fulltext ON customers 
USING gin(to_tsvector('chinese', name || ' ' || phone));

-- 配件名称和规格全文搜索
CREATE INDEX idx_parts_fulltext ON parts 
USING gin(to_tsvector('chinese', name || ' ' || specification));
```

## 5. JPA实体类代码框架

### 5.1 Customer实体类

```java
package com.4s.shop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers", indexes = {
    @Index(name = "idx_customers_phone", columnList = "phone"),
    @Index(name = "idx_customers_level", columnList = "customer_level"),
    @Index(name = "idx_customers_status", columnList = "status")
})
@Data
@EqualsAndHashCode(callSuper = false)
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "customer_code", unique = true, nullable = false, length = 20)
    private String customerCode;
    
    @Column(nullable = false, length = 50)
    private String name;
    
    @Column(length = 10)
    private String gender;
    
    private Integer age;
    
    @Column(unique = true, nullable = false, length = 20)
    private String phone;
    
    @Column(name = "id_card", length = 20)
    private String idCard;
    
    @Column(columnDefinition = "TEXT")
    private String address;
    
    @Column(name = "company_name", length = 100)
    private String companyName;
    
    @Column(name = "company_address", length = 200)
    private String companyAddress;
    
    @Column(length = 100)
    private String email;
    
    private LocalDate birthday;
    
    @Column(name = "customer_level", length = 20)
    @Enumerated(EnumType.STRING)
    private CustomerLevelEnum customerLevel = CustomerLevelEnum.NORMAL;
    
    @Column(name = "total_consumption", precision = 12, scale = 2)
    private BigDecimal totalConsumption = BigDecimal.ZERO;
    
    private Integer points = 0;
    
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.ACTIVE;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum CustomerLevelEnum {
        VIP, PREMIUM, NORMAL
    }
    
    public enum StatusEnum {
        ACTIVE, INACTIVE, SUSPENDED
    }
}
```

### 5.2 Vehicle实体类

```java
package com.4s.shop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles", indexes = {
    @Index(name = "idx_vehicles_vin", columnList = "vin"),
    @Index(name = "idx_vehicles_customer_id", columnList = "customer_id"),
    @Index(name = "idx_vehicles_license_plate", columnList = "license_plate")
})
@Data
@EqualsAndHashCode(callSuper = false)
public class Vehicle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @Column(name = "vin", unique = true, nullable = false, length = 17)
    private String vin;
    
    @Column(nullable = false, length = 50)
    private String brand;
    
    @Column(nullable = false, length = 100)
    private String model;
    
    @Column(name = "license_plate", length = 20)
    private String licensePlate;
    
    @Column(name = "purchase_date")
    private LocalDate purchaseDate;
    
    @Column(name = "mileage", precision = 10, scale = 2)
    private BigDecimal mileage;
    
    @Column(name = "engine_number", length = 50)
    private String engineNumber;
    
    @Column(length = 20)
    private String color;
    
    @Column(name = "production_date")
    private LocalDate productionDate;
    
    @Column(name = "fuel_type", length = 20)
    private String fuelType;
    
    @Column(name = "transmission_type", length = 20)
    private String transmissionType;
    
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.ACTIVE;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum StatusEnum {
        ACTIVE, INACTIVE, SOLD
    }
}
```

### 5.3 Part实体类

```java
package com.4s.shop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "parts", indexes = {
    @Index(name = "idx_parts_code", columnList = "part_code"),
    @Index(name = "idx_parts_category", columnList = "category_id"),
    @Index(name = "idx_parts_supplier", columnList = "supplier_id")
})
@Data
@EqualsAndHashCode(callSuper = false)
public class Part {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "part_code", unique = true, nullable = false, length = 30)
    private String partCode;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String specification;
    
    @Column(name = "oe_number", length = 50)
    private String oeNumber;
    
    @Column(length = 50)
    private String brand;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private PartCategory category;
    
    @Column(length = 20)
    private String unit;
    
    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;
    
    @Column(name = "cost_price", precision = 10, scale = 2)
    private BigDecimal costPrice;
    
    @Column(name = "min_stock")
    private Integer minStock = 0;
    
    @Column(name = "max_stock")
    private Integer maxStock = 0;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.ACTIVE;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum StatusEnum {
        ACTIVE, INACTIVE, DISCONTINUED
    }
}
```

### 5.4 WorkOrder实体类

```java
package com.4s.shop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "work_orders", indexes = {
    @Index(name = "idx_work_orders_customer", columnList = "customer_id"),
    @Index(name = "idx_work_orders_vehicle", columnList = "vehicle_id"),
    @Index(name = "idx_work_orders_status", columnList = "status"),
    @Index(name = "idx_work_orders_created_at", columnList = "created_at"),
    @Index(name = "idx_work_orders_assigned_tech", columnList = "assigned_technician_id")
})
@Data
@EqualsAndHashCode(callSuper = false)
public class WorkOrder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "order_number", unique = true, nullable = false, length = 30)
    private String orderNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;
    
    @Column(name = "order_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private OrderTypeEnum orderType;
    
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.PENDING;
    
    @Enumerated(EnumType.STRING)
    private PriorityEnum priority = PriorityEnum.NORMAL;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "estimated_hours", precision = 5, scale = 2)
    private BigDecimal estimatedHours;
    
    @Column(name = "actual_hours", precision = 5, scale = 2)
    private BigDecimal actualHours;
    
    @Column(name = "estimated_cost", precision = 10, scale = 2)
    private BigDecimal estimatedCost;
    
    @Column(name = "actual_cost", precision = 10, scale = 2)
    private BigDecimal actualCost;
    
    @Column(name = "start_time")
    private LocalDateTime startTime;
    
    @Column(name = "end_time")
    private LocalDateTime endTime;
    
    @Column(name = "scheduled_start_time")
    private LocalDateTime scheduledStartTime;
    
    @Column(name = "scheduled_end_time")
    private LocalDateTime scheduledEndTime;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_technician_id")
    private Employee assignedTechnician;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum OrderTypeEnum {
        REPAIR, MAINTENANCE, MODIFICATION
    }
    
    public enum StatusEnum {
        PENDING, IN_PROGRESS, COMPLETED, CANCELLED
    }
    
    public enum PriorityEnum {
        LOW, NORMAL, HIGH, URGENT
    }
}
```

### 5.5 Inventory实体类

```java
package com.4s.shop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory", indexes = {
    @Index(name = "idx_inventory_part_warehouse", columnList = "part_id, warehouse_id"),
    @Index(name = "idx_inventory_available", columnList = "available_quantity")
})
@Data
@EqualsAndHashCode(callSuper = false)
public class Inventory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id", nullable = false)
    private Part part;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;
    
    private Integer quantity = 0;
    
    @Column(name = "reserved_quantity")
    private Integer reservedQuantity = 0;
    
    @Column(name = "available_quantity", insertable = false, updatable = false)
    private Integer availableQuantity;
    
    @Column(length = 50)
    private String location;
    
    @Column(name = "batch_number", length = 50)
    private String batchNumber;
    
    @Column(name = "production_date")
    private LocalDate productionDate;
    
    @Column(name = "expiry_date")
    private LocalDate expiryDate;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
```

## 6. 数据库设计优化建议

### 6.1 性能优化
1. 使用分区表存储历史数据（如操作日志、工单记录）
2. 合理设置索引，避免过度索引影响写入性能
3. 使用连接池管理数据库连接
4. 对大字段使用延迟加载

### 6.2 安全性
1. 对敏感信息（如身份证号）进行加密存储
2. 实施细粒度的访问控制
3. 定期备份数据并测试恢复流程
4. 记录所有数据变更操作日志

### 6.3 扩展性
1. 预留扩展字段支持业务变化
2. 使用枚举类型确保数据一致性
3. 设计灵活的配置表支持业务规则调整
4. 考虑多租户架构支持连锁店扩展

### 6.4 数据完整性
1. 使用外键约束确保引用完整性
2. 使用检查约束确保业务规则
3. 实施软删除策略保护历史数据
4. 使用事务确保数据一致性
