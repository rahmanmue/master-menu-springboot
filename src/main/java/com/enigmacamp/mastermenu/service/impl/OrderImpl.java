package com.enigmacamp.mastermenu.service.impl;

import com.enigmacamp.mastermenu.model.dtos.order.OrderDetailRes;
import com.enigmacamp.mastermenu.model.dtos.order.OrderReq;
import com.enigmacamp.mastermenu.model.dtos.order.OrderRes;
import com.enigmacamp.mastermenu.model.entity.Order;
import com.enigmacamp.mastermenu.repository.OrderRepository;
import com.enigmacamp.mastermenu.service.OrderService;
import com.enigmacamp.mastermenu.utils.enums.EOrderStatus;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final TransactionImpl transactionService;
    private final ModelMapper modelMapper;

    @Override
    public List<OrderDetailRes> getAllOrder() {
        List<Order> orders = orderRepository.getAllOrder();
        return orders.stream()
            .map(order -> modelMapper.map(order, OrderDetailRes.class))
            .toList();
    }

    @Override
    public List<OrderDetailRes> getOrderByCustomerId(String customerId) {
        List<Order> orders = orderRepository.findOrderByCustomerIdDesc(customerId);
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDetailRes.class))
                .toList();
    }

    @Override
    public OrderDetailRes getOrderById(String id) {
        Order order = orderRepository.findOrderByDeletedFalse(id);

        if(order == null){
            throw new EntityNotFoundException("Order with id " + id + " not found");
        }

        return modelMapper.map(order, OrderDetailRes.class);
    }

    @Override
    public OrderRes updateOrder(String id, OrderReq orderReq) {

        Order existingOrder = orderRepository.findOrderByDeletedFalse(id);
        if(existingOrder == null){
            throw new EntityNotFoundException("Order with id " + id + " not found");
        }

        Order updated = transactionService.CancelOrCompletedTransactionByOrder(orderReq, existingOrder);

        return modelMapper.map(updated, OrderRes.class);

    }

    @Override
    public List<OrderDetailRes> getOrderByStatus(String status) {
        EOrderStatus eOrderStatus = EOrderStatus.valueOf(status.toUpperCase());
        List<Order> orders = orderRepository.findOrderByStatus(eOrderStatus);

        return orders.stream()
            .map(order -> modelMapper.map(order, OrderDetailRes.class))
            .toList();
    }

    // @Override
    // public OrderRes createOrder(OrderReq orderReq) {
    //     Order order = modelMapper.map(orderReq, Order.class);
    //     Customer customer = Customer.builder().id(order.getId()).build();
    //     order.setCustomer(customer);
    //     Order saved = orderRepository.save(order);
    //     return modelMapper.map(saved, OrderRes.class);
    // }

    // @Override
    // public void deleteOrder(String id) {

    //     if (orderRepository.findOrderByDeletedFalse(id) == null) {
    //         throw new EntityNotFoundException("Order with id "+id+" Not Found"); 
    //     }

    //     orderRepository.deleteById(id);
    // }

  

}
