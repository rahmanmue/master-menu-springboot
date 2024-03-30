package com.enigmacamp.mastermenu.controller;

import com.enigmacamp.mastermenu.model.entity.Employee;
import com.enigmacamp.mastermenu.service.EmployeeService;
import com.enigmacamp.mastermenu.utils.constant.ApiPathConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPathConstant.API+ ApiPathConstant.VERSION + ApiPathConstant.EMPLOYEE)
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployee() {
        return employeeService.getAllEmployee();
    }

    @GetMapping("/nip/{nip}")
    public Employee getEmployeeByNip(@PathVariable String nip) {
        return employeeService.getEmployeeByNip(nip);
    }

    @GetMapping("by-position")
    public List<Employee> getEmployeeByPosition(@RequestParam(name = "position") String position) {
        return employeeService.getEmployeeByPosition(position);
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable String id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping()
    public Employee saveEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @PutMapping
    public Employee updateEmployee(@RequestBody Employee employee) {
        return employeeService.updateEmployee(employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
    }

}
