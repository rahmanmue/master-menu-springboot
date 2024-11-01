package com.enigmacamp.mastermenu.controller;

import com.enigmacamp.mastermenu.model.dto.ApiResponse;
import com.enigmacamp.mastermenu.model.dto.request.EmployeeReq;
import com.enigmacamp.mastermenu.model.dto.response.EmployeeDetailRes;
import com.enigmacamp.mastermenu.model.dto.response.EmployeeRes;
import com.enigmacamp.mastermenu.service.EmployeeService;
import com.enigmacamp.mastermenu.utils.constant.ApiPathConstant;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPathConstant.API+ ApiPathConstant.VERSION + ApiPathConstant.EMPLOYEE)
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeDetailRes>>> getAllEmployee() {
        return new ResponseEntity<>(
            ApiResponse.<List<EmployeeDetailRes>>builder()
            .statusCode(HttpStatus.OK.value())
            .message("Success")
            .data(employeeService.getAllEmployee())
            .build(),
            HttpStatus.OK
        );
    }

    @GetMapping("/nip/{nip}")
    public ResponseEntity<ApiResponse<EmployeeDetailRes>> getEmployeeByNip(@PathVariable String nip) {
        EmployeeDetailRes employee = employeeService.getEmployeeByNip(nip);

        return new ResponseEntity<>(
            ApiResponse.<EmployeeDetailRes>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Employee retrieved by nip successfully")
                .data(employee) 
                .build(),
            HttpStatus.OK
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<EmployeeDetailRes>>> getEmployeeByPosition(@RequestParam(name = "position") String position) {
        List<EmployeeDetailRes> employees = employeeService.getEmployeeByPosition(position);
        
        return new ResponseEntity<>(
            ApiResponse.<List<EmployeeDetailRes>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Employees retrieved by position successfully")
                .data(employees) 
                .build(),
            HttpStatus.OK
        );
    }

    @GetMapping("/search/employee")
    public ResponseEntity<ApiResponse<List<EmployeeDetailRes>>> getAllEmployeeByName(@RequestParam("name") String name) {
        List<EmployeeDetailRes> employees = employeeService.getEmployeeByName(name);
        
        return new ResponseEntity<>(
            ApiResponse.<List<EmployeeDetailRes>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Employees retrieved by name successfully")
                .data(employees) 
                .build(),
            HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDetailRes>> getEmployeeById(@PathVariable String id) {
        EmployeeDetailRes employee = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(
            ApiResponse.<EmployeeDetailRes>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Employee retrieved by id successfully")
                .data(employee) 
                .build(),
            HttpStatus.OK
        );
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<EmployeeRes>> saveEmployee(@Valid @RequestBody EmployeeReq employeeReq) {
        EmployeeRes savedEmployee = employeeService.saveEmployee(employeeReq);
        
        return new ResponseEntity<>(
            ApiResponse.<EmployeeRes>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Employee created successfully")
                .data(savedEmployee)
                .build(),
            HttpStatus.CREATED
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<EmployeeRes>> updateEmployee(@Valid @RequestBody EmployeeReq employee) {
        EmployeeRes updatedEmployee = employeeService.updateEmployee(employee);
        return new ResponseEntity<>(
            ApiResponse.<EmployeeRes>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Employee updated successfully")
                .data(updatedEmployee)
                .build(),
            HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);

        return new ResponseEntity<>(
            ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Employee deleted sucessfully")
                .data("Employee with id "+ id + " has been deleted")
                .build(),
            HttpStatus.OK
        );
    }

}
