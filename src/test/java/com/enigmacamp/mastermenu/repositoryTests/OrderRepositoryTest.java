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

import com.enigmacamp.mastermenu.model.entity.Customer;
import com.enigmacamp.mastermenu.model.entity.Order;
import com.enigmacamp.mastermenu.repository.CustomerRepository;
import com.enigmacamp.mastermenu.repository.OrderRepository;
import com.enigmacamp.mastermenu.utils.enums.EOrderStatus;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;

    @BeforeEach
    void setup() {
        // Create and save Customer
        customer = Customer.builder().fullName("John Doe").phone("12345678").build();
        customerRepository.save(customer);

        // Create and save Order
        Order order = Order.builder()
                .customer(customer)
                .date(new Date())
                .status(EOrderStatus.PROCESSING)
                .build();
        orderRepository.save(order);
    }

    @Test
    void testGetAllOrder() {
        List<Order> orders = orderRepository.getAllOrder();
        assertFalse(orders.isEmpty(), "Order list should not be empty");
    }

    @Test
    void testFindOrderByDeletedFalse() {
        Order order = orderRepository.getAllOrder().get(0);
        Order result = orderRepository.findOrderByDeletedFalse(order.getId());
        assertNotNull(result, "Order should not be null");
        assertEquals(order.getId(), result.getId(), "Order ID should match");
    }

    @Test
    void testFindOrderByCustomerIdDesc() {
        List<Order> orders = orderRepository.findOrderByCustomerIdDesc(customer.getId());
        assertFalse(orders.isEmpty(), "Order list should not be empty");
        assertEquals(customer.getId(), orders.get(0).getCustomer().getId(), "Customer ID should match");
    }

    @Test
    void testFindOrderByStatus() {
        List<Order> orders = orderRepository.findOrderByStatus(EOrderStatus.PROCESSING);
        assertFalse(orders.isEmpty(), "Order list should not be empty");
        assertEquals(EOrderStatus.PROCESSING, orders.get(0).getStatus(), "Order status should match");
    }
}
