
package com.4s.shop.repository;

import com.4s.shop.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    
    Optional<Permission> findByName(String name);
    
    List<Permission> findByResource(String resource);
    
    List<Permission> findByAction(String action);
    
    @Query("SELECT p FROM Permission p WHERE p.resource = :resource AND p.action = :action")
    Optional<Permission> findByResourceAndAction(@Param("resource") String resource, 
                                                @Param("action") String action);
    
    @Query("SELECT p FROM Permission p WHERE p.name LIKE %:name%")
    List<Permission> findByNameContaining(@Param("name") String name);
    
    @Query("SELECT COUNT(p) > 0 FROM Permission p WHERE p.name = :name AND p.id != :id")
    boolean existsByNameAndIdNot(@Param("name") String name, @Param("id") Long id);
    
    @Query("SELECT p FROM Permission p WHERE p.resource LIKE %:resource% AND p.action LIKE %:action%")
    List<Permission> findByResourceAndActionContaining(@Param("resource") String resource, 
                                                      @Param("action") String action);
}
