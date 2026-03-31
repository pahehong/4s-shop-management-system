
package com.4s.shop.repository;

import com.4s.shop.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    Optional<Customer> findByCustomerCode(String customerCode);
    
    Optional<Customer> findByPhone(String phone);
    
    List<Customer> findByCustomerLevel(Customer.CustomerLevelEnum customerLevel);
    
    List<Customer> findByStatus(Customer.StatusEnum status);
    
    @Query("SELECT c FROM Customer c WHERE c.totalConsumption >= :minAmount")
    List<Customer> findByTotalConsumptionGreaterThanOrEqual(@Param("minAmount") BigDecimal minAmount);
    
    @Query("SELECT c FROM Customer c WHERE c.name LIKE %:name% OR c.phone LIKE %:name%")
    Page<Customer> findByNameOrPhoneContaining(@Param("name") String name, Pageable pageable);
    
    @Query("SELECT COUNT(c) > 0 FROM Customer c WHERE c.phone = :phone AND c.id != :id")
    boolean existsByPhoneAndIdNot(@Param("phone") String phone, @Param("id") Long id);
}
