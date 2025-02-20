package com.enigmacamp.mastermenu.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.enigmacamp.mastermenu.model.dto.response.CustomerDetailRes;
import com.enigmacamp.mastermenu.model.entity.Customer;
import com.enigmacamp.mastermenu.repository.CustomerRepository;
import com.midtrans.httpclient.error.MidtransError;
import com.midtrans.service.MidtransSnapApi;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final MidtransSnapApi midtransSnapApi;
    private final CustomerRepository customerRepository;

    public String createTopUpTransaction(String userId, Double amount) throws MidtransError{
       
            Customer customer = customerRepository.findByUser_Id(userId);
           
            UUID idRand = UUID.randomUUID();
            String orderId = customer.getId()+"::"+idRand.toString().substring(0,8);
            
            Map<String, String> transactionDetails = new HashMap<>();
            transactionDetails.put("order_id", orderId);
            transactionDetails.put("gross_amount", String.valueOf(amount));

            String email = customer.getUser().getEmail();
            String phone = customer.getPhone();

            Map<String, String> customerDetails = new HashMap<>();
            customerDetails.put("email", email);
            customerDetails.put("phone", phone);
           

            Map<String, Object> params = new HashMap<>();
            params.put("transaction_details", transactionDetails);
            params.put("customer_details", customerDetails);
            
            // return midtransSnapApi.createTransaction(params);

            return midtransSnapApi.createTransactionRedirectUrl(params);
    }
}
