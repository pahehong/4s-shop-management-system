
package com.4s.shop.repository.enhanced;

import com.4s.shop.entity.enhanced.IotVehicleData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IotVehicleDataRepository extends JpaRepository<IotVehicleData, Long> {
    
    List<IotVehicleData> findByVehicleId(Long vehicleId);
    
    List<IotVehicleData> findByDeviceId(Long deviceId);
    
    List<IotVehicleData> findByDataType(IotVehicleData.DataTypeEnum dataType);
    
    @Query("SELECT ivd FROM IotVehicleData ivd WHERE ivd.vehicle.id = :vehicleId AND ivd.timestamp BETWEEN :startTime AND :endTime ORDER BY ivd.timestamp DESC")
    List<IotVehicleData> findByVehicleIdAndTimestampBetweenOrderByTimestampDesc(
            @Param("vehicleId") Long vehicleId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    @Query("SELECT ivd FROM IotVehicleData ivd WHERE ivd.vehicle.id = :vehicleId AND ivd.dataType = :dataType AND ivd.timestamp > :since ORDER BY ivd.timestamp DESC")
    List<IotVehicleData> findByVehicleIdAndDataTypeAndTimestampAfter(
            @Param("vehicleId") Long vehicleId,
            @Param("dataType") IotVehicleData.DataTypeEnum dataType,
            @Param("since") LocalDateTime since);
    
    @Query("SELECT ivd FROM IotVehicleData ivd WHERE ivd.vehicle.id = :vehicleId ORDER BY ivd.timestamp DESC")
    Page<IotVehicleData> findByVehicleIdOrderByTimestampDesc(@Param("vehicleId") Long vehicleId, Pageable pageable);
    
    @Query("SELECT ivd FROM IotVehicleData ivd WHERE ivd.timestamp BETWEEN :startTime AND :endTime")
    List<IotVehicleData> findByTimestampBetween(@Param("startTime") LocalDateTime startTime, 
                                               @Param("endTime") LocalDateTime endTime);
}
