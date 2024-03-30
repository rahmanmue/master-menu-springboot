package com.enigmacamp.mastermenu.service.impl;

import com.enigmacamp.mastermenu.model.entity.Employee;
import com.enigmacamp.mastermenu.repository.EmployeeRepository;
import com.enigmacamp.mastermenu.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        if(employeeRepository.findEmployeeByDeletedFalse(employee.getId()) != null){
            return employeeRepository.save(employee);
        }else {
            throw new RuntimeException("Employee Not Found");
        }
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.getAllEmployee();
    }

    @Override
    public Employee getEmployeeById(String id) {
        return employeeRepository.findEmployeeByDeletedFalse(id);
    }

    @Override
    public Employee getEmployeeByNip(String nip) {
        return employeeRepository.getEmployeeByNip(nip);
    }

    @Override
    public void deleteEmployee(String id) {
        if (employeeRepository.findEmployeeByDeletedFalse(id) != null) {
             employeeRepository.deleteById(id);
        } else {
            throw new RuntimeException("Employee Not Found");
        }
    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        List<Employee> employeeListByPosition = employeeRepository.getAllEmployee()
                .stream().filter(employee -> employee.getPosition().equalsIgnoreCase(position))
                .toList();

        return employeeListByPosition;
    }
}
