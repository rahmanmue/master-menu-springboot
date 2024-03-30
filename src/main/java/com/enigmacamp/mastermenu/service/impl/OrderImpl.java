package com.enigmacamp.mastermenu.service.impl;

import com.enigmacamp.mastermenu.model.entity.Order;
import com.enigmacamp.mastermenu.repository.OrderRepository;
import com.enigmacamp.mastermenu.service.OrderService;
import com.enigmacamp.mastermenu.utils.enums.EOrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderImpl implements OrderService {
    private final OrderRepository orderRepository;
    @Override
    public List<Order> getAllOrder() {
        return orderRepository.getAllOrder();
    }

    @Override
    public List<Order> getOrderByCustomerId(String customerId) {
        return orderRepository.findOrderByCustomerIdDesc(customerId);
    }

    @Override
    public Order getOrderById(String id) {
        return orderRepository.findOrderByDeletedFalse(id);
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Order order) {
        if(orderRepository.findOrderByDeletedFalse(order.getId()) != null){
            return orderRepository.save(order);
        }else{
            throw new RuntimeException("Order with id "+order.getId()+" Not Found");
        }
    }

    @Override
    public void deleteOrder(String id) {
        if (orderRepository.findOrderByDeletedFalse(id) != null) {
            orderRepository.deleteOrder(id);
        } else {
            throw new RuntimeException("Order with id "+id+" Not Found");
        }
    }

    @Override
    public List<Order> getOrderByStatus(String status) {
        return orderRepository.getAllOrder()
                .stream().filter(order -> order.getStatus().equals(EOrderStatus.valueOf(status)))
                .toList();
    }
}
