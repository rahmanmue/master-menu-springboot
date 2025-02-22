package com.enigmacamp.mastermenu.serviceTests;

import com.enigmacamp.mastermenu.model.dtos.customer.CustomerDetailRes;
import com.enigmacamp.mastermenu.model.dtos.customer.CustomerReq;
import com.enigmacamp.mastermenu.model.dtos.customer.CustomerRes;
import com.enigmacamp.mastermenu.model.entity.Customer;
import com.enigmacamp.mastermenu.repository.CustomerRepository;
import com.enigmacamp.mastermenu.service.impl.CustomerImpl;
import com.enigmacamp.mastermenu.utils.enums.EGender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import jakarta.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ModelMapper modelMapper;

    @InjectMocks
    private CustomerImpl customerService;

    private Customer customer;
    private Customer updatedCustomer;
    private CustomerReq customerReq;
    private CustomerReq updatedCustomerReq;
    private CustomerRes customerRes;
    private CustomerDetailRes customerDetailRes;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId("1");
        customer.setFullName("John Doe");
        customer.setDateOfBirth(new Date());
        customer.setAddress("123 Main St");
        customer.setPhone("1234567890");
        customer.setGender(EGender.MALE);

        customerReq = new CustomerReq();
        customerReq.setId("1");
        customerReq.setFullName("John Doe");
        customerReq.setDateOfBirth(new Date());
        customerReq.setAddress("123 Main St");
        customerReq.setPhone("1234567890");
        customerReq.setGender(EGender.MALE);

        customerRes = new CustomerRes();
        customerRes.setId("1");

        customerDetailRes = new CustomerDetailRes();
        customerDetailRes.setId("1");
        customerDetailRes.setFullName("John Doe");
        customerDetailRes.setDateOfBirth(new Date());
        customerDetailRes.setAddress("123 Main St");
        customerDetailRes.setPhone("1234567890");
        customerDetailRes.setGender(EGender.MALE);

        updatedCustomerReq = new CustomerReq();
        updatedCustomerReq.setFullName("John Doe Updated");
        updatedCustomerReq.setDateOfBirth(new Date());
        updatedCustomerReq.setAddress("123 Main St");
        updatedCustomerReq.setPhone("1234567890");
        updatedCustomerReq.setGender(EGender.MALE);

        updatedCustomer = new Customer();
        updatedCustomer.setId("1");
        updatedCustomer.setFullName("John Doe Updated");
        updatedCustomer.setDateOfBirth(new Date());
        updatedCustomer.setAddress("123 Main St");
        updatedCustomer.setPhone("1234567890");
        updatedCustomer.setGender(EGender.MALE);
    }

    @Test
    void saveCustomer_ShouldSaveAndReturnCustomerRes() {
        when(modelMapper.map(customerReq, Customer.class)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(modelMapper.map(customer, CustomerRes.class)).thenReturn(customerRes);

        CustomerRes result = customerService.saveCustomer(customerReq);

        assertNotNull(result);
        assertEquals("1", result.getId());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void getCustomerById_ShouldReturnCustomerDetailRes_WhenCustomerExists() {
        when(customerRepository.findCustomerByDeletedFalse("1")).thenReturn(customer);
        when(modelMapper.map(customer, CustomerDetailRes.class)).thenReturn(customerDetailRes);

        CustomerDetailRes result = customerService.getCustomerById("1");

        assertNotNull(result);
        assertEquals("1", result.getId());
        verify(customerRepository, times(1)).findCustomerByDeletedFalse("1");
    }

    @Test
    void getCustomerById_ShouldThrowException_WhenCustomerDoesNotExist() {
        when(customerRepository.findCustomerByDeletedFalse("1")).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> customerService.getCustomerById("1"));
        verify(customerRepository, times(1)).findCustomerByDeletedFalse("1");
    }

    @Test
    void getAllCustomers_ShouldReturnListOfCustomerDetailRes() {
        when(customerRepository.getAllCustomer()).thenReturn(Collections.singletonList(customer));
        when(modelMapper.map(customer, CustomerDetailRes.class)).thenReturn(customerDetailRes);

        List<CustomerDetailRes> result = customerService.getAllCustomers();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(customerRepository, times(1)).getAllCustomer();
    }

    @Test
    void getAllCustomersByName_ShouldReturnListOfCustomerDetailRes_WhenCustomersExist() {
        when(customerRepository.getAllCustomerByName("John")).thenReturn(Collections.singletonList(customer));
        when(modelMapper.map(customer, CustomerDetailRes.class)).thenReturn(customerDetailRes);

        List<CustomerDetailRes> result = customerService.getAllCustomersByName("John");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(customerRepository, times(1)).getAllCustomerByName("John");
    }

    @Test
    void getAllCustomersByName_ShouldThrowException_WhenNoCustomersFound() {
        when(customerRepository.getAllCustomerByName("Unknown")).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> customerService.getAllCustomersByName("Unknown"));
        verify(customerRepository, times(1)).getAllCustomerByName("Unknown");
    }

    @Test
    void updateCustomer_ShouldUpdateAndReturnCustomerRes() {
        String id = "1";
        when(customerRepository.findCustomerByDeletedFalse(id)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(updatedCustomer);
        doAnswer(invocation -> {
            CustomerReq req = invocation.getArgument(0);
            Customer target = invocation.getArgument(1);
            target.setFullName(req.getFullName());
            target.setDateOfBirth(req.getDateOfBirth());
            target.setAddress(req.getAddress());
            target.setPhone(req.getPhone());
            target.setGender(req.getGender());
            return null;
        }).when(modelMapper).map(any(CustomerReq.class), any(Customer.class));
        when(modelMapper.map(eq(updatedCustomer), eq(CustomerRes.class))).thenReturn(customerRes);
        
        CustomerRes result = customerService.updateCustomer(id, updatedCustomerReq);

        assertNotNull(result);
        assertEquals("1", result.getId());
        verify(customerRepository, times(1)).save(customer);
        verify(modelMapper).map(updatedCustomerReq, customer);
        verify(modelMapper).map(updatedCustomer, CustomerRes.class);
    }

    @Test
    void deleteCustomer_ShouldDeleteCustomer_WhenCustomerExists() {
        when(customerRepository.findCustomerByDeletedFalse("1")).thenReturn(customer);

        customerService.deleteCustomer("1");

        verify(customerRepository, times(1)).deleteById("1");
    }

    @Test
    void deleteCustomer_ShouldThrowException_WhenCustomerDoesNotExist() {
        when(customerRepository.findCustomerByDeletedFalse("1")).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> customerService.deleteCustomer("1"));
        verify(customerRepository, times(1)).findCustomerByDeletedFalse("1");
    }
}

