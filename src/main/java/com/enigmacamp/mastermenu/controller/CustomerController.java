package com.enigmacamp.mastermenu.controller;

import com.enigmacamp.mastermenu.model.entity.Customer;
import com.enigmacamp.mastermenu.service.CustomerService;
import com.enigmacamp.mastermenu.utils.constant.ApiPathConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPathConstant.API+ ApiPathConstant.VERSION + ApiPathConstant.CUSTOMER)
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomer() {
        return customerService.getAllCustomers();
    }

    @GetMapping(value = "/{id}")
    public Customer getCustomerById(@PathVariable String id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @PutMapping
    public Customer updateCustomer(@RequestBody Customer customer) {
        return customerService.updateCustomer(customer);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
    }


}
