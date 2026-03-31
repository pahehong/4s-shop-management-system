
package com.4s.shop.repository;

import com.4s.shop.entity.WorkOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkOrderItemRepository extends JpaRepository<WorkOrderItem, Long> {
    
    List<WorkOrderItem> findByWorkOrderId(Long workOrderId);
    
    List<WorkOrderItem> findByServiceTypeId(Long serviceTypeId);
    
    List<WorkOrderItem> findByStatus(WorkOrderItem.StatusEnum status);
    
    @Query("SELECT woi FROM WorkOrderItem woi WHERE woi.workOrder.id = :workOrderId AND woi.status = :status")
    List<WorkOrderItem> findByWorkOrderIdAndStatus(@Param("workOrderId") Long workOrderId, 
                                                  @Param("status") WorkOrderItem.StatusEnum status);
    
    @Query("SELECT SUM(woi.totalPrice) FROM WorkOrderItem woi WHERE woi.workOrder.id = :workOrderId")
    Double sumTotalPriceByWorkOrderId(@Param("workOrderId") Long workOrderId);
}
