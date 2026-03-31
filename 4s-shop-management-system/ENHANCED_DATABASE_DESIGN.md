
# 4S店管理系统增强功能数据库设计文档

## 1. 新增核心实体列表

### 1.1 AI服务实体
- **AiModelResult** (AI模型结果)
- **AiModel** (AI模型信息)

### 1.2 分析服务实体
- **AnalyticsReport** (分析报告)
- **BusinessMetric** (业务指标)

### 1.3 IoT服务实体
- **IotVehicleData** (IoT车辆数据)
- **IotDevice** (IoT设备信息)

## 2. 完整SQL建表语句

```sql
-- AI模型表
CREATE TABLE ai_models (
    id BIGSERIAL PRIMARY KEY,
    model_name VARCHAR(100) NOT NULL,
    model_type VARCHAR(50) NOT NULL, -- 模型类型: FAULT_DIAGNOSIS, CUSTOMER_CHURN, DEMAND_FORECASTING
    description TEXT,
    version VARCHAR(20),
    status VARCHAR(20) DEFAULT 'ACTIVE', -- 激活/停用
    is_default BOOLEAN DEFAULT FALSE, -- 是否为默认模型
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- AI模型结果表
CREATE TABLE ai_model_results (
    id BIGSERIAL PRIMARY KEY,
    model_id BIGINT REFERENCES ai_models(id),
    model_type VARCHAR(50) NOT NULL, -- 模型类型: FAULT_DIAGNOSIS, CUSTOMER_CHURN, DEMAND_FORECASTING
    entity_id BIGINT, -- 关联实体ID
    entity_type VARCHAR(50), -- 关联实体类型
    prediction_result JSONB, -- 预测结果
    confidence_score DECIMAL(5,4), -- 置信度
    explanation TEXT, -- 结果解释
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- IoT设备表
CREATE TABLE iot_devices (
    id BIGSERIAL PRIMARY KEY,
    device_id VARCHAR(100) UNIQUE NOT NULL, -- 设备唯一标识
    device_name VARCHAR(100),
    device_type VARCHAR(50) NOT NULL, -- 设备类型: OBD, GPS, SENSOR
    vehicle_id BIGINT REFERENCES vehicles(id), -- 关联车辆
    customer_id BIGINT REFERENCES customers(id), -- 关联客户
    status VARCHAR(20) DEFAULT 'ACTIVE', -- 激活/停用/故障
    last_connected_at TIMESTAMP,
    firmware_version VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- IoT车辆数据表
CREATE TABLE iot_vehicle_data (
    id BIGSERIAL PRIMARY KEY,
    device_id BIGINT REFERENCES iot_devices(id),
    vehicle_id BIGINT REFERENCES vehicles(id),
    data_type VARCHAR(50) NOT NULL, -- 数据类型: OBD, GPS, SENSOR
    data_payload JSONB NOT NULL, -- 数据载荷
    timestamp TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 业务指标表
CREATE TABLE business_metrics (
    id BIGSERIAL PRIMARY KEY,
    metric_name VARCHAR(100) NOT NULL,
    metric_type VARCHAR(50) NOT NULL, -- 指标类型: SALES, SERVICE, CUSTOMER, FINANCIAL
    metric_value DECIMAL(15,2) NOT NULL,
    unit VARCHAR(20), -- 单位
    period_start DATE NOT NULL,
    period_end DATE NOT NULL,
    calculated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 分析报告表
CREATE TABLE analytics_reports (
    id BIGSERIAL PRIMARY KEY,
    report_type VARCHAR(50) NOT NULL, -- 报告类型: BUSINESS_DASHBOARD, EMPLOYEE_PERFORMANCE, CUSTOMER_VALUE
    report_name VARCHAR(100) NOT NULL,
    report_data JSONB NOT NULL, -- 报告数据
    generated_by BIGINT REFERENCES users(id),
    generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    period_start DATE,
    period_end DATE,
    status VARCHAR(20) DEFAULT 'GENERATED' -- 生成/处理中/失败
);
```

