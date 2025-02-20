package com.enigmacamp.mastermenu.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enigmacamp.mastermenu.service.HistoryPayService;
import com.enigmacamp.mastermenu.utils.enums.EPaymentStatus;
import com.enigmacamp.mastermenu.utils.enums.EPaymentType;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
public class PublicController {

    private final HistoryPayService historyPayService;
    
    
    @GetMapping("/hello")
    public String getMethodName() {
        return new String("Hello World");
    }

    @PostMapping("/notification/topup")
    public ResponseEntity<String> handleMidtransCallback(@RequestBody Map<String, Object> payload) {
        try {
            String orderId = (String) payload.get("order_id");
            String transactionStatus = (String) payload.get("transaction_status");
            String fraudStatus = (String) payload.get("fraud_status");
            String amount = (String) payload.get("gross_amount");
    
            System.out.printf("Received Midtrans Notification: Order ID: %s, Status: %s, Amount: %s", orderId, transactionStatus, amount);
    
            if ("settlement".equals(transactionStatus)) {
               historyPayService.createHistoryPayTopUp(orderId, amount, EPaymentType.TOPUP, EPaymentStatus.SUCCESS);
            } else if ("cancel".equals(transactionStatus) || "deny".equals(fraudStatus)) {
                historyPayService.createHistoryPayTopUp(orderId, amount, EPaymentType.TOPUP, EPaymentStatus.FAILED);
            }
    
            return ResponseEntity.ok("Notification received");
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing notification");
        }
    }
}
