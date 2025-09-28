
package com.4s.shop.entity.enhanced;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "business_metrics", indexes = {
    @Index(name = "idx_business_metrics_type_period", columnList = "metric_type, period_start, period_end"),
    @Index(name = "idx_business_metrics_calculated_at", columnList = "calculated_at")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessMetric {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "metric_name", nullable = false, length = 100)
    @NotBlank(message = "指标名称不能为空")
    @Size(max = 100, message = "指标名称长度不能超过100个字符")
    private String metricName;
    
    @Column(name = "metric_type", nullable = false, length = 50)
    @NotBlank(message = "指标类型不能为空")
    @Size(max = 50, message = "指标类型长度不能超过50个字符")
    @Enumerated(EnumType.STRING)
    private MetricTypeEnum metricType;
    
    @Column(name = "metric_value", precision = 15, scale = 2, nullable = false)
    @NotNull(message = "指标值不能为空")
    @DecimalMin(value = "0.0", message = "指标值不能小于0")
    private BigDecimal metricValue;
    
    @Column(length = 20)
    @Size(max = 20, message = "单位长度不能超过20个字符")
    private String unit;
    
    @Column(name = "period_start", nullable = false)
    @NotNull(message = "周期开始日期不能为空")
    private LocalDate periodStart;
    
    @Column(name = "period_end", nullable = false)
    @NotNull(message = "周期结束日期不能为空")
    private LocalDate periodEnd;
    
    @CreatedDate
    @Column(name = "calculated_at", nullable = false, updatable = false)
    private LocalDateTime calculatedAt;
    
    public enum MetricTypeEnum {
        SALES, SERVICE, CUSTOMER, FINANCIAL, INVENTORY, PERFORMANCE
    }
}
