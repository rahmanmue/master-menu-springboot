package com.enigmacamp.mastermenu.service;

import com.enigmacamp.mastermenu.model.dtos.customer.CustomerDetailRes;
import com.enigmacamp.mastermenu.model.dtos.customer.CustomerReq;
import com.enigmacamp.mastermenu.model.dtos.customer.CustomerRes;

import java.util.List;

public interface CustomerService {
    CustomerRes saveCustomer(CustomerReq customerReq);
    CustomerDetailRes getCustomerById(String id);
    List<CustomerDetailRes> getAllCustomers();
    List<CustomerDetailRes> getAllCustomersByName(String name);
    CustomerRes updateCustomer(String id, CustomerReq customerReq);
    void deleteCustomer(String id);
}
