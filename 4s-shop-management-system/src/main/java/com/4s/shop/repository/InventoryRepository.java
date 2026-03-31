
package com.4s.shop.repository;

import com.4s.shop.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    
    Optional<Inventory> findByPartIdAndWarehouseId(Long partId, Long warehouseId);
    
    List<Inventory> findByPartId(Long partId);
    
    List<Inventory> findByWarehouseId(Long warehouseId);
    
    @Query("SELECT i FROM Inventory i WHERE i.part.id = :partId AND i.warehouse.id = :warehouseId")
    Optional<Inventory> findByPartIdAndWarehouseId(@Param("partId") Long partId, 
                                                  @Param("warehouseId") Long warehouseId);
    
    @Query("SELECT i FROM Inventory i WHERE i.part.id = :partId AND i.availableQuantity > 0")
    List<Inventory> findByPartIdWithAvailableQuantity(@Param("partId") Long partId);
    
    @Query("SELECT i FROM Inventory i WHERE i.warehouse.id = :warehouseId AND i.availableQuantity <= i.part.minStock")
    List<Inventory> findLowStockInventories(@Param("warehouseId") Long warehouseId);
    
    @Query("SELECT i FROM Inventory i WHERE i.availableQuantity <= i.part.minStock")
    List<Inventory> findLowStockInventoriesAllWarehouses();
    
    @Query("SELECT i FROM Inventory i WHERE i.part.id = :partId AND i.warehouse.id = :warehouseId AND i.availableQuantity >= :quantity")
    Optional<Inventory> findByPartIdAndWarehouseIdAndAvailableQuantityGreaterThanOrEqual(
            @Param("partId") Long partId, 
            @Param("warehouseId") Long warehouseId, 
            @Param("quantity") Integer quantity);
    
    @Query("SELECT i FROM Inventory i WHERE i.part.name LIKE %:keyword% OR i.part.partCode LIKE %:keyword%")
    List<Inventory> findByPartNameOrCodeContaining(@Param("keyword") String keyword);
}
