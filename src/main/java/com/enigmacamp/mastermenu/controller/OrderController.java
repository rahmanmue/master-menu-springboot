package com.enigmacamp.mastermenu.controller;

import com.enigmacamp.mastermenu.model.dtos.ApiResponse;
import com.enigmacamp.mastermenu.model.dtos.order.OrderDetailRes;
import com.enigmacamp.mastermenu.model.dtos.order.OrderReq;
import com.enigmacamp.mastermenu.model.dtos.order.OrderRes;
import com.enigmacamp.mastermenu.service.OrderService;
import com.enigmacamp.mastermenu.utils.constant.ApiPathConstant;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION + ApiPathConstant.ORDER)
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<OrderDetailRes>>> getAllOrder() {
        return new ResponseEntity<>(
            ApiResponse.<List<OrderDetailRes>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success")
                .data(orderService.getAllOrder())
                .build(),
                HttpStatus.OK      
            );
    }

    @GetMapping("/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<OrderDetailRes>>> getAllOrderByStatus(@RequestParam(name = "status") String status) {
        List<OrderDetailRes> orders = orderService.getOrderByStatus(status.toUpperCase());
        return new ResponseEntity<>(
            ApiResponse.<List<OrderDetailRes>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success retrivied data")
                .data(orders)
                .build(),
                HttpStatus.OK      
            );
    }

    @GetMapping("/customer")
    public ResponseEntity<ApiResponse<List<OrderDetailRes>>> getOrderByCustomerId(@RequestParam(name = "customerId") String customerId) {
        List<OrderDetailRes> orders = orderService.getOrderByCustomerId(customerId);
        return new ResponseEntity<>(
            ApiResponse.<List<OrderDetailRes>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success retrivied data")
                .data(orders)
                .build(),
                HttpStatus.OK      
            );
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<OrderDetailRes>> getOrderById(@PathVariable String id) {
        OrderDetailRes order = orderService.getOrderById(id);

        return new ResponseEntity<>(
            ApiResponse.<OrderDetailRes>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success retrivied data")
                .data(order)
                .build(),
                HttpStatus.OK      
            );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<OrderRes>> updateOrder(@PathVariable String id, @Valid @RequestBody OrderReq orderReq) {
        OrderRes updated = orderService.updateOrder(id, orderReq);

        return new ResponseEntity<>(
            ApiResponse.<OrderRes>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Order updated successfully")
                .data(updated)
                .build(),
                HttpStatus.OK      
            );
    }

    // @PostMapping
    // public ResponseEntity<ApiResponse<OrderRes>> createOrder(@Valid @RequestBody OrderReq orderReq) {
    //     OrderRes saved = orderService.createOrder(orderReq);

    //     return new ResponseEntity<>(
    //         ApiResponse.<OrderRes>builder()
    //             .statusCode(HttpStatus.CREATED.value())
    //             .message("Order created successfully")
    //             .data(saved)
    //             .build(),
    //             HttpStatus.CREATED      
    //         );
    // }

  

    // @DeleteMapping("/{id}")
    // public ResponseEntity<ApiResponse<String>> deleteOrder(@PathVariable String id) {
    //     orderService.deleteOrder(id);

    //     return new ResponseEntity<>(
    //         ApiResponse.<String>builder()
    //             .statusCode(HttpStatus.OK.value())
    //             .message("Order deleted sucessfully")
    //             .data("Order with id "+ id + " has been deleted")
    //             .build(),
    //         HttpStatus.OK
    //     );
    // }


}
