
package com.4s.shop.entity.enhanced;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ai_models", indexes = {
    @Index(name = "idx_ai_models_type", columnList = "model_type"),
    @Index(name = "idx_ai_models_status", columnList = "status")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class AiModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "model_name", nullable = false, length = 100)
    @NotBlank(message = "模型名称不能为空")
    @Size(max = 100, message = "模型名称长度不能超过100个字符")
    private String modelName;
    
    @Column(name = "model_type", nullable = false, length = 50)
    @NotBlank(message = "模型类型不能为空")
    @Size(max = 50, message = "模型类型长度不能超过50个字符")
    @Enumerated(EnumType.STRING)
    private ModelTypeEnum modelType;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(length = 20)
    @Size(max = 20, message = "版本号长度不能超过20个字符")
    private String version;
    
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.ACTIVE;
    
    @Column(name = "is_default")
    private Boolean isDefault = false;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 关联关系
    @OneToMany(mappedBy = "aiModel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AiModelResult> modelResults;
    
    public enum ModelTypeEnum {
        FAULT_DIAGNOSIS, CUSTOMER_CHURN, DEMAND_FORECASTING, PREDICTIVE_MAINTENANCE
    }
    
    public enum StatusEnum {
        ACTIVE, INACTIVE, MAINTENANCE
    }
}
