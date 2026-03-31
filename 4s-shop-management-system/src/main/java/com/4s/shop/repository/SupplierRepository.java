
package com.4s.shop.repository;

import com.4s.shop.entity.Supplier;
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
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    
    Optional<Supplier> findBySupplierCode(String supplierCode);
    
    List<Supplier> findByStatus(Supplier.StatusEnum status);
    
    @Query("SELECT s FROM Supplier s WHERE s.name LIKE %:name% OR s.contactPerson LIKE %:name% OR s.phone LIKE %:name%")
    Page<Supplier> findByNameContaining(@Param("name") String name, Pageable pageable);
    
    @Query("SELECT s FROM Supplier s WHERE s.rating >= :minRating AND s.status = :status")
    List<Supplier> findByRatingGreaterThanOrEqualAndStatus(@Param("minRating") BigDecimal minRating, 
                                                          @Param("status") Supplier.StatusEnum status);
    
    @Query("SELECT COUNT(s) > 0 FROM Supplier s WHERE s.supplierCode = :supplierCode AND s.id != :id")
    boolean existsBySupplierCodeAndIdNot(@Param("supplierCode") String supplierCode, @Param("id") Long id);
    
    @Query("SELECT s FROM Supplier s WHERE s.status = :status ORDER BY s.rating DESC")
    List<Supplier> findByStatusOrderByRatingDesc(@Param("status") Supplier.StatusEnum status);
}
