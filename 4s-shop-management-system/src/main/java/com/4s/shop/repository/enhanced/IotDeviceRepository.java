
package com.4s.shop.repository.enhanced;

import com.4s.shop.entity.enhanced.IotDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IotDeviceRepository extends JpaRepository<IotDevice, Long> {
    
    Optional<IotDevice> findByDeviceId(String deviceId);
    
    List<IotDevice> findByVehicleId(Long vehicleId);
    
    List<IotDevice> findByCustomerId(Long customerId);
    
    List<IotDevice> findByStatus(IotDevice.StatusEnum status);
    
    List<IotDevice> findByDeviceType(IotDevice.DeviceTypeEnum deviceType);
    
    @Query("SELECT id FROM IotDevice id WHERE id.vehicle.id = :vehicleId AND id.status = :status")
    List<IotDevice> findByVehicleIdAndStatus(@Param("vehicleId") Long vehicleId, 
                                            @Param("status") IotDevice.StatusEnum status);
    
    @Query("SELECT COUNT(id) > 0 FROM IotDevice id WHERE id.deviceId = :deviceId AND id.id != :id")
    boolean existsByDeviceIdAndIdNot(@Param("deviceId") String deviceId, @Param("id") Long id);
    
    @Query("SELECT id FROM IotDevice id WHERE id.lastConnectedAt > :lastConnectedAfter")
    List<IotDevice> findActiveDevices(@Param("lastConnectedAfter") java.time.LocalDateTime lastConnectedAfter);
}
