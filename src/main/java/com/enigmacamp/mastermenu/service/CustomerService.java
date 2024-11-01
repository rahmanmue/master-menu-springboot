package com.enigmacamp.mastermenu.service;

import com.enigmacamp.mastermenu.model.dto.request.CustomerReq;
import com.enigmacamp.mastermenu.model.dto.response.CustomerDetailRes;
import com.enigmacamp.mastermenu.model.dto.response.CustomerRes;

import java.util.List;

public interface CustomerService {
    CustomerRes saveCustomer(CustomerReq customerReq);
    CustomerDetailRes getCustomerById(String id);
    List<CustomerDetailRes> getAllCustomers();
    List<CustomerDetailRes> getAllCustomersByName(String name);
    CustomerRes updateCustomer(CustomerReq customerReq);
    void deleteCustomer(String id);
}
