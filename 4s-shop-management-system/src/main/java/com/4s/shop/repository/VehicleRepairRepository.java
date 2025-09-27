
package com.4s.shop.repository;

import com.4s.shop.entity.VehicleRepair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VehicleRepairRepository extends JpaRepository<VehicleRepair, Long> {
    
    List<VehicleRepair> findByVehicleId(Long vehicleId);
    
    List<VehicleRepair> findByFaultDescriptionContaining(String faultDescription);
    
    @Query("SELECT vr FROM VehicleRepair vr WHERE vr.vehicle.id = :vehicleId AND vr.repairDate BETWEEN :startDate AND :endDate")
    List<VehicleRepair> findByVehicleIdAndRepairDateBetween(@Param("vehicleId") Long vehicleId, 
                                                           @Param("startDate") LocalDate startDate, 
                                                           @Param("endDate") LocalDate endDate);
    
    @Query("SELECT vr FROM VehicleRepair vr WHERE vr.vehicle.id = :vehicleId ORDER BY vr.repairDate DESC")
    List<VehicleRepair> findByVehicleIdOrderByRepairDateDesc(@Param("vehicleId") Long vehicleId);
    
    @Query("SELECT vr FROM VehicleRepair vr WHERE vr.technician.id = :technicianId")
    List<VehicleRepair> findByTechnicianId(@Param("technicianId") Long technicianId);
    
    @Query("SELECT vr FROM VehicleRepair vr WHERE vr.warrantyExpiryDate > :date")
    List<VehicleRepair> findUnderWarranty(@Param("date") LocalDate date);
    
    @Query("SELECT SUM(vr.cost) FROM VehicleRepair vr WHERE vr.vehicle.id = :vehicleId")
    Double sumCostByVehicleId(@Param("vehicleId") Long vehicleId);
    
    @Query("SELECT SUM(vr.cost) FROM VehicleRepair vr WHERE vr.technician.id = :technicianId AND vr.repairDate BETWEEN :startDate AND :endDate")
    Double sumCostByTechnicianIdAndDateRange(@Param("technicianId") Long technicianId, 
                                            @Param("startDate") LocalDate startDate, 
                                            @Param("endDate") LocalDate endDate);
}
