package com.enigmacamp.mastermenu.service;

import com.enigmacamp.mastermenu.model.dto.request.EmployeeReq;
import com.enigmacamp.mastermenu.model.dto.response.EmployeeDetailRes;
import com.enigmacamp.mastermenu.model.dto.response.EmployeeRes;

import java.util.List;

public interface EmployeeService {

    EmployeeRes saveEmployee(EmployeeReq employeeReq);
    EmployeeRes updateEmployee(EmployeeReq employeeReq);
    List<EmployeeDetailRes> getAllEmployee();
    EmployeeDetailRes getEmployeeById(String id);
    EmployeeDetailRes getEmployeeByNip(String nip);
    void deleteEmployee(String id);
    List<EmployeeDetailRes> getEmployeeByPosition(String position);
    List<EmployeeDetailRes> getEmployeeByName(String name);

}
