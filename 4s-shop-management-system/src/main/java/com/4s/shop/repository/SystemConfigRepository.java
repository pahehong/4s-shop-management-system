
package com.4s.shop.repository;

import com.4s.shop.entity.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfig, Long> {
    
    Optional<SystemConfig> findByConfigKey(String configKey);
    
    @Query("SELECT COUNT(sc) > 0 FROM SystemConfig sc WHERE sc.configKey = :configKey AND sc.id != :id")
    boolean existsByConfigKeyAndIdNot(@Param("configKey") String configKey, @Param("id") Long id);
    
    @Query("SELECT sc FROM SystemConfig sc WHERE sc.configKey LIKE %:keyword% OR sc.description LIKE %:keyword%")
    java.util.List<SystemConfig> findByKeywordContaining(@Param("keyword") String keyword);
}
