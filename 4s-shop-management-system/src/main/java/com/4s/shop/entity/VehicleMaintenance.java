
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
@Table(name = "vehicle_maintenances")
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class VehicleMaintenance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    @NotNull(message = "车辆信息不能为空")
    private Vehicle vehicle;
    
    @Column(name = "maintenance_type", nullable = false, length = 50)
    @NotBlank(message = "保养类型不能为空")
    @Size(max = 50, message = "保养类型长度不能超过50个字符")
    private String maintenanceType;
    
    @Column(name = "maintenance_date", nullable = false)
    @NotNull(message = "保养日期不能为空")
    private LocalDate maintenanceDate;
    
    @Column(name = "mileage_at_service", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", message = "保养时里程不能小于0")
    private BigDecimal mileageAtService;
    
    @Column(name = "next_maintenance_mileage", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", message = "下次保养里程不能小于0")
    private BigDecimal nextMaintenanceMileage;
    
    @Column(name = "next_maintenance_date")
    private LocalDate nextMaintenanceDate;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @DecimalMin(value = "0.0", message = "费用不能小于0")
    private BigDecimal cost;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_advisor_id")
    private Employee serviceAdvisor;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
