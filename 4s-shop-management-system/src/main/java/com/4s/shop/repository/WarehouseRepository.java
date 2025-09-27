
package com.4s.shop.repository;

import com.4s.shop.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    
    Optional<Warehouse> findByName(String name);
    
    List<Warehouse> findByStatus(Warehouse.StatusEnum status);
    
    List<Warehouse> findByDepartmentId(Long departmentId);
    
    @Query("SELECT w FROM Warehouse w WHERE w.department.id = :departmentId AND w.status = :status")
    List<Warehouse> findByDepartmentIdAndStatus(@Param("departmentId") Long departmentId, 
                                               @Param("status") Warehouse.StatusEnum status);
    
    @Query("SELECT COUNT(w) > 0 FROM Warehouse w WHERE w.name = :name AND w.id != :id")
    boolean existsByNameAndIdNot(@Param("name") String name, @Param("id") Long id);
    
    @Query("SELECT w FROM Warehouse w WHERE w.manager.id = :managerId")
    List<Warehouse> findByManagerId(@Param("managerId") Long managerId);
}
