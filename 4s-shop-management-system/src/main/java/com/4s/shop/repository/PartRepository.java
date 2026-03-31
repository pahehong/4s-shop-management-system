
package com.4s.shop.repository;

import com.4s.shop.entity.Part;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
    
    Optional<Part> findByPartCode(String partCode);
    
    List<Part> findByCategory_Id(Long categoryId);
    
    List<Part> findBySupplier_Id(Long supplierId);
    
    List<Part> findByStatus(Part.StatusEnum status);
    
    @Query("SELECT p FROM Part p WHERE p.name LIKE %:keyword% OR p.partCode LIKE %:keyword% OR p.oeNumber LIKE %:keyword%")
    Page<Part> findByNameOrCodeContaining(@Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT p FROM Part p WHERE p.category.id = :categoryId AND p.status = :status")
    List<Part> findByCategoryIdAndStatus(@Param("categoryId") Long categoryId, 
                                        @Param("status") Part.StatusEnum status);
    
    @Query("SELECT p FROM Part p WHERE p.supplier.id = :supplierId AND p.status = :status")
    List<Part> findBySupplierIdAndStatus(@Param("supplierId") Long supplierId, 
                                        @Param("status") Part.StatusEnum status);
    
    @Query("SELECT COUNT(p) > 0 FROM Part p WHERE p.partCode = :partCode AND p.id != :id")
    boolean existsByPartCodeAndIdNot(@Param("partCode") String partCode, @Param("id") Long id);
    
    @Query("SELECT p FROM Part p WHERE p.name LIKE %:name% AND p.status = :status")
    List<Part> findByNameContainingAndStatus(@Param("name") String name, 
                                            @Param("status") Part.StatusEnum status);
}
