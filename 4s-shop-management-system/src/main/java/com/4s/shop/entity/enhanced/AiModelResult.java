
package com.4s.shop.entity.enhanced;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ai_model_results", indexes = {
    @Index(name = "idx_ai_model_results_type_entity", columnList = "model_type, entity_type, entity_id"),
    @Index(name = "idx_ai_model_results_model", columnList = "model_id"),
    @Index(name = "idx_ai_model_results_created_at", columnList = "created_at"),
    @Index(name = "idx_ai_model_results_type_time", columnList = "model_type, created_at")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class AiModelResult {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private AiModel aiModel;
    
    @Column(name = "model_type", nullable = false, length = 50)
    @NotBlank(message = "模型类型不能为空")
    @Size(max = 50, message = "模型类型长度不能超过50个字符")
    @Enumerated(EnumType.STRING)
    private AiModel.ModelTypeEnum modelType;
    
    @Column(name = "entity_id")
    private Long entityId;
    
    @Column(name = "entity_type", length = 50)
    @Size(max = 50, message = "实体类型长度不能超过50个字符")
    private String entityType;
    
    @Column(name = "prediction_result", columnDefinition = "jsonb")
    private String predictionResult;
    
    @Column(name = "confidence_score", precision = 5, scale = 4)
    @DecimalMin(value = "0.0", message = "置信度不能小于0")
    @DecimalMax(value = "1.0", message = "置信度不能大于1")
    private BigDecimal confidenceScore;
    
    @Column(columnDefinition = "TEXT")
    private String explanation;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
