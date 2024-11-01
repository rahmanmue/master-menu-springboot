package com.enigmacamp.mastermenu.service;

import com.enigmacamp.mastermenu.model.dto.request.OrderReq;
import com.enigmacamp.mastermenu.model.dto.response.OrderDetailRes;
import com.enigmacamp.mastermenu.model.dto.response.OrderRes;

import java.util.List;

public interface OrderService {
    List<OrderDetailRes> getAllOrder();
    List<OrderDetailRes> getOrderByCustomerId(String customerId);
    OrderDetailRes getOrderById(String id);
    // OrderRes createOrder(OrderReq orderReq);
    OrderRes updateOrder(OrderReq order);
    // void deleteOrder(String id);
    List<OrderDetailRes> getOrderByStatus(String status);
}