## 3. 实体关系图 (ERD)

```
                    +-------------------+
                    |    ai_models      |
                    +-------------------+
                    | id (PK)           |
                    | model_name        |
                    | model_type        |
                    | ...               |
                    +-------------------+
                            |
                            | (1:M)
                            |
                    +-------------------+
                    | ai_model_results  |
                    +-------------------+
                    | id (PK)           |
                    | model_id (FK)     |
                    | model_type        |
                    | entity_id         |
                    | entity_type       |
                    | prediction_result |
                    | confidence_score  |
                    +-------------------+

+-------------------+       +-------------------+
|    vehicles       |       |   iot_devices     |
+-------------------+       +-------------------+
| id (PK)           |<------| vehicle_id (FK)   |
| ...               |       | id (PK)           |
+-------------------+       | device_id         |
                            | device_type       |
+-------------------+       | status            |
|    customers      |       | ...               |
+-------------------+       +-------------------+
| id (PK)           |               |
| ...               |               | (1:M)
+-------------------+               |
        |                           |
        | (M:1)                     |
        |                           |
+-------+-------+           +-------------------+
| iot_vehicle_data|---------| ai_model_results  |
+---------------+           +-------------------+
| id (PK)       |           | id (PK)           |
| device_id(FK) |           | entity_id         |
| vehicle_id(FK)|           | entity_type       |
| data_type     |           | ...               |
| data_payload  |           +-------------------+
| timestamp     |
+---------------+

+-------------------+
|  business_metrics |
+-------------------+
| id (PK)           |
| metric_name       |
| metric_type       |
| metric_value      |
| ...               |
+-------------------+
        |
        | (M:1)
        |
+-------------------+
| analytics_reports |
+-------------------+
| id (PK)           |
| report_type       |
| report_data       |
| generated_by (FK) |
| ...               |
+-------------------+
```

## 4. 索引优化建议

### 4.1 主要查询优化索引

```sql
-- AI模型结果表索引
CREATE INDEX idx_ai_model_results_type_entity ON ai_model_results(model_type, entity_type, entity_id);
CREATE INDEX idx_ai_model_results_model ON ai_model_results(model_id);
CREATE INDEX idx_ai_model_results_created_at ON ai_model_results(created_at);

-- IoT车辆数据表索引
CREATE INDEX idx_iot_vehicle_data_vehicle_time ON iot_vehicle_data(vehicle_id, timestamp);
CREATE INDEX idx_iot_vehicle_data_timestamp ON iot_vehicle_data(timestamp);
CREATE INDEX idx_iot_vehicle_data_device ON iot_vehicle_data(device_id);
CREATE INDEX idx_iot_vehicle_data_type ON iot_vehicle_data(data_type);

-- IoT设备表索引
CREATE INDEX idx_iot_devices_vehicle ON iot_devices(vehicle_id);
CREATE INDEX idx_iot_devices_customer ON iot_devices(customer_id);
CREATE INDEX idx_iot_devices_status ON iot_devices(status);

-- 分析报告表索引
CREATE INDEX idx_analytics_reports_type ON analytics_reports(report_type);
CREATE INDEX idx_analytics_reports_generated_at ON analytics_reports(generated_at);
CREATE INDEX idx_analytics_reports_period ON analytics_reports(period_start, period_end);

-- 业务指标表索引
CREATE INDEX idx_business_metrics_type_period ON business_metrics(metric_type, period_start, period_end);
CREATE INDEX idx_business_metrics_calculated_at ON business_metrics(calculated_at);
```

### 4.2 复合查询优化索引

```sql
-- AI模型结果按类型和时间范围查询
CREATE INDEX idx_ai_model_results_type_time ON ai_model_results(model_type, created_at);

-- IoT数据按车辆和时间范围查询
CREATE INDEX idx_iot_vehicle_data_vehicle_type_time ON iot_vehicle_data(vehicle_id, data_type, timestamp DESC);

-- 分析报告按类型和生成时间查询
CREATE INDEX idx_analytics_reports_type_time ON analytics_reports(report_type, generated_at DESC);
```

