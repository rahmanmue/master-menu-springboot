package com.enigmacamp.mastermenu.service.impl;

import com.enigmacamp.mastermenu.model.dto.request.CustomerReq;
import com.enigmacamp.mastermenu.model.dto.response.CustomerDetailRes;
import com.enigmacamp.mastermenu.model.dto.response.CustomerRes;
import com.enigmacamp.mastermenu.model.entity.Customer;
import com.enigmacamp.mastermenu.repository.CustomerRepository;
import com.enigmacamp.mastermenu.service.CustomerService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public CustomerRes saveCustomer(CustomerReq customerReq) {
        Customer customer = modelMapper.map(customerReq, Customer.class);
        Customer saved = customerRepository.save(customer);
        return modelMapper.map( saved, CustomerRes.class);
    }

    @Override
    public CustomerDetailRes getCustomerById(String id) {
        Customer customer = customerRepository.findCustomerByDeletedFalse(id);

        if(customer == null){
            throw new EntityNotFoundException("Customer with id " + id + " not found");
        }

        return modelMapper.map(customer, CustomerDetailRes.class);
    }

    @Override
    public List<CustomerDetailRes> getAllCustomers() {
        List<Customer> customers = customerRepository.getAllCustomer();
        return customers.stream()
            .map(customer -> modelMapper.map(customer, CustomerDetailRes.class))
            .toList();
    }


    @Override
    public List<CustomerDetailRes> getAllCustomersByName(String name) {
        List<Customer> customers = customerRepository.getAllCustomerByName(name);

        if(customers == null){
            throw new EntityNotFoundException("Customer with name "+name+" Not Found");
        }

        return customers.stream()
            .map(customer -> modelMapper.map(customer, CustomerDetailRes.class))
            .toList();
    }

    @Override
    public CustomerRes updateCustomer(CustomerReq customerReq) {
        Customer existingCustomer = customerRepository.findCustomerByDeletedFalse(customerReq.getId());

        if(existingCustomer == null){
            throw new EntityNotFoundException("Customer with id "+customerReq.getId()+" Not Found");
        }

        modelMapper.map(customerReq, existingCustomer);

        Customer updated = customerRepository.save(existingCustomer);

        return modelMapper.map(updated, CustomerRes.class);
    }

    @Override
    public void deleteCustomer(String id) {
        if(customerRepository.findCustomerByDeletedFalse(id) == null){
            throw new EntityNotFoundException("Customer with id "+id+" Not Found");
        }
        customerRepository.deleteById(id);

    }

}
