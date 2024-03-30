package com.enigmacamp.mastermenu.controller;

import com.enigmacamp.mastermenu.model.entity.Order;
import com.enigmacamp.mastermenu.service.OrderService;
import com.enigmacamp.mastermenu.utils.constant.ApiPathConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION + ApiPathConstant.ORDER)
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<Order> getAllOrder() {
        return orderService.getAllOrder();
    }

    @GetMapping("/status")
    public List<Order> getAllOrderByStatus(@RequestParam(name = "status") String status) {
        return orderService.getOrderByStatus(status.toUpperCase());
    }

    @GetMapping("/customer")
    public List<Order> getOrderByCustomerId(@RequestParam(name = "customer_id") String customer_id) {
        return orderService.getOrderByCustomerId(customer_id);
    }
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable String id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PutMapping
    public Order updateOrder(@RequestBody Order order) {
        return orderService.updateOrder(order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
    }


}
