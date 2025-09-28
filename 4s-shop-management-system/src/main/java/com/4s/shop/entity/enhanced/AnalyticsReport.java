
package com.4s.shop.entity.enhanced;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "analytics_reports", indexes = {
    @Index(name = "idx_analytics_reports_type", columnList = "report_type"),
    @Index(name = "idx_analytics_reports_generated_at", columnList = "generated_at"),
    @Index(name = "idx_analytics_reports_period", columnList = "period_start, period_end"),
    @Index(name = "idx_analytics_reports_type_time", columnList = "report_type, generated_at DESC")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class AnalyticsReport {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "report_type", nullable = false, length = 50)
    @NotBlank(message = "报告类型不能为空")
    @Size(max = 50, message = "报告类型长度不能超过50个字符")
    @Enumerated(EnumType.STRING)
    private ReportTypeEnum reportType;
    
    @Column(name = "report_name", nullable = false, length = 100)
    @NotBlank(message = "报告名称不能为空")
    @Size(max = 100, message = "报告名称长度不能超过100个字符")
    private String reportName;
    
    @Column(name = "report_data", nullable = false, columnDefinition = "jsonb")
    @NotBlank(message = "报告数据不能为空")
    private String reportData;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generated_by")
    private com.4s.shop.entity.User generatedBy;
    
    @CreatedDate
    @Column(name = "generated_at", nullable = false, updatable = false)
    private LocalDateTime generatedAt;
    
    @Column(name = "period_start")
    private LocalDate periodStart;
    
    @Column(name = "period_end")
    private LocalDate periodEnd;
    
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.GENERATED;
    
    public enum ReportTypeEnum {
        BUSINESS_DASHBOARD, EMPLOYEE_PERFORMANCE, CUSTOMER_VALUE, 
        INVENTORY_ANALYSIS, FINANCIAL_REPORT, SALES_ANALYSIS
    }
    
    public enum StatusEnum {
        GENERATED, PROCESSING, FAILED
    }
}
