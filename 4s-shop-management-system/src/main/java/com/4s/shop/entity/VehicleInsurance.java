
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

@Entity
@Table(name = "vehicle_insurances", indexes = {
    @Index(name = "idx_vehicle_insurances_policy", columnList = "policy_number")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class VehicleInsurance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    @NotNull(message = "车辆信息不能为空")
    private Vehicle vehicle;
    
    @Column(name = "insurance_company", length = 100)
    @Size(max = 100, message = "保险公司名称长度不能超过100个字符")
    private String insuranceCompany;
    
    @Column(name = "policy_number", unique = true, length = 50)
    @Size(max = 50, message = "保单号长度不能超过50个字符")
    private String policyNumber;
    
    @Column(name = "insurance_type", length = 50)
    @Size(max = 50, message = "保险类型长度不能超过50个字符")
    private String insuranceType;
    
    @Column(name = "start_date")
    private LocalDate startDate;
    
    @Column(name = "end_date")
    private LocalDate endDate;
    
    @Column(name = "premium", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", message = "保费不能小于0")
    private BigDecimal premium;
    
    @Column(name = "coverage_amount", precision = 12, scale = 2)
    @DecimalMin(value = "0.0", message = "保额不能小于0")
    private BigDecimal coverageAmount;
    
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.ACTIVE;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum StatusEnum {
        ACTIVE, EXPIRED, CANCELLED
    }
}
