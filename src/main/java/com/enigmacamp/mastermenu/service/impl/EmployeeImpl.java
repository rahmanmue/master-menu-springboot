package com.enigmacamp.mastermenu.service.impl;

import com.enigmacamp.mastermenu.model.dto.request.EmployeeReq;
import com.enigmacamp.mastermenu.model.dto.response.EmployeeDetailRes;
import com.enigmacamp.mastermenu.model.dto.response.EmployeeRes;
import com.enigmacamp.mastermenu.model.entity.Employee;
import com.enigmacamp.mastermenu.repository.EmployeeRepository;
import com.enigmacamp.mastermenu.service.EmployeeService;
import com.enigmacamp.mastermenu.utils.enums.EPosition;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public EmployeeRes saveEmployee(EmployeeReq employeeReq) {
        Employee employee = modelMapper.map(employeeReq, Employee.class);
        Employee saved = employeeRepository.save(employee);
        return modelMapper.map(saved, EmployeeRes.class);
    }

    @Override
    public EmployeeRes updateEmployee(EmployeeReq employeeReq) {
        Employee existingEmployee = employeeRepository.findEmployeeByDeletedFalse(employeeReq.getId());

        if( existingEmployee == null){
            throw new RuntimeException("Employee Not Found");
        }

        modelMapper.map(employeeReq, existingEmployee);

        return modelMapper.map(existingEmployee, EmployeeRes.class);
    }

    @Override
    public List<EmployeeDetailRes> getAllEmployee() {
        List<Employee> employees = employeeRepository.getAllEmployee();
        return employees.stream()
            .map(employee -> modelMapper.map(employee, EmployeeDetailRes.class))
            .toList();
    }

    @Override
    public List<EmployeeDetailRes> getEmployeeByName(String name) {
        List<Employee> employees = employeeRepository.getAllEmployeeByName(name);
        return employees.stream()
            .map(employee -> modelMapper.map(employee, EmployeeDetailRes.class))
            .toList();
    }

    @Override
    public EmployeeDetailRes getEmployeeById(String id) {
        Employee employee = employeeRepository.findEmployeeByDeletedFalse(id);

        if(employee == null){
            throw new EntityNotFoundException("Employee Not Found");
        }

        return modelMapper.map(employee, EmployeeDetailRes.class);
    }

    @Override
    public EmployeeDetailRes getEmployeeByNip(String nip) {
        Employee employee = employeeRepository.getEmployeeByNip(nip);

        if(employee == null){
            throw new EntityNotFoundException("Employee Not Found");
        }

        return modelMapper.map(employee, EmployeeDetailRes.class);
    }

    @Override
    public void deleteEmployee(String id) {
        if (employeeRepository.findEmployeeByDeletedFalse(id) == null) {
            throw new EntityNotFoundException("Employee Not Found");
        } 

        employeeRepository.deleteById(id);
    }

    @Override
    public List<EmployeeDetailRes> getEmployeeByPosition(String position) {
        EPosition ePosition = EPosition.valueOf(position.toUpperCase());
        return employeeRepository.getEmployeeByPosition(ePosition).stream()
            .map(employee -> modelMapper.map(employee, EmployeeDetailRes.class))
            .toList();
    }

   
}
