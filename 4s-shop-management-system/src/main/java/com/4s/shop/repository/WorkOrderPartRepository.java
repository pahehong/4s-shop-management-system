
package com.4s.shop.repository;

import com.4s.shop.entity.WorkOrderPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkOrderPartRepository extends JpaRepository<WorkOrderPart, Long> {
    
    List<WorkOrderPart> findByWorkOrderId(Long workOrderId);
    
    List<WorkOrderPart> findByPartId(Long partId);
    
    @Query("SELECT wop FROM WorkOrderPart wop WHERE wop.workOrder.id = :workOrderId")
    List<WorkOrderPart> findByWorkOrderId(@Param("workOrderId") Long workOrderId);
    
    @Query("SELECT wop FROM WorkOrderPart wop WHERE wop.part.id = :partId AND wop.workOrder.status != 'CANCELLED'")
    List<WorkOrderPart> findByPartIdAndWorkOrderStatusNotCancelled(@Param("partId") Long partId);
    
    @Query("SELECT SUM(wop.totalPrice) FROM WorkOrderPart wop WHERE wop.workOrder.id = :workOrderId")
    Double sumTotalPriceByWorkOrderId(@Param("workOrderId") Long workOrderId);
    
    @Query("SELECT wop FROM WorkOrderPart wop WHERE wop.workOrder.id = :workOrderId AND wop.part.id = :partId")
    List<WorkOrderPart> findByWorkOrderIdAndPartId(@Param("workOrderId") Long workOrderId, 
                                                  @Param("partId") Long partId);
}
