package com.enigmacamp.mastermenu.service.impl;

import com.enigmacamp.mastermenu.model.entity.Customer;
import com.enigmacamp.mastermenu.repository.CustomerRepository;
import com.enigmacamp.mastermenu.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(String id) {
        return customerRepository.findCustomerByDeletedFalse(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomer();
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        if(customerRepository.findCustomerByDeletedFalse(customer.getId()) != null){
            return customerRepository.save(customer);
        }else{
            throw new RuntimeException("Customer with id "+customer.getId()+" Not Found");
        }
    }

    @Override
    public void deleteCustomer(String id) {
        if(customerRepository.findCustomerByDeletedFalse(id) != null){
            customerRepository.deleteById(id);
        }else{
            throw new RuntimeException("Customer with id "+id+" Not Found");
        }

    }
}
