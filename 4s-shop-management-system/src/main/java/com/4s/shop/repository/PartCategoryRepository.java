
package com.4s.shop.repository;

import com.4s.shop.entity.PartCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartCategoryRepository extends JpaRepository<PartCategory, Long> {
    
    Optional<PartCategory> findByName(String name);
    
    List<PartCategory> findByParentId(Long parentId);
    
    List<PartCategory> findByLevel(Integer level);
    
    @Query("SELECT pc FROM PartCategory pc WHERE pc.parent.id = :parentId")
    List<PartCategory> findByParentId(@Param("parentId") Long parentId);
    
    @Query("SELECT pc FROM PartCategory pc WHERE pc.parent IS NULL")
    List<PartCategory> findRootCategories();
    
    @Query("SELECT COUNT(pc) > 0 FROM PartCategory pc WHERE pc.name = :name AND pc.id != :id")
    boolean existsByNameAndIdNot(@Param("name") String name, @Param("id") Long id);
    
    @Query("SELECT pc FROM PartCategory pc WHERE pc.path LIKE %:path%")
    List<PartCategory> findByPathContaining(@Param("path") String path);
}
