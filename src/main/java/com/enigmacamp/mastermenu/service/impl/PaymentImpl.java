package com.enigmacamp.mastermenu.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.enigmacamp.mastermenu.model.entity.Customer;
import com.enigmacamp.mastermenu.model.entity.User;
import com.enigmacamp.mastermenu.repository.CustomerRepository;
import com.enigmacamp.mastermenu.repository.UserRepository;
import com.enigmacamp.mastermenu.service.PaymentService;
import com.midtrans.httpclient.error.MidtransError;
import com.midtrans.service.MidtransSnapApi;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentImpl implements PaymentService {
    private final MidtransSnapApi midtransSnapApi;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    @Override
    public Map<String, Object> createTopUpTransaction(String email, Double amount) throws MidtransError{

        User user = userRepository.findByEmail(email).orElseThrow(
            () -> new EntityNotFoundException("user not found")
        );
     
        Customer customer = customerRepository.findByUser_Id(user.getId());

        if(customer.getId().isEmpty()){
            throw new EntityNotFoundException("customer not found");
        }
        
        UUID idRand = UUID.randomUUID();
        String orderId = customer.getId()+"::"+idRand.toString().substring(0,8);
        
        Map<String, String> transactionDetails = new HashMap<>();
        transactionDetails.put("order_id", orderId);
        transactionDetails.put("gross_amount", String.valueOf(amount));

       
        String phone = customer.getPhone();

        Map<String, String> customerDetails = new HashMap<>();
        customerDetails.put("email", email);
        customerDetails.put("phone", phone);
    

        Map<String, Object> params = new HashMap<>();
        params.put("transaction_details", transactionDetails);
        params.put("customer_details", customerDetails);
        
        // return midtransSnapApi.createTransaction(params);

        return midtransSnapApi.createTransaction(params).toMap();
        
    }


}
