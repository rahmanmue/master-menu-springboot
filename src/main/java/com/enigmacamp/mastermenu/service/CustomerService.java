package com.enigmacamp.mastermenu.service;

import com.enigmacamp.mastermenu.model.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    Customer getCustomerById(String id);
    List<Customer> getAllCustomers();
    Customer updateCustomer(Customer customer);
    void deleteCustomer(String id);
}
