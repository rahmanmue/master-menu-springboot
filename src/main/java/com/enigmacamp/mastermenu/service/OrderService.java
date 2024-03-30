package com.enigmacamp.mastermenu.service;

import com.enigmacamp.mastermenu.model.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrder();
    List<Order> getOrderByCustomerId(String customerId);
    Order getOrderById(String id);

    Order createOrder(Order order);

    Order updateOrder(Order order);

    void deleteOrder(String id);

    List<Order> getOrderByStatus(String status);
}
