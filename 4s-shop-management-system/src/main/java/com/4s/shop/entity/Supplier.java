
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
@Table(name = "suppliers", indexes = {
    @Index(name = "idx_suppliers_code", columnList = "supplier_code")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class Supplier {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "supplier_code", unique = true, nullable = false, length = 20)
    @NotBlank(message = "供应商编码不能为空")
    @Size(max = 20, message = "供应商编码长度不能超过20个字符")
    private String supplierCode;
    
    @Column(nullable = false, length = 100)
    @NotBlank(message = "供应商名称不能为空")
    @Size(max = 100, message = "供应商名称长度不能超过100个字符")
    private String name;
    
    @Column(name = "contact_person", length = 50)
    @Size(max = 50, message = "联系人长度不能超过50个字符")
    private String contactPerson;
    
    @Column(length = 20)
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "电话号码格式不正确")
    private String phone;
    
    @Column(length = 100)
    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "邮箱长度不能超过100个字符")
    private String email;
    
    @Column(columnDefinition = "TEXT")
    private String address;
    
    @DecimalMin(value = "0.0", message = "评级不能小于0")
    @DecimalMax(value = "5.0", message = "评级不能大于5")
    private BigDecimal rating;
    
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.ACTIVE;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 关联关系
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Part> parts;
    
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PurchaseOrder> purchaseOrders;
    
    public enum StatusEnum {
        ACTIVE, INACTIVE, SUSPENDED
    }
}
