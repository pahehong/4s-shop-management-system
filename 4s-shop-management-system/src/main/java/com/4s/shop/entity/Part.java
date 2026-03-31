
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
@Table(name = "parts", indexes = {
    @Index(name = "idx_parts_code", columnList = "part_code"),
    @Index(name = "idx_parts_category", columnList = "category_id"),
    @Index(name = "idx_parts_supplier", columnList = "supplier_id")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class Part {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "part_code", unique = true, nullable = false, length = 30)
    @NotBlank(message = "配件编码不能为空")
    @Size(max = 30, message = "配件编码长度不能超过30个字符")
    private String partCode;
    
    @Column(nullable = false, length = 100)
    @NotBlank(message = "配件名称不能为空")
    @Size(max = 100, message = "配件名称长度不能超过100个字符")
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String specification;
    
    @Column(name = "oe_number", length = 50)
    @Size(max = 50, message = "OE号长度不能超过50个字符")
    private String oeNumber;
    
    @Column(length = 50)
    @Size(max = 50, message = "品牌长度不能超过50个字符")
    private String brand;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private PartCategory category;
    
    @Column(length = 20)
    @Size(max = 20, message = "单位长度不能超过20个字符")
    private String unit;
    
    @Column(name = "unit_price", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", message = "单价不能小于0")
    private BigDecimal unitPrice;
    
    @Column(name = "cost_price", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", message = "成本价不能小于0")
    private BigDecimal costPrice;
    
    @Column(name = "min_stock")
    @Min(value = 0, message = "最小库存不能小于0")
    private Integer minStock = 0;
    
    @Column(name = "max_stock")
    @Min(value = 0, message = "最大库存不能小于0")
    private Integer maxStock = 0;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.ACTIVE;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 关联关系
    @OneToMany(mappedBy = "part", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Inventory> inventories;
    
    @OneToMany(mappedBy = "part", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkOrderPart> workOrderParts;
    
    @OneToMany(mappedBy = "part", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PurchaseOrderItem> purchaseOrderItems;
    
    public enum StatusEnum {
        ACTIVE, INACTIVE, DISCONTINUED
    }
}
