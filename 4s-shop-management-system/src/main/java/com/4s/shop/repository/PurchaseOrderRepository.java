
package com.4s.shop.repository;

import com.4s.shop.entity.PurchaseOrder;
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
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    
    Optional<PurchaseOrder> findByPoNumber(String poNumber);
    
    List<PurchaseOrder> findBySupplierId(Long supplierId);
    
    List<PurchaseOrder> findByEmployeeId(Long employeeId);
    
    List<PurchaseOrder> findByStatus(PurchaseOrder.StatusEnum status);
    
    @Query("SELECT po FROM PurchaseOrder po WHERE po.supplier.id = :supplierId AND po.status = :status")
    List<PurchaseOrder> findBySupplierIdAndStatus(@Param("supplierId") Long supplierId, 
                                                 @Param("status") PurchaseOrder.StatusEnum status);
    
    @Query("SELECT po FROM PurchaseOrder po WHERE po.employee.id = :employeeId AND po.status = :status")
    List<PurchaseOrder> findByEmployeeIdAndStatus(@Param("employeeId") Long employeeId, 
                                                 @Param("status") PurchaseOrder.StatusEnum status);
    
    @Query("SELECT po FROM PurchaseOrder po WHERE po.expectedDeliveryDate < :date AND po.status != 'COMPLETED'")
    List<PurchaseOrder> findOverdueOrders(@Param("date") LocalDate date);
    
    @Query("SELECT po FROM PurchaseOrder po WHERE po.poNumber LIKE %:keyword% OR po.supplier.name LIKE %:keyword%")
    Page<PurchaseOrder> findByKeywordContaining(@Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT COUNT(po) > 0 FROM PurchaseOrder po WHERE po.poNumber = :poNumber AND po.id != :id")
    boolean existsByPoNumberAndIdNot(@Param("poNumber") String poNumber, @Param("id") Long id);
    
    @Query("SELECT po FROM PurchaseOrder po WHERE po.createdAt BETWEEN :startDate AND :endDate")
    List<PurchaseOrder> findByCreatedAtBetween(@Param("startDate") java.time.LocalDateTime startDate, 
                                              @Param("endDate") java.time.LocalDateTime endDate);
}