## 5. JPA实体类代码框架

### 5.1 AiModel实体类

```java
package com.4s.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_models", indexes = {
    @Index(name = "idx_ai_models_type", columnList = "model_type"),
    @Index(name = "idx_ai_models_status", columnList = "status")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class AiModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "model_name", nullable = false, length = 100)
    @NotBlank(message = "模型名称不能为空")
    @Size(max = 100, message = "模型名称长度不能超过100个字符")
    private String modelName;
    
    @Column(name = "model_type", nullable = false, length = 50)
    @NotBlank(message = "模型类型不能为空")
    @Size(max = 50, message = "模型类型长度不能超过50个字符")
    @Enumerated(EnumType.STRING)
    private ModelTypeEnum modelType;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(length = 20)
    @Size(max = 20, message = "版本号长度不能超过20个字符")
    private String version;
    
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.ACTIVE;
    
    @Column(name = "is_default")
    private Boolean isDefault = false;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 关联关系
    @OneToMany(mappedBy = "aiModel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<AiModelResult> modelResults;
    
    public enum ModelTypeEnum {
        FAULT_DIAGNOSIS, CUSTOMER_CHURN, DEMAND_FORECASTING, PREDICTIVE_MAINTENANCE
    }
    
    public enum StatusEnum {
        ACTIVE, INACTIVE, MAINTENANCE
    }
}
```

### 5.2 AiModelResult实体类

```java
package com.4s.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ai_model_results", indexes = {
    @Index(name = "idx_ai_model_results_type_entity", columnList = "model_type, entity_type, entity_id"),
    @Index(name = "idx_ai_model_results_model", columnList = "model_id"),
    @Index(name = "idx_ai_model_results_created_at", columnList = "created_at"),
    @Index(name = "idx_ai_model_results_type_time", columnList = "model_type, created_at")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class AiModelResult {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private AiModel aiModel;
    
    @Column(name = "model_type", nullable = false, length = 50)
    @NotBlank(message = "模型类型不能为空")
    @Size(max = 50, message = "模型类型长度不能超过50个字符")
    @Enumerated(EnumType.STRING)
    private AiModel.ModelTypeEnum modelType;
    
    @Column(name = "entity_id")
    private Long entityId;
    
    @Column(name = "entity_type", length = 50)
    @Size(max = 50, message = "实体类型长度不能超过50个字符")
    private String entityType;
    
    @Column(name = "prediction_result", columnDefinition = "jsonb")
    private String predictionResult;
    
    @Column(name = "confidence_score", precision = 5, scale = 4)
    @DecimalMin(value = "0.0", message = "置信度不能小于0")
    @DecimalMax(value = "1.0", message = "置信度不能大于1")
    private BigDecimal confidenceScore;
    
    @Column(columnDefinition = "TEXT")
    private String explanation;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
```

### 5.3 IotDevice实体类

```java
package com.4s.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "iot_devices", indexes = {
    @Index(name = "idx_iot_devices_vehicle", columnList = "vehicle_id"),
    @Index(name = "idx_iot_devices_customer", columnList = "customer_id"),
    @Index(name = "idx_iot_devices_status", columnList = "status")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class IotDevice {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "device_id", unique = true, nullable = false, length = 100)
    @NotBlank(message = "设备ID不能为空")
    @Size(max = 100, message = "设备ID长度不能超过100个字符")
    private String deviceId;
    
    @Column(name = "device_name", length = 100)
    @Size(max = 100, message = "设备名称长度不能超过100个字符")
    private String deviceName;
    
    @Column(name = "device_type", nullable = false, length = 50)
    @NotBlank(message = "设备类型不能为空")
    @Size(max = 50, message = "设备类型长度不能超过50个字符")
    @Enumerated(EnumType.STRING)
    private DeviceTypeEnum deviceType;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.ACTIVE;
    
    @Column(name = "last_connected_at")
    private LocalDateTime lastConnectedAt;
    
    @Column(name = "firmware_version", length = 20)
    @Size(max = 20, message = "固件版本长度不能超过20个字符")
    private String firmwareVersion;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 关联关系
    @OneToMany(mappedBy = "iotDevice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<IotVehicleData> iotVehicleDataList;
    
    public enum DeviceTypeEnum {
        OBD, GPS, SENSOR, CAMERA
    }
    
    public enum StatusEnum {
        ACTIVE, INACTIVE, FAULTY
    }
}
```

