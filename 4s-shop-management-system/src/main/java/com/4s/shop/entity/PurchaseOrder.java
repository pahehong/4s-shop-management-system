
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
@Table(name = "purchase_orders", indexes = {
    @Index(name = "idx_purchase_orders_supplier", columnList = "supplier_id"),
    @Index(name = "idx_purchase_orders_status", columnList = "status"),
    @Index(name = "idx_purchase_orders_created_at", columnList = "created_at")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class PurchaseOrder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "po_number", unique = true, nullable = false, length = 30)
    @NotBlank(message = "采购订单号不能为空")
    @Size(max = 30, message = "采购订单号长度不能超过30个字符")
    private String poNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    @NotNull(message = "供应商信息不能为空")
    private Supplier supplier;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    @NotNull(message = "采购员信息不能为空")
    private Employee employee;
    
    @Column(name = "total_amount", precision = 12, scale = 2)
    @DecimalMin(value = "0.0", message = "总金额不能小于0")
    private BigDecimal totalAmount;
    
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.PENDING;
    
    @Column(name = "expected_delivery_date")
    private LocalDate expectedDeliveryDate;
    
    @Column(name = "actual_delivery_date")
    private LocalDate actualDeliveryDate;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 关联关系
    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PurchaseOrderItem> purchaseOrderItems;
    
    public enum StatusEnum {
        PENDING, APPROVED, CANCELLED, COMPLETED
    }
}
