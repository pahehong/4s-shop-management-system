
package com.4s.shop.repository;

import com.4s.shop.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
    
    Optional<ServiceType> findByName(String name);
    
    List<ServiceType> findByStatus(ServiceType.StatusEnum status);
    
    List<ServiceType> findByCategory(String category);
    
    @Query("SELECT st FROM ServiceType st WHERE st.name LIKE %:name% OR st.category LIKE %:name%")
    List<ServiceType> findByNameContaining(@Param("name") String name);
    
    @Query("SELECT COUNT(st) > 0 FROM ServiceType st WHERE st.name = :name AND st.id != :id")
    boolean existsByNameAndIdNot(@Param("name") String name, @Param("id") Long id);
    
    @Query("SELECT st FROM ServiceType st WHERE st.status = :status ORDER BY st.basePrice ASC")
    List<ServiceType> findByStatusOrderByBasePrice(@Param("status") ServiceType.StatusEnum status);
}
