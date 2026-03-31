
package com.4s.shop.entity.enhanced;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "iot_vehicle_data", indexes = {
    @Index(name = "idx_iot_vehicle_data_vehicle_time", columnList = "vehicle_id, timestamp"),
    @Index(name = "idx_iot_vehicle_data_timestamp", columnList = "timestamp"),
    @Index(name = "idx_iot_vehicle_data_device", columnList = "device_id"),
    @Index(name = "idx_iot_vehicle_data_type", columnList = "data_type"),
    @Index(name = "idx_iot_vehicle_data_vehicle_type_time", columnList = "vehicle_id, data_type, timestamp DESC")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
public class IotVehicleData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    @NotNull(message = "设备信息不能为空")
    private IotDevice iotDevice;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    @NotNull(message = "车辆信息不能为空")
    private com.4s.shop.entity.Vehicle vehicle;
    
    @Column(name = "data_type", nullable = false, length = 50)
    @NotBlank(message = "数据类型不能为空")
    @Size(max = 50, message = "数据类型长度不能超过50个字符")
    @Enumerated(EnumType.STRING)
    private DataTypeEnum dataType;
    
    @Column(name = "data_payload", nullable = false, columnDefinition = "jsonb")
    @NotBlank(message = "数据载荷不能为空")
    private String dataPayload;
    
    @Column(nullable = false)
    @NotNull(message = "时间戳不能为空")
    private LocalDateTime timestamp;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    public enum DataTypeEnum {
        OBD, GPS, SENSOR, CAMERA
    }
}
