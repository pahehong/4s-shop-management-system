
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

@Entity
@Table(name = "invoices", indexes = {
    @Index(name = "idx_invoices_number", columnList = "invoice_number"),
    @Index(name = "idx_invoices_work_order", columnList = "work_order_id"),
    @Index(name = "idx_invoices_customer", columnList = "customer_id")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class Invoice {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "invoice_number", unique = true, nullable = false, length = 30)
    @NotBlank(message = "发票号不能为空")
    @Size(max = 30, message = "发票号长度不能超过30个字符")
    private String invoiceNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id", nullable = false)
    @NotNull(message = "工单信息不能为空")
    private WorkOrder workOrder;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull(message = "客户信息不能为空")
    private Customer customer;
    
    @Column(name = "total_amount", precision = 12, scale = 2)
    @DecimalMin(value = "0.0", message = "总金额不能小于0")
    private BigDecimal totalAmount;
    
    @Column(name = "tax_amount", precision = 12, scale = 2)
    @DecimalMin(value = "0.0", message = "税额不能小于0")
    private BigDecimal taxAmount;
    
    @Column(name = "invoice_status", length = 20)
    @Enumerated(EnumType.STRING)
    private InvoiceStatusEnum invoiceStatus = InvoiceStatusEnum.DRAFT;
    
    @Column(name = "invoice_type", length = 20)
    @Enumerated(EnumType.STRING)
    private InvoiceTypeEnum invoiceType = InvoiceTypeEnum.NORMAL;
    
    @Column(name = "issued_date")
    private LocalDate issuedDate;
    
    @Column(name = "due_date")
    private LocalDate dueDate;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum InvoiceStatusEnum {
        DRAFT, ISSUED, SENT, PAID
    }
    
    public enum InvoiceTypeEnum {
        NORMAL, SPECIAL, ELECTRONIC
    }
}
