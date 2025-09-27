
package com.4s.shop.repository;

import com.4s.shop.entity.VehicleMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VehicleMaintenanceRepository extends JpaRepository<VehicleMaintenance, Long> {
    
    List<VehicleMaintenance> findByVehicleId(Long vehicleId);
    
    List<VehicleMaintenance> findByMaintenanceType(String maintenanceType);
    
    @Query("SELECT vm FROM VehicleMaintenance vm WHERE vm.vehicle.id = :vehicleId AND vm.maintenanceDate BETWEEN :startDate AND :endDate")
    List<VehicleMaintenance> findByVehicleIdAndMaintenanceDateBetween(@Param("vehicleId") Long vehicleId, 
                                                                    @Param("startDate") LocalDate startDate, 
                                                                    @Param("endDate") LocalDate endDate);
    
    @Query("SELECT vm FROM VehicleMaintenance vm WHERE vm.vehicle.id = :vehicleId ORDER BY vm.maintenanceDate DESC")
    List<VehicleMaintenance> findByVehicleIdOrderByMaintenanceDateDesc(@Param("vehicleId") Long vehicleId);
    
    @Query("SELECT vm FROM VehicleMaintenance vm WHERE vm.nextMaintenanceDate < :date")
    List<VehicleMaintenance> findDueMaintenance(@Param("date") LocalDate date);
    
    @Query("SELECT vm FROM VehicleMaintenance vm WHERE vm.serviceAdvisor.id = :advisorId")
    List<VehicleMaintenance> findByServiceAdvisorId(@Param("advisorId") Long advisorId);
    
    @Query("SELECT SUM(vm.cost) FROM VehicleMaintenance vm WHERE vm.vehicle.id = :vehicleId")
    Double sumCostByVehicleId(@Param("vehicleId") Long vehicleId);
}
