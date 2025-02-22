package com.enigmacamp.mastermenu.serviceTests;


import com.enigmacamp.mastermenu.model.dtos.order.OrderDetailRes;
import com.enigmacamp.mastermenu.model.dtos.order.OrderReq;
import com.enigmacamp.mastermenu.model.dtos.order.OrderRes;
import com.enigmacamp.mastermenu.model.entity.Customer;
import com.enigmacamp.mastermenu.model.entity.Order;
import com.enigmacamp.mastermenu.repository.OrderRepository;
import com.enigmacamp.mastermenu.service.impl.OrderImpl;
import com.enigmacamp.mastermenu.service.impl.TransactionImpl;
import com.enigmacamp.mastermenu.utils.enums.EOrderStatus;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private TransactionImpl transactionService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OrderImpl orderService;

    private Order order;
    private OrderReq orderReq;
    private OrderDetailRes orderDetailRes;
    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId("cust1");
        customer.setFullName("customer1");

        // Setup mock objects
        order = new Order();
        order.setId("1");
        order.setCustomer(customer);;
        order.setStatus(EOrderStatus.PROCESSING);

        orderReq = new OrderReq();
        orderReq.setEmployeeId("employee1");
        orderReq.setStatus(EOrderStatus.PROCESSING);

        orderDetailRes = new OrderDetailRes();
        orderDetailRes.setId("1");
        orderDetailRes.setCustomerId("customer1");
        orderDetailRes.setStatus(EOrderStatus.PROCESSING);
    }

    @Test
    void getAllOrder() {
        List<Order> orders = Arrays.asList(order);
        when(orderRepository.getAllOrder()).thenReturn(orders);
        when(modelMapper.map(order, OrderDetailRes.class)).thenReturn(orderDetailRes);

        List<OrderDetailRes> result = orderService.getAllOrder();

        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
        verify(orderRepository, times(1)).getAllOrder();
    }

    @Test
    void getOrderByCustomerId() {
        List<Order> orders = Arrays.asList(order);
        when(orderRepository.findOrderByCustomerIdDesc("customer1")).thenReturn(orders);
        when(modelMapper.map(order, OrderDetailRes.class)).thenReturn(orderDetailRes);

        List<OrderDetailRes> result = orderService.getOrderByCustomerId("customer1");

        assertEquals(1, result.size());
        assertEquals("customer1", result.get(0).getCustomerId());
        verify(orderRepository, times(1)).findOrderByCustomerIdDesc("customer1");
    }

    @Test
    void getOrderById_Found() {
        when(orderRepository.findOrderByDeletedFalse("1")).thenReturn(order);
        when(modelMapper.map(order, OrderDetailRes.class)).thenReturn(orderDetailRes);

        OrderDetailRes result = orderService.getOrderById("1");

        assertNotNull(result);
        assertEquals("1", result.getId());
        verify(orderRepository, times(1)).findOrderByDeletedFalse("1");
    }

    @Test
    void getOrderById_NotFound() {
        when(orderRepository.findOrderByDeletedFalse("1")).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> orderService.getOrderById("1"));
    }

    @Test
    void updateOrder() {
        String id = "1";
        when(orderRepository.findOrderByDeletedFalse(id)).thenReturn(order);
        when(transactionService.CancelOrCompletedTransactionByOrder(orderReq, order)).thenReturn(order);
        when(modelMapper.map(order, OrderRes.class)).thenReturn(new OrderRes());

        OrderRes result = orderService.updateOrder(id, orderReq);

        assertNotNull(result);
        verify(orderRepository, times(1)).findOrderByDeletedFalse("1");
        verify(transactionService, times(1)).CancelOrCompletedTransactionByOrder(orderReq, order);
    }

    @Test
    void updateOrder_NotFound() {
        String id = "1";
        when(orderRepository.findOrderByDeletedFalse(id)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> orderService.updateOrder(id, orderReq));
    }

    @Test
    void getOrderByStatus() {
        List<Order> orders = Arrays.asList(order);
        when(orderRepository.findOrderByStatus(EOrderStatus.PROCESSING)).thenReturn(orders);
        when(modelMapper.map(order, OrderDetailRes.class)).thenReturn(orderDetailRes);

        List<OrderDetailRes> result = orderService.getOrderByStatus("PROCESSING");

        assertEquals(1, result.size());
        assertEquals("PROCESSING", result.get(0).getStatus().toString());
        verify(orderRepository, times(1)).findOrderByStatus(EOrderStatus.PROCESSING);
    }
}

