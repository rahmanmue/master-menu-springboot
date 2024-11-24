package com.enigmacamp.mastermenu.serviceTests;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.enigmacamp.mastermenu.model.dto.request.TransactionDetailReq;
import com.enigmacamp.mastermenu.model.dto.request.TransactionReq;
import com.enigmacamp.mastermenu.model.dto.response.TransactionListRes;
import com.enigmacamp.mastermenu.model.dto.response.TransactionRes;
import com.enigmacamp.mastermenu.model.entity.*;
import com.enigmacamp.mastermenu.repository.*;
import com.enigmacamp.mastermenu.service.TransactionDetailService;
import com.enigmacamp.mastermenu.service.impl.TransactionImpl;
import com.enigmacamp.mastermenu.utils.enums.EOrderStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import jakarta.persistence.EntityNotFoundException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionImplTest {

    @InjectMocks
    private TransactionImpl transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionDetailService transactionDetailService;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ModelMapper modelMapper;

    private Transaction transaction;
    private Transaction updatedTransaction;
    private TransactionDetail transactionDetail;
    private Employee employee;
    private Customer customer;
    private Order order;
    private Menu menu;
    private TransactionReq transactionReq;
    private TransactionRes transactionRes;
    private TransactionDetailReq transactionDetailReq;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId("cust123");
        customer.setFullName("John Doe");

        employee = new Employee();
        employee.setId("empl123");
        employee.setFullName("Bill");

        order = new Order();
        order.setId("order123");
        order.setCustomer(customer);
        order.setStatus(EOrderStatus.PROCESSING);

        menu = new Menu();
        menu.setId("menu123");
        menu.setName("Pizza");
        menu.setPrice(100);
        menu.setStock(50);

        transaction = new Transaction();
        transaction.setCustomer(customer);
        transaction.setTransactionDate(Date.valueOf(LocalDate.now()));

        updatedTransaction = new Transaction();
        updatedTransaction.setId("trans123");
        updatedTransaction.setCustomer(customer);
        updatedTransaction.setEmployee(employee);
        updatedTransaction.setOrder(order);
        updatedTransaction.setTransactionDate(Date.valueOf(LocalDate.now()));


        transactionDetail = new TransactionDetail();
        transactionDetail.setId("detail123");
        transactionDetail.setMenu(menu);
        transactionDetail.setQuantity(2);
        

        transactionDetailReq = new TransactionDetailReq();
        transactionDetailReq.setId("detail123");
        transactionDetailReq.setMenuId(menu.getId());
        transactionDetailReq.setQuantity(2);

        transactionReq = new TransactionReq();
        transactionReq.setCustomerId(customer.getId());
        transactionReq.setOrderId(order.getId());
        transactionReq.setTransactionDetail(List.of(transactionDetailReq));

        transactionRes = new TransactionRes();
        transactionRes.setId("trans123");


        
    }

   @Test
   void createTransaction_ShouldReturnTransactionRes_WhenDataIsValid() {
       // Mock the repository methods and other services
       when(customerRepository.findCustomerByDeletedFalse(transactionReq.getCustomerId())).thenReturn(customer);
       when(orderRepository.save(any(Order.class))).thenReturn(order);
       when(menuRepository.findMenuByDeletedFalse(transactionDetailReq.getMenuId())).thenReturn(menu);
       when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
       when(transactionDetailService.saveTransactionDetail(any(TransactionDetail.class))).thenReturn(new TransactionDetail());
   
       // Mock the ModelMapper to correctly map from TransactionReq to Transaction
       when(modelMapper.map(transactionReq, Transaction.class)).thenReturn(transaction);
       when(modelMapper.map(transaction, TransactionRes.class)).thenReturn(new TransactionRes());
   
       // Call the method under test
       TransactionRes response = transactionService.createTransaction(transactionReq);
   
       // Assert the results
       assertNotNull(response);
   
       // Verify the repository save method is called only once
       verify(transactionRepository, times(2)).save(any(Transaction.class));
       verify(transactionDetailService, times(1)).saveTransactionDetail(any(TransactionDetail.class));
   }
   


   @Test
    void createTransaction_ShouldThrowEntityNotFoundException_WhenCustomerNotFound() {
        // Simulate customer not found (null)
        when(customerRepository.findCustomerByDeletedFalse(transactionReq.getCustomerId())).thenReturn(null);
        when(modelMapper.map(any(TransactionReq.class), eq(Transaction.class)))
        .thenReturn(new Transaction());


        // Assert that the correct exception is thrown
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, 
            () -> transactionService.createTransaction(transactionReq));

        // Verify that the exception message is correct
        assertEquals("Customer with id cust123 Not Found", exception.getMessage());
    }


    @Test
    void getTransactionById_ShouldReturnTransactionListRes_WhenTransactionExists() {
        when(transactionRepository.getTransactionById("trans123")).thenReturn(transaction);
        when(modelMapper.map(any(Transaction.class), eq(TransactionListRes.class))).thenReturn(new TransactionListRes());

        TransactionListRes response = transactionService.getTransactionById("trans123");

        assertNotNull(response);
        verify(transactionRepository, times(1)).getTransactionById("trans123");
    }

    @Test
    void getTransactionById_ShouldThrowEntityNotFoundException_WhenTransactionNotFound() {
        when(transactionRepository.getTransactionById("trans123")).thenReturn(null);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, 
            () -> transactionService.getTransactionById("trans123"));

        assertEquals("Transaction Not Found", exception.getMessage());
    }

    @Test
    void updateTransaction_ShouldReturnTransactionRes_WhenDataIsValid() {
        // Mock required dependencies
        when(orderRepository.findOrderByDeletedFalse(transactionReq.getOrderId())).thenReturn(order);
        when(customerRepository.findCustomerByDeletedFalse(transactionReq.getCustomerId())).thenReturn(customer);
        when(employeeRepository.findEmployeeByDeletedFalse(transactionReq.getEmployeeId())).thenReturn(employee); // Mock employee
        when(transactionRepository.getTransactionById(transactionReq.getId())).thenReturn(transaction);
        when(transactionDetailService.getTransactionDetailById(transactionDetailReq.getId())).thenReturn(transactionDetail); // Mock transaction detail
        when(menuRepository.findMenuByDeletedFalse(menu.getId())).thenReturn(menu); // Mock menu
        when(transactionRepository.save(any(Transaction.class))).thenReturn(updatedTransaction);
        when(modelMapper.map(any(Transaction.class), eq(TransactionRes.class))).thenReturn(transactionRes);

        // Perform test
        TransactionRes response = transactionService.updateTransaction(transactionReq);

        assertNotNull(response);
        assertEquals("trans123", response.getId());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void updateTransaction_ShouldThrowEntityNotFoundException_WhenTransactionNotFound() {
        when(transactionRepository.getTransactionById(transactionReq.getId())).thenReturn(null);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, 
            () -> transactionService.updateTransaction(transactionReq));

        assertEquals("Transaction Not Found", exception.getMessage());
    }
}

