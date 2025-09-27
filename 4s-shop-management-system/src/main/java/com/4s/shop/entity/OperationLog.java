
package com.4s.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "operation_logs", indexes = {
    @Index(name = "idx_operation_logs_user", columnList = "user_id"),
    @Index(name = "idx_operation_logs_resource", columnList = "resource_type, resource_id"),
    @Index(name = "idx_operation_logs_created_at", columnList = "created_at")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class OperationLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "用户信息不能为空")
    private User user;
    
    @Column(nullable = false, length = 100)
    @NotBlank(message = "操作类型不能为空")
    @Size(max = 100, message = "操作类型长度不能超过100个字符")
    private String operation;
    
    @Column(name = "resource_type", length = 50)
    @Size(max = 50, message = "资源类型长度不能超过50个字符")
    private String resourceType;
    
    @Column(name = "resource_id")
    private Long resourceId;
    
    @Column(name = "old_values", columnDefinition = "jsonb")
    private String oldValues;
    
    @Column(name = "new_values", columnDefinition = "jsonb")
    private String newValues;
    
    @Column(name = "ip_address")
    private String ipAddress;
    
    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
