
package com.4s.shop.repository;

import com.4s.shop.entity.WorkOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {
    
    Optional<WorkOrder> findByOrderNumber(String orderNumber);
    
    List<WorkOrder> findByCustomerId(Long customerId);
    
    List<WorkOrder> findByVehicleId(Long vehicleId);
    
    List<WorkOrder> findByStatus(WorkOrder.StatusEnum status);
    
    List<WorkOrder> findByOrderType(WorkOrder.OrderTypeEnum orderType);
    
    List<WorkOrder> findByAssignedTechnicianId(Long technicianId);
    
    @Query("SELECT w FROM WorkOrder w WHERE w.customer.id = :customerId AND w.status = :status")
    List<WorkOrder> findByCustomerIdAndStatus(@Param("customerId") Long customerId, 
                                             @Param("status") WorkOrder.StatusEnum status);
    
    @Query("SELECT w FROM WorkOrder w WHERE w.vehicle.id = :vehicleId AND w.status = :status")
    List<WorkOrder> findByVehicleIdAndStatus(@Param("vehicleId") Long vehicleId, 
                                            @Param("status") WorkOrder.StatusEnum status);
    
    @Query("SELECT w FROM WorkOrder w WHERE w.assignedTechnician.id = :technicianId AND w.status = :status")
    List<WorkOrder> findByTechnicianIdAndStatus(@Param("technicianId") Long technicianId, 
                                               @Param("status") WorkOrder.StatusEnum status);
    
    @Query("SELECT w FROM WorkOrder w WHERE w.createdAt BETWEEN :startDate AND :endDate")
    List<WorkOrder> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, 
                                          @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT w FROM WorkOrder w WHERE w.orderNumber LIKE %:keyword% OR w.customer.name LIKE %:keyword% OR w.vehicle.licensePlate LIKE %:keyword%")
    Page<WorkOrder> findByKeywordContaining(@Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT COUNT(w) > 0 FROM WorkOrder w WHERE w.orderNumber = :orderNumber AND w.id != :id")
    boolean existsByOrderNumberAndIdNot(@Param("orderNumber") String orderNumber, @Param("id") Long id);
    
    @Query("SELECT w FROM WorkOrder w WHERE w.priority = :priority AND w.status IN :statuses")
    List<WorkOrder> findByPriorityAndStatusIn(@Param("priority") WorkOrder.PriorityEnum priority, 
                                             @Param("statuses") List<WorkOrder.StatusEnum> statuses);
}