### 5.4 IotVehicleData实体类

```java
package com.4s.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "iot_vehicle_data", indexes = {
    @Index(name = "idx_iot_vehicle_data_vehicle_time", columnList = "vehicle_id, timestamp"),
    @Index(name = "idx_iot_vehicle_data_timestamp", columnList = "timestamp"),
    @Index(name = "idx_iot_vehicle_data_device", columnList = "device_id"),
    @Index(name = "idx_iot_vehicle_data_type", columnList = "data_type"),
    @Index(name = "idx_iot_vehicle_data_vehicle_type_time", columnList = "vehicle_id, data_type, timestamp DESC")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class IotVehicleData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    @NotNull(message = "设备信息不能为空")
    private IotDevice iotDevice;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    @NotNull(message = "车辆信息不能为空")
    private Vehicle vehicle;
    
    @Column(name = "data_type", nullable = false, length = 50)
    @NotBlank(message = "数据类型不能为空")
    @Size(max = 50, message = "数据类型长度不能超过50个字符")
    @Enumerated(EnumType.STRING)
    private DataTypeEnum dataType;
    
    @Column(name = "data_payload", nullable = false, columnDefinition = "jsonb")
    @NotBlank(message = "数据载荷不能为空")
    private String dataPayload;
    
    @Column(nullable = false)
    @NotNull(message = "时间戳不能为空")
    private LocalDateTime timestamp;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
```

### 5.5 BusinessMetric实体类

```java
package com.4s.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "business_metrics", indexes = {
    @Index(name = "idx_business_metrics_type_period", columnList = "metric_type, period_start, period_end"),
    @Index(name = "idx_business_metrics_calculated_at", columnList = "calculated_at")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessMetric {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "metric_name", nullable = false, length = 100)
    @NotBlank(message = "指标名称不能为空")
    @Size(max = 100, message = "指标名称长度不能超过100个字符")
    private String metricName;
    
    @Column(name = "metric_type", nullable = false, length = 50)
    @NotBlank(message = "指标类型不能为空")
    @Size(max = 50, message = "指标类型长度不能超过50个字符")
    @Enumerated(EnumType.STRING)
    private MetricTypeEnum metricType;
    
    @Column(name = "metric_value", precision = 15, scale = 2, nullable = false)
    @NotNull(message = "指标值不能为空")
    @DecimalMin(value = "0.0", message = "指标值不能小于0")
    private BigDecimal metricValue;
    
    @Column(length = 20)
    @Size(max = 20, message = "单位长度不能超过20个字符")
    private String unit;
    
    @Column(name = "period_start", nullable = false)
    @NotNull(message = "周期开始日期不能为空")
    private LocalDate periodStart;
    
    @Column(name = "period_end", nullable = false)
    @NotNull(message = "周期结束日期不能为空")
    private LocalDate periodEnd;
    
    @CreatedDate
    @Column(name = "calculated_at", nullable = false, updatable = false)
    private LocalDateTime calculatedAt;
    
    public enum MetricTypeEnum {
        SALES, SERVICE, CUSTOMER, FINANCIAL, INVENTORY, PERFORMANCE
    }
}
```

### 5.6 AnalyticsReport实体类

