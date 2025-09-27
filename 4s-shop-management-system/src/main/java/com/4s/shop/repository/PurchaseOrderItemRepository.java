
package com.4s.shop.repository;

import com.4s.shop.entity.PurchaseOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItem, Long> {
    
    List<PurchaseOrderItem> findByPurchaseOrderId(Long purchaseOrderId);
    
    List<PurchaseOrderItem> findByPartId(Long partId);
    
    @Query("SELECT poi FROM PurchaseOrderItem poi WHERE poi.purchaseOrder.id = :purchaseOrderId")
    List<PurchaseOrderItem> findByPurchaseOrderId(@Param("purchaseOrderId") Long purchaseOrderId);
    
    @Query("SELECT poi FROM PurchaseOrderItem poi WHERE poi.part.id = :partId AND poi.purchaseOrder.status != 'CANCELLED'")
    List<PurchaseOrderItem> findByPartIdAndPurchaseOrderStatusNotCancelled(@Param("partId") Long partId);
    
    @Query("SELECT SUM(poi.totalPrice) FROM PurchaseOrderItem poi WHERE poi.purchaseOrder.id = :purchaseOrderId")
    Double sumTotalPriceByPurchaseOrderId(@Param("purchaseOrderId") Long purchaseOrderId);
    
    @Query("SELECT poi FROM PurchaseOrderItem poi WHERE poi.purchaseOrder.id = :purchaseOrderId AND poi.part.id = :partId")
    List<PurchaseOrderItem> findByPurchaseOrderIdAndPartId(@Param("purchaseOrderId") Long purchaseOrderId, 
                                                          @Param("partId") Long partId);
}
