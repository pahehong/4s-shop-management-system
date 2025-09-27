
package com.4s.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "service_types")
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class ServiceType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 50)
    @NotBlank(message = "服务类型名称不能为空")
    @Size(max = 50, message = "服务类型名称长度不能超过50个字符")
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "base_hours", precision = 5, scale = 2)
    @DecimalMin(value = "0.0", message = "基础工时不能小于0")
    private BigDecimal baseHours;
    
    @Column(name = "base_price", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", message = "基础价格不能小于0")
    private BigDecimal basePrice;
    
    @Column(length = 50)
    @Size(max = 50, message = "分类长度不能超过50个字符")
    private String category;
    
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.ACTIVE;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 关联关系
    @OneToMany(mappedBy = "serviceType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkOrderItem> workOrderItems;
    
    public enum StatusEnum {
        ACTIVE, INACTIVE
    }
}
