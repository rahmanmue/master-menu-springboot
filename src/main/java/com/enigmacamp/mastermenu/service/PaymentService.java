package com.enigmacamp.mastermenu.service;

import java.util.Map;

import com.midtrans.httpclient.error.MidtransError;

public interface PaymentService {
    Map<String, Object> createTopUpTransaction(String email, Double amount) throws MidtransError;    
} 