```java
package com.4s.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "analytics_reports", indexes = {
    @Index(name = "idx_analytics_reports_type", columnList = "report_type"),
    @Index(name = "idx_analytics_reports_generated_at", columnList = "generated_at"),
    @Index(name = "idx_analytics_reports_period", columnList = "period_start, period_end"),
    @Index(name = "idx_analytics_reports_type_time", columnList = "report_type, generated_at DESC")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class AnalyticsReport {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "report_type", nullable = false, length = 50)
    @NotBlank(message = "报告类型不能为空")
    @Size(max = 50, message = "报告类型长度不能超过50个字符")
    @Enumerated(EnumType.STRING)
    private ReportTypeEnum reportType;
    
    @Column(name = "report_name", nullable = false, length = 100)
    @NotBlank(message = "报告名称不能为空")
    @Size(max = 100, message = "报告名称长度不能超过100个字符")
    private String reportName;
    
    @Column(name = "report_data", nullable = false, columnDefinition = "jsonb")
    @NotBlank(message = "报告数据不能为空")
    private String reportData;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generated_by")
    private User generatedBy;
    
    @CreatedDate
    @Column(name = "generated_at", nullable = false, updatable = false)
    private LocalDateTime generatedAt;
    
    @Column(name = "period_start")
    private LocalDate periodStart;
    
    @Column(name = "period_end")
    private LocalDate periodEnd;
    
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.GENERATED;
    
    public enum ReportTypeEnum {
        BUSINESS_DASHBOARD, EMPLOYEE_PERFORMANCE, CUSTOMER_VALUE, 
        INVENTORY_ANALYSIS, FINANCIAL_REPORT, SALES_ANALYSIS
    }
    
    public enum StatusEnum {
        GENERATED, PROCESSING, FAILED
    }
}
```

## 6. Repository接口

### 6.1 AiModelRepository

```java
package com.4s.shop.repository;

import com.4s.shop.entity.AiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AiModelRepository extends JpaRepository<AiModel, Long> {
    
    Optional<AiModel> findByModelName(String modelName);
    
    List<AiModel> findByModelType(AiModel.ModelTypeEnum modelType);
    
    List<AiModel> findByStatus(AiModel.StatusEnum status);
    
    @Query("SELECT am FROM AiModel am WHERE am.modelType = :modelType AND am.status = :status")
    List<AiModel> findByModelTypeAndStatus(@Param("modelType") AiModel.ModelTypeEnum modelType, 
                                          @Param("status") AiModel.StatusEnum status);
    
    @Query("SELECT am FROM AiModel am WHERE am.isDefault = true AND am.modelType = :modelType")
    Optional<AiModel> findDefaultByModelType(@Param("modelType") AiModel.ModelTypeEnum modelType);
    
    @Query("SELECT COUNT(am) > 0 FROM AiModel am WHERE am.modelName = :modelName AND am.id != :id")
    boolean existsByModelNameAndIdNot(@Param("modelName") String modelName, @Param("id") Long id);
}
```

### 6.2 AiModelResultRepository

```java
package com.4s.shop.repository;

import com.4s.shop.entity.AiModelResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AiModelResultRepository extends JpaRepository<AiModelResult, Long> {
    
    List<AiModelResult> findByModelTypeAndEntityId(String modelType, Long entityId);
    
    List<AiModelResult> findByModelId(Long modelId);
    
    @Query("SELECT amr FROM AiModelResult amr WHERE amr.modelType = :modelType AND amr.entityType = :entityType AND amr.entityId = :entityId ORDER BY amr.createdAt DESC")
    List<AiModelResult> findByModelTypeAndEntityTypeAndEntityIdOrderByCreatedAtDesc(
            @Param("modelType") String modelType,
            @Param("entityType") String entityType,
            @Param("entityId") Long entityId);
    
    @Query("SELECT amr FROM AiModelResult amr WHERE amr.modelType = :modelType AND amr.createdAt BETWEEN :startDate AND :endDate")
    List<AiModelResult> findByModelTypeAndCreatedAtBetween(
            @Param("modelType") String modelType,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT amr FROM AiModelResult amr WHERE amr.entityId = :entityId AND amr.entityType = :entityType ORDER BY amr.createdAt DESC")
    List<AiModelResult> findByEntityIdAndEntityTypeOrderByCreatedAtDesc(
            @Param("entityId") Long entityId,
            @Param("entityType") String entityType);
}
```

