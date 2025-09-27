
package com.4s.shop.repository;

import com.4s.shop.entity.PaymentRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, Long> {
    
    List<PaymentRecord> findByCustomerId(Long customerId);
    
    List<PaymentRecord> findByPaymentMethod(PaymentRecord.PaymentMethodEnum paymentMethod);
    
    List<PaymentRecord> findByPaymentStatus(PaymentRecord.PaymentStatusEnum paymentStatus);
    
    @Query("SELECT pr FROM PaymentRecord pr WHERE pr.customer.id = :customerId AND pr.paymentStatus = :paymentStatus")
    List<PaymentRecord> findByCustomerIdAndPaymentStatus(@Param("customerId") Long customerId, 
                                                        @Param("paymentStatus") PaymentRecord.PaymentStatusEnum paymentStatus);
    
    @Query("SELECT pr FROM PaymentRecord pr WHERE pr.orderId = :orderId AND pr.orderType = :orderType")
    List<PaymentRecord> findByOrderIdAndOrderType(@Param("orderId") Long orderId, 
                                                 @Param("orderType") String orderType);
    
    @Query("SELECT SUM(pr.amount) FROM PaymentRecord pr WHERE pr.customer.id = :customerId AND pr.paymentStatus = 'PAID'")
    BigDecimal sumPaidAmountByCustomerId(@Param("customerId") Long customerId);
    
    @Query("SELECT pr FROM PaymentRecord pr WHERE pr.createdAt BETWEEN :startDate AND :endDate")
    List<PaymentRecord> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, 
                                              @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT pr FROM PaymentRecord pr WHERE pr.customer.id = :customerId AND pr.createdAt BETWEEN :startDate AND :endDate")
    List<PaymentRecord> findByCustomerIdAndCreatedAtBetween(@Param("customerId") Long customerId, 
                                                           @Param("startDate") LocalDateTime startDate, 
                                                           @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT pr FROM PaymentRecord pr WHERE pr.amount >= :minAmount AND pr.paymentStatus = 'PAID'")
    Page<PaymentRecord> findByAmountGreaterThanOrEqualAndPaymentStatusPaid(@Param("minAmount") BigDecimal minAmount, 
                                                                          Pageable pageable);
    
    @Query("SELECT COUNT(pr) > 0 FROM PaymentRecord pr WHERE pr.transactionId = :transactionId AND pr.id != :id")
    boolean existsByTransactionIdAndIdNot(@Param("transactionId") String transactionId, @Param("id") Long id);
}
