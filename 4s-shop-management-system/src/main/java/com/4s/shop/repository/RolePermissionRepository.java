
package com.4s.shop.repository;

import com.4s.shop.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
    
    List<RolePermission> findByRoleId(Long roleId);
    
    List<RolePermission> findByPermissionId(Long permissionId);
    
    @Query("SELECT rp FROM RolePermission rp WHERE rp.role.id = :roleId AND rp.permission.id = :permissionId")
    Optional<RolePermission> findByRoleIdAndPermissionId(@Param("roleId") Long roleId, 
                                                        @Param("permissionId") Long permissionId);
    
    @Query("SELECT COUNT(rp) > 0 FROM RolePermission rp WHERE rp.role.id = :roleId AND rp.permission.id = :permissionId AND rp.id != :id")
    boolean existsByRoleIdAndPermissionIdAndIdNot(@Param("roleId") Long roleId, 
                                                 @Param("permissionId") Long permissionId, 
                                                 @Param("id") Long id);
    
    @Query("SELECT rp FROM RolePermission rp WHERE rp.role.id = :roleId")
    List<RolePermission> findByRoleIdWithPermissions(@Param("roleId") Long roleId);
    
    @Query("SELECT rp FROM RolePermission rp WHERE rp.permission.id = :permissionId")
    List<RolePermission> findByPermissionIdWithRoles(@Param("permissionId") Long permissionId);
}
