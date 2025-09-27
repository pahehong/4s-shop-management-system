
package com.4s.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory", indexes = {
    @Index(name = "idx_inventory_part_warehouse", columnList = "part_id, warehouse_id"),
    @Index(name = "idx_inventory_available", columnList = "available_quantity")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class Inventory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id", nullable = false)
    @NotNull(message = "配件信息不能为空")
    private Part part;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    @NotNull(message = "仓库信息不能为空")
    private Warehouse warehouse;
    
    @Min(value = 0, message = "库存数量不能小于0")
    private Integer quantity = 0;
    
    @Column(name = "reserved_quantity")
    @Min(value = 0, message = "预留数量不能小于0")
    private Integer reservedQuantity = 0;
    
    @Column(name = "available_quantity", insertable = false, updatable = false)
    private Integer availableQuantity;
    
    @Column(length = 50)
    @Size(max = 50, message = "位置长度不能超过50个字符")
    private String location;
    
    @Column(name = "batch_number", length = 50)
    @Size(max = 50, message = "批次号长度不能超过50个字符")
    private String batchNumber;
    
    @Column(name = "production_date")
    private LocalDate productionDate;
    
    @Column(name = "expiry_date")
    private LocalDate expiryDate;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
