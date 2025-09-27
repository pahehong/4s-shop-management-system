
package com.4s.shop.repository;

import com.4s.shop.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    Optional<Employee> findByEmployeeCode(String employeeCode);
    
    Optional<Employee> findByPhone(String phone);
    
    List<Employee> findByDepartmentId(Long departmentId);
    
    List<Employee> findByPositionId(Long positionId);
    
    List<Employee> findByStatus(Employee.StatusEnum status);
    
    @Query("SELECT e FROM Employee e WHERE e.name LIKE %:name% OR e.phone LIKE %:name%")
    Page<Employee> findByNameOrPhoneContaining(@Param("name") String name, Pageable pageable);
    
    @Query("SELECT e FROM Employee e WHERE e.department.id = :departmentId AND e.status = :status")
    List<Employee> findByDepartmentIdAndStatus(@Param("departmentId") Long departmentId, 
                                              @Param("status") Employee.StatusEnum status);
    
    @Query("SELECT e FROM Employee e WHERE e.position.id = :positionId AND e.status = :status")
    List<Employee> findByPositionIdAndStatus(@Param("positionId") Long positionId, 
                                            @Param("status") Employee.StatusEnum status);
    
    @Query("SELECT COUNT(e) > 0 FROM Employee e WHERE e.phone = :phone AND e.id != :id")
    boolean existsByPhoneAndIdNot(@Param("phone") String phone, @Param("id") Long id);
    
    @Query("SELECT e FROM Employee e WHERE e.skillLevel = :skillLevel AND e.status = :status")
    List<Employee> findBySkillLevelAndStatus(@Param("skillLevel") String skillLevel, 
                                            @Param("status") Employee.StatusEnum status);
}
