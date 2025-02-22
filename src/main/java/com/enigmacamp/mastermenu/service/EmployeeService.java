package com.enigmacamp.mastermenu.service;

import com.enigmacamp.mastermenu.model.dtos.employee.EmployeeDetailRes;
import com.enigmacamp.mastermenu.model.dtos.employee.EmployeeReq;
import com.enigmacamp.mastermenu.model.dtos.employee.EmployeeRes;

import java.util.List;

public interface EmployeeService {

    EmployeeRes saveEmployee(EmployeeReq employeeReq);
    EmployeeRes updateEmployee(String id, EmployeeReq employeeReq);
    List<EmployeeDetailRes> getAllEmployee();
    EmployeeDetailRes getEmployeeById(String id);
    EmployeeDetailRes getEmployeeByNip(String nip);
    void deleteEmployee(String id);
    List<EmployeeDetailRes> getEmployeeByPosition(String position);
    List<EmployeeDetailRes> getEmployeeByName(String name);

}
