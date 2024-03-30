package com.enigmacamp.mastermenu.service;

import com.enigmacamp.mastermenu.model.entity.Employee;

import java.util.List;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);
    Employee updateEmployee(Employee employee);
    List<Employee> getAllEmployee();
    Employee getEmployeeById(String id);
    Employee getEmployeeByNip(String nip);
    void deleteEmployee(String id);
    List<Employee> getEmployeeByPosition(String position);

}
