
package com.4s.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_records", indexes = {
    @Index(name = "idx_payments_customer", columnList = "customer_id"),
    @Index(name = "idx_payments_status", columnList = "payment_status"),
    @Index(name = "idx_payments_created_at", columnList = "created_at")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class PaymentRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "order_id")
    private Long orderId;
    
    @Column(name = "order_type", length = 20)
    @Size(max = 20, message = "订单类型长度不能超过20个字符")
    private String orderType;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull(message = "客户信息不能为空")
    private Customer customer;
    
    @DecimalMin(value = "0.0", message = "支付金额不能小于0")
    private BigDecimal amount;
    
    @Column(name = "payment_method", length = 20)
    @Size(max = 20, message = "支付方式长度不能超过20个字符")
    @Enumerated(EnumType.STRING)
    private PaymentMethodEnum paymentMethod;
    
    @Column(name = "payment_status", length = 20)
    @Enumerated(EnumType.STRING)
    private PaymentStatusEnum paymentStatus = PaymentStatusEnum.PENDING;
    
    @Column(name = "transaction_id", length = 100)
    @Size(max = 100, message = "交易号长度不能超过100个字符")
    private String transactionId;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "paid_at")
    private LocalDateTime paidAt;
    
    public enum PaymentMethodEnum {
        CASH, CARD, WECHAT, ALIPAY, BANK_TRANSFER
    }
    
    public enum PaymentStatusEnum {
        PENDING, PAID, REFUNDED
    }
}