### 6.3 IotDeviceRepository

```java
package com.4s.shop.repository;

import com.4s.shop.entity.IotDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IotDeviceRepository extends JpaRepository<IotDevice, Long> {
    
    Optional<IotDevice> findByDeviceId(String deviceId);
    
    List<IotDevice> findByVehicleId(Long vehicleId);
    
    List<IotDevice> findByCustomerId(Long customerId);
    
    List<IotDevice> findByStatus(IotDevice.StatusEnum status);
    
    List<IotDevice> findByDeviceType(IotDevice.DeviceTypeEnum deviceType);
    
    @Query("SELECT id FROM IotDevice id WHERE id.vehicle.id = :vehicleId AND id.status = :status")
    List<IotDevice> findByVehicleIdAndStatus(@Param("vehicleId") Long vehicleId, 
                                            @Param("status") IotDevice.StatusEnum status);
    
    @Query("SELECT COUNT(id) > 0 FROM IotDevice id WHERE id.deviceId = :deviceId AND id.id != :id")
    boolean existsByDeviceIdAndIdNot(@Param("deviceId") String deviceId, @Param("id") Long id);
    
    @Query("SELECT id FROM IotDevice id WHERE id.lastConnectedAt > :lastConnectedAfter")
    List<IotDevice> findActiveDevices(@Param("lastConnectedAfter") java.time.LocalDateTime lastConnectedAfter);
}
```

### 6.4 IotVehicleDataRepository

```java
package com.4s.shop.repository;

import com.4s.shop.entity.IotVehicleData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IotVehicleDataRepository extends JpaRepository<IotVehicleData, Long> {
    
    List<IotVehicleData> findByVehicleId(Long vehicleId);
    
    List<IotVehicleData> findByDeviceId(Long deviceId);
    
    List<IotVehicleData> findByDataType(IotVehicleData.DataTypeEnum dataType);
    
    @Query("SELECT ivd FROM IotVehicleData ivd WHERE ivd.vehicle.id = :vehicleId AND ivd.timestamp BETWEEN :startTime AND :endTime ORDER BY ivd.timestamp DESC")
    List<IotVehicleData> findByVehicleIdAndTimestampBetweenOrderByTimestampDesc(
            @Param("vehicleId") Long vehicleId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    @Query("SELECT ivd FROM IotVehicleData ivd WHERE ivd.vehicle.id = :vehicleId AND ivd.dataType = :dataType AND ivd.timestamp > :since ORDER BY ivd.timestamp DESC")
    List<IotVehicleData> findByVehicleIdAndDataTypeAndTimestampAfter(
            @Param("vehicleId") Long vehicleId,
            @Param("dataType") IotVehicleData.DataTypeEnum dataType,
            @Param("since") LocalDateTime since);
    
    @Query("SELECT ivd FROM IotVehicleData ivd WHERE ivd.vehicle.id = :vehicleId ORDER BY ivd.timestamp DESC")
    Page<IotVehicleData> findByVehicleIdOrderByTimestampDesc(@Param("vehicleId") Long vehicleId, Pageable pageable);
    
    @Query("SELECT ivd FROM IotVehicleData ivd WHERE ivd.timestamp BETWEEN :startTime AND :endTime")
    List<IotVehicleData> findByTimestampBetween(@Param("startTime") LocalDateTime startTime, 
                                               @Param("endTime") LocalDateTime endTime);
}
```

### 6.5 BusinessMetricRepository

