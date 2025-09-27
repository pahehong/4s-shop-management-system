
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
@Table(name = "customers", indexes = {
    @Index(name = "idx_customers_phone", columnList = "phone"),
    @Index(name = "idx_customers_level", columnList = "customer_level"),
    @Index(name = "idx_customers_status", columnList = "status")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "customer_code", unique = true, nullable = false, length = 20)
    @NotBlank(message = "客户编码不能为空")
    @Size(max = 20, message = "客户编码长度不能超过20个字符")
    private String customerCode;
    
    @Column(nullable = false, length = 50)
    @NotBlank(message = "客户姓名不能为空")
    @Size(max = 50, message = "客户姓名长度不能超过50个字符")
    private String name;
    
    @Column(length = 10)
    @Size(max = 10, message = "性别长度不能超过10个字符")
    private String gender;
    
    @Min(value = 0, message = "年龄不能小于0")
    @Max(value = 150, message = "年龄不能大于150")
    private Integer age;
    
    @Column(unique = true, nullable = false, length = 20)
    @NotBlank(message = "电话号码不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "电话号码格式不正确")
    private String phone;
    
    @Column(name = "id_card", length = 20)
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$", 
             message = "身份证号码格式不正确")
    private String idCard;
    
    @Column(columnDefinition = "TEXT")
    private String address;
    
    @Column(name = "company_name", length = 100)
    @Size(max = 100, message = "公司名称长度不能超过100个字符")
    private String companyName;
    
    @Column(name = "company_address", length = 200)
    @Size(max = 200, message = "公司地址长度不能超过200个字符")
    private String companyAddress;
    
    @Column(length = 100)
    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "邮箱长度不能超过100个字符")
    private String email;
    
    private LocalDate birthday;
    
    @Column(name = "customer_level", length = 20)
    @Enumerated(EnumType.STRING)
    private CustomerLevelEnum customerLevel = CustomerLevelEnum.NORMAL;
    
    @Column(name = "total_consumption", precision = 12, scale = 2)
    private BigDecimal totalConsumption = BigDecimal.ZERO;
    
    private Integer points = 0;
    
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.ACTIVE;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 关联关系
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vehicle> vehicles;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkOrder> workOrders;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PaymentRecord> paymentRecords;
    
    public enum CustomerLevelEnum {
        VIP, PREMIUM, NORMAL
    }
    
    public enum StatusEnum {
        ACTIVE, INACTIVE, SUSPENDED
    }
}
