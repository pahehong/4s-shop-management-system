
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
@Table(name = "vehicle_repairs")
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class VehicleRepair {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    @NotNull(message = "车辆信息不能为空")
    private Vehicle vehicle;
    
    @Column(name = "repair_date", nullable = false)
    @NotNull(message = "维修日期不能为空")
    private LocalDate repairDate;
    
    @Column(name = "fault_description", columnDefinition = "TEXT")
    private String faultDescription;
    
    @Column(name = "repair_description", columnDefinition = "TEXT")
    private String repairDescription;
    
    @DecimalMin(value = "0.0", message = "费用不能小于0")
    private BigDecimal cost;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technician_id")
    private Employee technician;
    
    @Column(name = "warranty_period")
    @Min(value = 0, message = "保修期不能小于0")
    private Integer warrantyPeriod;
    
    @Column(name = "warranty_expiry_date")
    private LocalDate warrantyExpiryDate;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
