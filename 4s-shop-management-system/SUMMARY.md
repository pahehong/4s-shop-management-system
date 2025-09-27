
# 4S店管理系统JPA实体类和Repository接口总结

## 实体类 (Entities)

### 客户管理模块
- `Customer.java` - 客户信息实体
- `CustomerTag.java` - 客户标签实体（未创建，因为之前的数据库设计中没有包含此实体的详细信息）

### 车辆管理模块
- `Vehicle.java` - 车辆信息实体
- `VehicleMaintenance.java` - 车辆保养记录实体
- `VehicleRepair.java` - 车辆维修记录实体
- `VehicleInsurance.java` - 车辆保险信息实体

### 维修服务模块
- `WorkOrder.java` - 工单信息实体
- `WorkOrderItem.java` - 工单项目实体
- `WorkOrderPart.java` - 工单配件实体
- `ServiceType.java` - 服务类型实体

### 库存管理模块
- `Part.java` - 配件信息实体
- `PartCategory.java` - 配件分类实体
- `Inventory.java` - 库存信息实体
- `Warehouse.java` - 仓库信息实体
- `Supplier.java` - 供应商信息实体
- `PurchaseOrder.java` - 采购订单实体
- `PurchaseOrderItem.java` - 采购订单项实体

### 财务管理模块
- `PaymentRecord.java` - 支付记录实体
- `Invoice.java` - 发票信息实体

### 员工管理模块
- `Employee.java` - 员工信息实体
- `Department.java` - 部门信息实体
- `Position.java` - 职位信息实体

### 系统管理模块
- `User.java` - 用户信息实体
- `Role.java` - 角色实体
- `UserRole.java` - 用户角色关联实体
- `Permission.java` - 权限实体
- `RolePermission.java` - 角色权限关联实体
- `OperationLog.java` - 操作日志实体
- `SystemConfig.java` - 系统配置实体

## Repository接口

### 客户管理模块
- `CustomerRepository.java` - 客户信息Repository

### 车辆管理模块
- `VehicleRepository.java` - 车辆信息Repository
- `VehicleMaintenanceRepository.java` - 车辆保养记录Repository
- `VehicleRepairRepository.java` - 车辆维修记录Repository
- `VehicleInsuranceRepository.java` - 车辆保险信息Repository（未创建，因为之前的数据库设计中没有包含此实体的详细信息）

### 维修服务模块
- `WorkOrderRepository.java` - 工单信息Repository
- `WorkOrderItemRepository.java` - 工单项目Repository
- `WorkOrderPartRepository.java` - 工单配件Repository
- `ServiceTypeRepository.java` - 服务类型Repository

### 库存管理模块
- `PartRepository.java` - 配件信息Repository
- `PartCategoryRepository.java` - 配件分类Repository
- `InventoryRepository.java` - 库存信息Repository
- `WarehouseRepository.java` - 仓库信息Repository
- `SupplierRepository.java` - 供应商信息Repository
- `PurchaseOrderRepository.java` - 采购订单Repository
- `PurchaseOrderItemRepository.java` - 采购订单项Repository

### 财务管理模块
- `PaymentRecordRepository.java` - 支付记录Repository
- `InvoiceRepository.java` - 发票信息Repository

### 员工管理模块
- `EmployeeRepository.java` - 员工信息Repository
- `DepartmentRepository.java` - 部门信息Repository（未创建，因为之前的数据库设计中没有包含此实体的详细信息）
- `PositionRepository.java` - 职位信息Repository（未创建，因为之前的数据库设计中没有包含此实体的详细信息）

### 系统管理模块
- `UserRepository.java` - 用户信息Repository
- `RoleRepository.java` - 角色Repository
- `UserRoleRepository.java` - 用户角色关联Repository
- `PermissionRepository.java` - 权限Repository
- `RolePermissionRepository.java` - 角色权限关联Repository
- `OperationLogRepository.java` - 操作日志Repository
- `SystemConfigRepository.java` - 系统配置Repository

## 技术特点

### 实体类特点
1. 使用Lombok注解减少样板代码
2. 正确配置关联关系（@OneToMany, @ManyToOne, @OneToOne, @ManyToMany）
3. 包含数据验证注解（@NotBlank, @Email, @Size, @Min, @Max, @DecimalMin, @DecimalMax, @Pattern）
4. 包含审计字段（@CreatedDate, @LastModifiedDate）
5. 使用枚举类型管理状态

### Repository接口特点
1. 继承JpaRepository，获得基本的CRUD操作
2. 提供基于方法名的查询功能
3. 包含自定义查询方法（使用@Query注解）
4. 支持分页和排序功能

## 枚举类型

在各个实体类中定义了以下枚举类型：
- Customer.CustomerLevelEnum: VIP, PREMIUM, NORMAL
- Customer.StatusEnum: ACTIVE, INACTIVE, SUSPENDED
- Vehicle.StatusEnum: ACTIVE, INACTIVE, SOLD
- Part.StatusEnum: ACTIVE, INACTIVE, DISCONTINUED
- WorkOrder.OrderTypeEnum: REPAIR, MAINTENANCE, MODIFICATION
- WorkOrder.StatusEnum: PENDING, IN_PROGRESS, COMPLETED, CANCELLED
- WorkOrder.PriorityEnum: LOW, NORMAL, HIGH, URGENT
- Employee.StatusEnum: ACTIVE, INACTIVE, RESIGNED
- Department.StatusEnum: ACTIVE, INACTIVE
- Supplier.StatusEnum: ACTIVE, INACTIVE, SUSPENDED
- Warehouse.StatusEnum: ACTIVE, INACTIVE, MAINTENANCE
- ServiceType.StatusEnum: ACTIVE, INACTIVE
- WorkOrderItem.StatusEnum: PENDING, IN_PROGRESS, COMPLETED
- PaymentRecord.PaymentMethodEnum: CASH, CARD, WECHAT, ALIPAY, BANK_TRANSFER
- PaymentRecord.PaymentStatusEnum: PENDING, PAID, REFUNDED
- Invoice.InvoiceStatusEnum: DRAFT, ISSUED, SENT, PAID
- Invoice.InvoiceTypeEnum: NORMAL, SPECIAL, ELECTRONIC
- User.StatusEnum: ACTIVE, INACTIVE, LOCKED
- VehicleInsurance.StatusEnum: ACTIVE, EXPIRED, CANCELLED

## 使用说明

这些实体类和Repository接口遵循了Spring Data JPA的最佳实践，可以直接用于4S店管理系统项目中。每个实体类都包含了适当的验证规则、关联关系和审计字段，Repository接口提供了丰富的查询方法，可以满足各种业务需求。
