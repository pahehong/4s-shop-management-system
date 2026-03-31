
package com.4s.shop.entity.enhanced;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "iot_devices", indexes = {
    @Index(name = "idx_iot_devices_vehicle", columnList = "vehicle_id"),
    @Index(name = "idx_iot_devices_customer", columnList = "customer_id"),
    @Index(name = "idx_iot_devices_status", columnList = "status")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class IotDevice {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "device_id", unique = true, nullable = false, length = 100)
    @NotBlank(message = "设备ID不能为空")
    @Size(max = 100, message = "设备ID长度不能超过100个字符")
    private String deviceId;
    
    @Column(name = "device_name", length = 100)
    @Size(max = 100, message = "设备名称长度不能超过100个字符")
    private String deviceName;
    
    @Column(name = "device_type", nullable = false, length = 50)
    @NotBlank(message = "设备类型不能为空")
    @Size(max = 50, message = "设备类型长度不能超过50个字符")
    @Enumerated(EnumType.STRING)
    private DeviceTypeEnum deviceType;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private com.4s.shop.entity.Vehicle vehicle;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private com.4s.shop.entity.Customer customer;
    
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.ACTIVE;
    
    @Column(name = "last_connected_at")
    private LocalDateTime lastConnectedAt;
    
    @Column(name = "firmware_version", length = 20)
    @Size(max = 20, message = "固件版本长度不能超过20个字符")
    private String firmwareVersion;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 关联关系
    @OneToMany(mappedBy = "iotDevice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<IotVehicleData> iotVehicleDataList;
    
    public enum DeviceTypeEnum {
        OBD, GPS, SENSOR, CAMERA
    }
    
    public enum StatusEnum {
        ACTIVE, INACTIVE, FAULTY
    }
}
