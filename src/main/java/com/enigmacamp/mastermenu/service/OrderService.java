package com.enigmacamp.mastermenu.service;

import com.enigmacamp.mastermenu.model.dtos.order.OrderDetailRes;
import com.enigmacamp.mastermenu.model.dtos.order.OrderReq;
import com.enigmacamp.mastermenu.model.dtos.order.OrderRes;

import java.util.List;

public interface OrderService {
    List<OrderDetailRes> getAllOrder();
    List<OrderDetailRes> getOrderByCustomerId(String customerId);
    OrderDetailRes getOrderById(String id);
    // OrderRes createOrder(OrderReq orderReq);
    OrderRes updateOrder(String id, OrderReq order);
    // void deleteOrder(String id);
    List<OrderDetailRes> getOrderByStatus(String status);
}
