
package com.4s.shop.repository;

import com.4s.shop.entity.OperationLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OperationLogRepository extends JpaRepository<OperationLog, Long> {
    
    List<OperationLog> findByUserId(Long userId);
    
    List<OperationLog> findByResourceType(String resourceType);
    
    @Query("SELECT ol FROM OperationLog ol WHERE ol.user.id = :userId AND ol.createdAt BETWEEN :startDate AND :endDate")
    List<OperationLog> findByUserIdAndCreatedAtBetween(@Param("userId") Long userId, 
                                                      @Param("startDate") LocalDateTime startDate, 
                                                      @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT ol FROM OperationLog ol WHERE ol.resourceType = :resourceType AND ol.resourceId = :resourceId")
    List<OperationLog> findByResourceTypeAndResourceId(@Param("resourceType") String resourceType, 
                                                      @Param("resourceId") Long resourceId);
    
    @Query("SELECT ol FROM OperationLog ol WHERE ol.operation LIKE %:operation%")
    Page<OperationLog> findByOperationContaining(@Param("operation") String operation, Pageable pageable);
    
    @Query("SELECT ol FROM OperationLog ol WHERE ol.user.id = :userId AND ol.operation LIKE %:operation%")
    Page<OperationLog> findByUserIdAndOperationContaining(@Param("userId") Long userId, 
                                                         @Param("operation") String operation, 
                                                         Pageable pageable);
    
    @Query("SELECT ol FROM OperationLog ol WHERE ol.createdAt BETWEEN :startDate AND :endDate")
    List<OperationLog> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, 
                                             @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT ol FROM OperationLog ol WHERE ol.user.id = :userId AND ol.createdAt BETWEEN :startDate AND :endDate ORDER BY ol.createdAt DESC")
    Page<OperationLog> findByUserIdAndCreatedAtBetweenOrderByCreatedAtDesc(@Param("userId") Long userId, 
                                                                          @Param("startDate") LocalDateTime startDate, 
                                                                          @Param("endDate") LocalDateTime endDate, 
                                                                          Pageable pageable);
}
