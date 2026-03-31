
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
@Table(name = "work_orders", indexes = {
    @Index(name = "idx_work_orders_customer", columnList = "customer_id"),
    @Index(name = "idx_work_orders_vehicle", columnList = "vehicle_id"),
    @Index(name = "idx_work_orders_status", columnList = "status"),
    @Index(name = "idx_work_orders_created_at", columnList = "created_at"),
    @Index(name = "idx_work_orders_assigned_tech", columnList = "assigned_technician_id")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class WorkOrder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "order_number", unique = true, nullable = false, length = 30)
    @NotBlank(message = "工单号不能为空")
    @Size(max = 30, message = "工单号长度不能超过30个字符")
    private String orderNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull(message = "客户信息不能为空")
    private Customer customer;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    @NotNull(message = "车辆信息不能为空")
    private Vehicle vehicle;
    
    @Column(name = "order_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "工单类型不能为空")
    private OrderTypeEnum orderType;
    
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.PENDING;
    
    @Enumerated(EnumType.STRING)
    private PriorityEnum priority = PriorityEnum.NORMAL;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "estimated_hours", precision = 5, scale = 2)
    @DecimalMin(value = "0.0", message = "预估工时不能小于0")
    private BigDecimal estimatedHours;
    
    @Column(name = "actual_hours", precision = 5, scale = 2)
    @DecimalMin(value = "0.0", message = "实际工时不能小于0")
    private BigDecimal actualHours;
    
    @Column(name = "estimated_cost", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", message = "预估费用不能小于0")
    private BigDecimal estimatedCost;
    
    @Column(name = "actual_cost", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", message = "实际费用不能小于0")
    private BigDecimal actualCost;
    
    @Column(name = "start_time")
    private LocalDateTime startTime;
    
    @Column(name = "end_time")
    private LocalDateTime endTime;
    
    @Column(name = "scheduled_start_time")
    private LocalDateTime scheduledStartTime;
    
    @Column(name = "scheduled_end_time")
    private LocalDateTime scheduledEndTime;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_technician_id")
    private Employee assignedTechnician;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 关联关系
    @OneToMany(mappedBy = "workOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkOrderItem> workOrderItems;
    
    @OneToMany(mappedBy = "workOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkOrderPart> workOrderParts;
    
    @OneToMany(mappedBy = "workOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Invoice> invoices;
    
    public enum OrderTypeEnum {
        REPAIR, MAINTENANCE, MODIFICATION
    }
    
    public enum StatusEnum {
        PENDING, IN_PROGRESS, COMPLETED, CANCELLED
    }
    
    public enum PriorityEnum {
        LOW, NORMAL, HIGH, URGENT
    }
}
