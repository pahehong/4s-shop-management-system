
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
import java.util.List;

@Entity
@Table(name = "employees", indexes = {
    @Index(name = "idx_employees_code", columnList = "employee_code"),
    @Index(name = "idx_employees_department", columnList = "department_id"),
    @Index(name = "idx_employees_position", columnList = "position_id")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "employee_code", unique = true, nullable = false, length = 20)
    @NotBlank(message = "员工编码不能为空")
    @Size(max = 20, message = "员工编码长度不能超过20个字符")
    private String employeeCode;
    
    @Column(nullable = false, length = 50)
    @NotBlank(message = "员工姓名不能为空")
    @Size(max = 50, message = "员工姓名长度不能超过50个字符")
    private String name;
    
    @Column(length = 10)
    @Size(max = 10, message = "性别长度不能超过10个字符")
    private String gender;
    
    @Column(length = 20)
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "电话号码格式不正确")
    private String phone;
    
    @Column(length = 100)
    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "邮箱长度不能超过100个字符")
    private String email;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;
    
    @Column(name = "skill_level", length = 20)
    @Size(max = 20, message = "技能等级长度不能超过20个字符")
    private String skillLevel;
    
    @Column(name = "hire_date")
    private LocalDate hireDate;
    
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.ACTIVE;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 关联关系
    @OneToMany(mappedBy = "assignedTechnician", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkOrder> assignedWorkOrders;
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PurchaseOrder> purchaseOrders;
    
    @OneToMany(mappedBy = "serviceAdvisor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VehicleMaintenance> vehicleMaintenances;
    
    @OneToMany(mappedBy = "technician", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VehicleRepair> vehicleRepairs;
    
    public enum StatusEnum {
        ACTIVE, INACTIVE, RESIGNED
    }
}
