
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
@Table(name = "purchase_order_items")
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class PurchaseOrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "po_id", nullable = false)
    @NotNull(message = "采购订单信息不能为空")
    private PurchaseOrder purchaseOrder;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id", nullable = false)
    @NotNull(message = "配件信息不能为空")
    private Part part;
    
    @Min(value = 1, message = "数量不能小于1")
    private Integer quantity;
    
    @Column(name = "unit_price", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", message = "单价不能小于0")
    private BigDecimal unitPrice;
    
    @Column(name = "total_price", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", message = "总价不能小于0")
    private BigDecimal totalPrice;
    
    @Column(name = "received_quantity")
    @Min(value = 0, message = "已收货数量不能小于0")
    private Integer receivedQuantity = 0;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