```java
package com.4s.shop.repository;

import com.4s.shop.entity.BusinessMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BusinessMetricRepository extends JpaRepository<BusinessMetric, Long> {
    
    List<BusinessMetric> findByMetricType(BusinessMetric.MetricTypeEnum metricType);
    
    List<BusinessMetric> findByPeriodStartBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT bm FROM BusinessMetric bm WHERE bm.metricType = :metricType AND bm.periodStart >= :periodStart AND bm.periodEnd <= :periodEnd ORDER BY bm.periodStart")
    List<BusinessMetric> findByMetricTypeAndPeriodBetween(
            @Param("metricType") BusinessMetric.MetricTypeEnum metricType,
            @Param("periodStart") LocalDate periodStart,
            @Param("periodEnd") LocalDate periodEnd);
    
    @Query("SELECT AVG(bm.metricValue) FROM BusinessMetric bm WHERE bm.metricType = :metricType AND bm.periodStart >= :periodStart AND bm.periodEnd <= :periodEnd")
    Double findAverageByMetricTypeAndPeriodBetween(
            @Param("metricType") BusinessMetric.MetricTypeEnum metricType,
            @Param("periodStart") LocalDate periodStart,
            @Param("periodEnd") LocalDate periodEnd);
    
    @Query("SELECT bm FROM BusinessMetric bm WHERE bm.metricName LIKE %:metricName%")
    List<BusinessMetric> findByMetricNameContaining(@Param("metricName") String metricName);
}
```

### 6.6 AnalyticsReportRepository

```java
package com.4s.shop.repository;

import com.4s.shop.entity.AnalyticsReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnalyticsReportRepository extends JpaRepository<AnalyticsReport, Long> {
    
    List<AnalyticsReport> findByReportType(AnalyticsReport.ReportTypeEnum reportType);
    
    List<AnalyticsReport> findByStatus(AnalyticsReport.StatusEnum status);
    
    @Query("SELECT ar FROM AnalyticsReport ar WHERE ar.reportType = :reportType AND ar.status = :status")
    List<AnalyticsReport> findByReportTypeAndStatus(
            @Param("reportType") AnalyticsReport.ReportTypeEnum reportType,
            @Param("status") AnalyticsReport.StatusEnum status);
    
    @Query("SELECT ar FROM AnalyticsReport ar WHERE ar.generatedBy.id = :userId AND ar.generatedAt BETWEEN :startDate AND :endDate ORDER BY ar.generatedAt DESC")
    List<AnalyticsReport> findByGeneratedByAndGeneratedAtBetweenOrderByGeneratedAtDesc(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT ar FROM AnalyticsReport ar WHERE ar.periodStart >= :periodStart AND ar.periodEnd <= :periodEnd")
    List<AnalyticsReport> findByPeriodBetween(@Param("periodStart") LocalDate periodStart, 
                                             @Param("periodEnd") LocalDate periodEnd);
    
    @Query("SELECT ar FROM AnalyticsReport ar WHERE ar.reportType = :reportType ORDER BY ar.generatedAt DESC")
    Page<AnalyticsReport> findByReportTypeOrderByGeneratedAtDesc(
            @Param("reportType") AnalyticsReport.ReportTypeEnum reportType, 
            Pageable pageable);
    
    @Query("SELECT ar FROM AnalyticsReport ar WHERE ar.reportName LIKE %:keyword% OR ar.reportType = :reportType")
    Page<AnalyticsReport> findByKeywordContainingOrReportType(
            @Param("keyword") String keyword,
            @Param("reportType") AnalyticsReport.ReportTypeEnum reportType, 
            Pageable pageable);
}
```

## 7. 数据库设计优化建议

### 7.1 性能优化
1. 对于时间序列数据（如IoT数据），考虑使用分区表按时间分区
2. 对于JSONB字段，创建GIN索引以提高查询性能
3. 定期清理历史数据，特别是IoT车辆数据
4. 使用连接池管理数据库连接

### 7.2 安全性
1. 对敏感数据进行加密存储
2. 实施细粒度的访问控制
3. 记录所有数据访问操作日志
4. 定期备份数据并测试恢复流程

### 7.3 扩展性
1. 预留扩展字段支持业务变化
2. 使用枚举类型确保数据一致性
3. 设计灵活的配置表支持业务规则调整
4. 考虑数据归档策略

### 7.4 数据完整性
1. 使用外键约束确保引用完整性
2. 使用检查约束确保业务规则
3. 实施软删除策略保护历史数据
4. 使用事务确保数据一致性
```
