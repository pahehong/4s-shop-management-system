
package com.4s.shop.repository.enhanced;

import com.4s.shop.entity.enhanced.AiModelResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AiModelResultRepository extends JpaRepository<AiModelResult, Long> {
    
    List<AiModelResult> findByModelTypeAndEntityId(String modelType, Long entityId);
    
    List<AiModelResult> findByModelId(Long modelId);
    
    @Query("SELECT amr FROM AiModelResult amr WHERE amr.modelType = :modelType AND amr.entityType = :entityType AND amr.entityId = :entityId ORDER BY amr.createdAt DESC")
    List<AiModelResult> findByModelTypeAndEntityTypeAndEntityIdOrderByCreatedAtDesc(
            @Param("modelType") String modelType,
            @Param("entityType") String entityType,
            @Param("entityId") Long entityId);
    
    @Query("SELECT amr FROM AiModelResult amr WHERE amr.modelType = :modelType AND amr.createdAt BETWEEN :startDate AND :endDate")
    List<AiModelResult> findByModelTypeAndCreatedAtBetween(
            @Param("modelType") String modelType,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT amr FROM AiModelResult amr WHERE amr.entityId = :entityId AND amr.entityType = :entityType ORDER BY amr.createdAt DESC")
    List<AiModelResult> findByEntityIdAndEntityTypeOrderByCreatedAtDesc(
            @Param("entityId") Long entityId,
            @Param("entityType") String entityType);
}
