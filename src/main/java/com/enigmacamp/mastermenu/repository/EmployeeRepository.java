package com.enigmacamp.mastermenu.repository;

import com.enigmacamp.mastermenu.model.entity.Employee;
import com.enigmacamp.mastermenu.utils.enums.EPosition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    @Query("Select e from Employee e where e.deleted = false")
    List<Employee> getAllEmployee();

    @Query("Select e from Employee e where e.deleted = false and e.id = :employee_id")
    Employee findEmployeeByDeletedFalse(@Param("employee_id") String employee_id);

    @Query("Select e from Employee e where e.deleted = false and e.nip = :nip")
    Employee getEmployeeByNip(@Param("nip") String nip);

    @Query("Select e from Employee e where e.deleted = false and e.position = :position")
    List<Employee> getEmployeeByPosition(@Param("position") EPosition position);

    @Query("Select e from Employee e where e.deleted = false and LOWER(e.fullName) LIKE LOWER(CONCAT('%',:name,'%'))")
    List<Employee> getAllEmployeeByName(@Param("name") String name);

}
