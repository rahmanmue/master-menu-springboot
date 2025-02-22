package com.enigmacamp.mastermenu.controller;

import com.enigmacamp.mastermenu.model.dtos.ApiResponse;
import com.enigmacamp.mastermenu.model.dtos.customer.CustomerDetailRes;
import com.enigmacamp.mastermenu.model.dtos.customer.CustomerReq;
import com.enigmacamp.mastermenu.model.dtos.customer.CustomerRes;
import com.enigmacamp.mastermenu.service.CustomerService;
import com.enigmacamp.mastermenu.utils.constant.ApiPathConstant;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPathConstant.API+ ApiPathConstant.VERSION + ApiPathConstant.CUSTOMER)
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerDetailRes>>> getAllCustomer() {
        List<CustomerDetailRes> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(
            ApiResponse.<List<CustomerDetailRes>>builder()
            .statusCode(HttpStatus.OK.value())
            .message("Success")
            .data(customers)
            .build(),
            HttpStatus.OK
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CustomerDetailRes>>> getAllCustomerByName(@RequestParam("name") String name) {
        List<CustomerDetailRes> customers = customerService.getAllCustomersByName(name);
        return new ResponseEntity<>(
            ApiResponse.<List<CustomerDetailRes>>builder()
            .statusCode(HttpStatus.OK.value())
            .message("Success")
            .data(customers)
            .build(),
            HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerDetailRes>> getCustomerById(@PathVariable String id) {
        CustomerDetailRes customer = customerService.getCustomerById(id);

        return new ResponseEntity<>(
            ApiResponse.<CustomerDetailRes>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Customer retrieved successfully")
                .data(customer) 
                .build(),
            HttpStatus.OK
        );
    }

    // Not Use Only Use on Registered but use service not controller
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CustomerRes>> createCustomer(@Valid @RequestBody CustomerReq customerReq) {
        CustomerRes savedCustumer =  customerService.saveCustomer(customerReq); 
        
        return new ResponseEntity<>(
            ApiResponse.<CustomerRes>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Customer created successfully")
                .data(savedCustumer)
                .build(),
            HttpStatus.CREATED
        );
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<CustomerRes>> updateCustomer(@PathVariable String id, @Valid @RequestBody CustomerReq customerReq) {
        CustomerRes updatedCustomer = customerService.updateCustomer(id, customerReq);
        return new ResponseEntity<>(
            ApiResponse.<CustomerRes>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Customer updated sucessfully")
                .data(updatedCustomer)
                .build(),
            HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);

        return new ResponseEntity<>(
            ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Customer deleted sucessfully")
                .data("Customer with id "+ id + " has been deleted")
                .build(),
            HttpStatus.OK
        );
    }


}
