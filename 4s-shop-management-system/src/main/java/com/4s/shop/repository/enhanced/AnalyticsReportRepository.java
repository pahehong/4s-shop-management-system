
package com.4s.shop.repository.enhanced;

import com.4s.shop.entity.enhanced.AnalyticsReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnalyticsReportRepository extends JpaRepository<AnalyticsReport, Long> {
    
    List<AnalyticsReport> findByReportType(AnalyticsReport.ReportTypeEnum reportType);
    
    List<AnalyticsReport> findByStatus(AnalyticsReport.StatusEnum status);
    
    @Query("SELECT ar FROM AnalyticsReport ar WHERE ar.reportType = :reportType AND ar.status = :status")
    List<AnalyticsReport> findByReportTypeAndStatus(
            @Param("reportType") AnalyticsReport.ReportTypeEnum reportType,
            @Param("status") AnalyticsReport.StatusEnum status);
    
    @Query("SELECT ar FROM AnalyticsReport ar WHERE ar.generatedBy.id = :userId AND ar.generatedAt BETWEEN :startDate AND :endDate ORDER BY ar.generatedAt DESC")
    List<AnalyticsReport> findByGeneratedByAndGeneratedAtBetweenOrderByGeneratedAtDesc(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT ar FROM AnalyticsReport ar WHERE ar.periodStart >= :periodStart AND ar.periodEnd <= :periodEnd")
    List<AnalyticsReport> findByPeriodBetween(@Param("periodStart") LocalDate periodStart, 
                                             @Param("periodEnd") LocalDate periodEnd);
    
    @Query("SELECT ar FROM AnalyticsReport ar WHERE ar.reportType = :reportType ORDER BY ar.generatedAt DESC")
    Page<AnalyticsReport> findByReportTypeOrderByGeneratedAtDesc(
            @Param("reportType") AnalyticsReport.ReportTypeEnum reportType, 
            Pageable pageable);
    
    @Query("SELECT ar FROM AnalyticsReport ar WHERE ar.reportName LIKE %:keyword% OR ar.reportType = :reportType")
    Page<AnalyticsReport> findByKeywordContainingOrReportType(
            @Param("keyword") String keyword,
            @Param("reportType") AnalyticsReport.ReportTypeEnum reportType, 
            Pageable pageable);
}
