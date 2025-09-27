
package com.4s.shop.repository;

import com.4s.shop.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    
    Optional<Vehicle> findByVin(String vin);
    
    Optional<Vehicle> findByLicensePlate(String licensePlate);
    
    List<Vehicle> findByCustomerId(Long customerId);
    
    List<Vehicle> findByBrand(String brand);
    
    List<Vehicle> findByModel(String model);
    
    List<Vehicle> findByStatus(Vehicle.StatusEnum status);
    
    @Query("SELECT v FROM Vehicle v WHERE v.customer.id = :customerId AND v.status = :status")
    List<Vehicle> findByCustomerIdAndStatus(@Param("customerId") Long customerId, 
                                           @Param("status") Vehicle.StatusEnum status);
    
    @Query("SELECT v FROM Vehicle v WHERE v.brand LIKE %:keyword% OR v.model LIKE %:keyword% OR v.vin LIKE %:keyword% OR v.licensePlate LIKE %:keyword%")
    Page<Vehicle> findByKeywordContaining(@Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT COUNT(v) > 0 FROM Vehicle v WHERE v.vin = :vin AND v.id != :id")
    boolean existsByVinAndIdNot(@Param("vin") String vin, @Param("id") Long id);
    
    @Query("SELECT COUNT(v) > 0 FROM Vehicle v WHERE v.licensePlate = :licensePlate AND v.id != :id")
    boolean existsByLicensePlateAndIdNot(@Param("licensePlate") String licensePlate, @Param("id") Long id);
}
