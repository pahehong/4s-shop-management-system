
package com.4s.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vehicles", indexes = {
    @Index(name = "idx_vehicles_vin", columnList = "vin"),
    @Index(name = "idx_vehicles_customer_id", columnList = "customer_id"),
    @Index(name = "idx_vehicles_license_plate", columnList = "license_plate")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class Vehicle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull(message = "客户信息不能为空")
    private Customer customer;
    
    @Column(name = "vin", unique = true, nullable = false, length = 17)
    @NotBlank(message = "车架号不能为空")
    @Size(max = 17, message = "车架号长度不能超过17个字符")
    private String vin;
    
    @Column(nullable = false, length = 50)
    @NotBlank(message = "品牌不能为空")
    @Size(max = 50, message = "品牌长度不能超过50个字符")
    private String brand;
    
    @Column(nullable = false, length = 100)
    @NotBlank(message = "车型不能为空")
    @Size(max = 100, message = "车型长度不能超过100个字符")
    private String model;
    
    @Column(name = "license_plate", length = 20)
    @Size(max = 20, message = "车牌号长度不能超过20个字符")
    private String licensePlate;
    
    @Column(name = "purchase_date")
    private LocalDate purchaseDate;
    
    @Column(name = "mileage", precision = 10, scale = 2)
    private BigDecimal mileage;
    
    @Column(name = "engine_number", length = 50)
    @Size(max = 50, message = "发动机号长度不能超过50个字符")
    private String engineNumber;
    
    @Column(length = 20)
    @Size(max = 20, message = "颜色长度不能超过20个字符")
    private String color;
    
    @Column(name = "production_date")
    private LocalDate productionDate;
    
    @Column(name = "fuel_type", length = 20)
    @Size(max = 20, message = "燃油类型长度不能超过20个字符")
    private String fuelType;
    
    @Column(name = "transmission_type", length = 20)
    @Size(max = 20, message = "变速箱类型长度不能超过20个字符")
    private String transmissionType;
    
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.ACTIVE;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 关联关系
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkOrder> workOrders;
    
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VehicleMaintenance> vehicleMaintenances;
    
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VehicleRepair> vehicleRepairs;
    
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VehicleInsurance> vehicleInsurances;
    
    public enum StatusEnum {
        ACTIVE, INACTIVE, SOLD
    }
}
