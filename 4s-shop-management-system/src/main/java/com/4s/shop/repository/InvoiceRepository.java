
package com.4s.shop.repository;

import com.4s.shop.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
    
    List<Invoice> findByWorkOrderId(Long workOrderId);
    
    List<Invoice> findByCustomerId(Long customerId);
    
    List<Invoice> findByInvoiceStatus(Invoice.InvoiceStatusEnum invoiceStatus);
    
    List<Invoice> findByInvoiceType(Invoice.InvoiceTypeEnum invoiceType);
    
    @Query("SELECT i FROM Invoice i WHERE i.workOrder.id = :workOrderId AND i.invoiceStatus = :invoiceStatus")
    List<Invoice> findByWorkOrderIdAndInvoiceStatus(@Param("workOrderId") Long workOrderId, 
                                                   @Param("invoiceStatus") Invoice.InvoiceStatusEnum invoiceStatus);
    
    @Query("SELECT i FROM Invoice i WHERE i.customer.id = :customerId AND i.invoiceStatus = :invoiceStatus")
    List<Invoice> findByCustomerIdAndInvoiceStatus(@Param("customerId") Long customerId, 
                                                  @Param("invoiceStatus") Invoice.InvoiceStatusEnum invoiceStatus);
    
    @Query("SELECT i FROM Invoice i WHERE i.issuedDate BETWEEN :startDate AND :endDate")
    List<Invoice> findByIssuedDateBetween(@Param("startDate") LocalDate startDate, 
                                         @Param("endDate") LocalDate endDate);
    
    @Query("SELECT i FROM Invoice i WHERE i.invoiceNumber LIKE %:keyword% OR i.customer.name LIKE %:keyword%")
    Page<Invoice> findByKeywordContaining(@Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT COUNT(i) > 0 FROM Invoice i WHERE i.invoiceNumber = :invoiceNumber AND i.id != :id")
    boolean existsByInvoiceNumberAndIdNot(@Param("invoiceNumber") String invoiceNumber, @Param("id") Long id);
    
    @Query("SELECT SUM(i.totalAmount) FROM Invoice i WHERE i.customer.id = :customerId AND i.invoiceStatus = 'PAID'")
    Double sumPaidAmountByCustomerId(@Param("customerId") Long customerId);
    
    @Query("SELECT i FROM Invoice i WHERE i.dueDate < :date AND i.invoiceStatus IN ('ISSUED', 'SENT')")
    List<Invoice> findOverdueInvoices(@Param("date") LocalDate date);
}
