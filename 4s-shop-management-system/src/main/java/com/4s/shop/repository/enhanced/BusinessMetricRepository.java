
package com.4s.shop.repository.enhanced;

import com.4s.shop.entity.enhanced.BusinessMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BusinessMetricRepository extends JpaRepository<BusinessMetric, Long> {
    
    List<BusinessMetric> findByMetricType(BusinessMetric.MetricTypeEnum metricType);
    
    List<BusinessMetric> findByPeriodStartBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT bm FROM BusinessMetric bm WHERE bm.metricType = :metricType AND bm.periodStart >= :periodStart AND bm.periodEnd <= :periodEnd ORDER BY bm.periodStart")
    List<BusinessMetric> findByMetricTypeAndPeriodBetween(
            @Param("metricType") BusinessMetric.MetricTypeEnum metricType,
            @Param("periodStart") LocalDate periodStart,
            @Param("periodEnd") LocalDate periodEnd);
    
    @Query("SELECT AVG(bm.metricValue) FROM BusinessMetric bm WHERE bm.metricType = :metricType AND bm.periodStart >= :periodStart AND bm.periodEnd <= :periodEnd")
    Double findAverageByMetricTypeAndPeriodBetween(
            @Param("metricType") BusinessMetric.MetricTypeEnum metricType,
            @Param("periodStart") LocalDate periodStart,
            @Param("periodEnd") LocalDate periodEnd);
    
    @Query("SELECT bm FROM BusinessMetric bm WHERE bm.metricName LIKE %:metricName%")
    List<BusinessMetric> findByMetricNameContaining(@Param("metricName") String metricName);
}
