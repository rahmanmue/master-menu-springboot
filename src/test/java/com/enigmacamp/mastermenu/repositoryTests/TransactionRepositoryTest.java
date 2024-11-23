package com.enigmacamp.mastermenu.repositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.enigmacamp.mastermenu.model.entity.Customer;
import com.enigmacamp.mastermenu.model.entity.Order;
import com.enigmacamp.mastermenu.model.entity.Transaction;
import com.enigmacamp.mastermenu.repository.CustomerRepository;
import com.enigmacamp.mastermenu.repository.OrderRepository;
import com.enigmacamp.mastermenu.repository.TransactionRepository;

@ActiveProfiles("test")
@DataJpaTest
public class TransactionRepositoryTest {
    
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired 
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;
    private Order order;

    @BeforeEach
    void setup() {
        // Create and save Customer
        customer = Customer.builder().fullName("John Doe").phone("12345678").build();
        customerRepository.save(customer);

        // Create and save Order
        order = Order.builder().customer(customer).date(new Date()).build();
        orderRepository.save(order);

        // Create and save Transaction
        Transaction transaction = Transaction.builder()
                .transactionDate(new Date())
                .totalPrice(50000)
                .totalItem(5)
                .customer(customer)
                .order(order)
                .build();

        transactionRepository.save(transaction);
    }

    @Test
    void testGetAllTransaction() {
        List<Transaction> transactions = transactionRepository.getAllTransaction();
        assertFalse(transactions.isEmpty(), "Transaction list should not be empty");
    }

    @Test
    void testGetTransactionById() {
        Transaction transaction = transactionRepository.getAllTransaction().get(0);
        Transaction result = transactionRepository.getTransactionById(transaction.getId());
        assertNotNull(result, "Transaction should not be null");
        assertEquals(transaction.getId(), result.getId(), "Transaction ID should match");
    }

    @Test
    void testFindTransactionsByCustomerName() {
        List<Transaction> transactions = transactionRepository.findTransactionsByCustomerName("John");
        assertFalse(transactions.isEmpty(), "Transactions list should not be empty");
        assertEquals(customer.getFullName(), transactions.get(0).getCustomer().getFullName(), "Customer name should match");
    }

    @Test
    void testGetTransactionByOrderId() {
        Transaction result = transactionRepository.getTransactionByOrderId(order.getId());
        assertNotNull(result, "Transaction should not be null");
        assertEquals(order.getId(), result.getOrder().getId(), "Order ID should match");
    }

}
