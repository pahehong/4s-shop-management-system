
package com.4s.shop.repository.enhanced;

import com.4s.shop.entity.enhanced.AiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AiModelRepository extends JpaRepository<AiModel, Long> {
    
    Optional<AiModel> findByModelName(String modelName);
    
    List<AiModel> findByModelType(AiModel.ModelTypeEnum modelType);
    
    List<AiModel> findByStatus(AiModel.StatusEnum status);
    
    @Query("SELECT am FROM AiModel am WHERE am.modelType = :modelType AND am.status = :status")
    List<AiModel> findByModelTypeAndStatus(@Param("modelType") AiModel.ModelTypeEnum modelType, 
                                          @Param("status") AiModel.StatusEnum status);
    
    @Query("SELECT am FROM AiModel am WHERE am.isDefault = true AND am.modelType = :modelType")
    Optional<AiModel> findDefaultByModelType(@Param("modelType") AiModel.ModelTypeEnum modelType);
    
    @Query("SELECT COUNT(am) > 0 FROM AiModel am WHERE am.modelName = :modelName AND am.id != :id")
    boolean existsByModelNameAndIdNot(@Param("modelName") String modelName, @Param("id") Long id);
}
