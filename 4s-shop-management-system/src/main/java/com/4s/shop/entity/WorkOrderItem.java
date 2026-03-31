
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
@Table(name = "work_order_items")
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class WorkOrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id", nullable = false)
    @NotNull(message = "工单信息不能为空")
    private WorkOrder workOrder;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_type_id", nullable = false)
    @NotNull(message = "服务类型不能为空")
    private ServiceType serviceType;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "hours", precision = 5, scale = 2)
    @DecimalMin(value = "0.0", message = "工时不能小于0")
    private BigDecimal hours;
    
    @Column(name = "unit_price", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", message = "单价不能小于0")
    private BigDecimal unitPrice;
    
    @Column(name = "total_price", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", message = "总价不能小于0")
    private BigDecimal totalPrice;
    
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.PENDING;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    public enum StatusEnum {
        PENDING, IN_PROGRESS, COMPLETED
    }
}
