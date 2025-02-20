package com.enigmacamp.mastermenu.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enigmacamp.mastermenu.model.entity.Wallet;
import com.enigmacamp.mastermenu.service.PaymentService;
import com.enigmacamp.mastermenu.service.WalletService;
import com.midtrans.httpclient.error.MidtransError;

import lombok.RequiredArgsConstructor;
import com.enigmacamp.mastermenu.utils.constant.ApiPathConstant;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION + ApiPathConstant.WALLET)
public class WalletController {
    private final PaymentService paymentService;
    private final WalletService walletService;

    @GetMapping("/my-balance")
    public Wallet getBalance(@AuthenticationPrincipal UserDetails userDetails) {
        return walletService.getWalletUser(userDetails.getUsername());
    }

    @PostMapping("/topup")
    public String topUp(@RequestParam String userId, @RequestParam Double amount) {
        try {
            return paymentService.createTopUpTransaction(userId, amount);
        } catch (MidtransError e) {
            return "Error: " + e.getMessage();
        }
    }

    
    

    
    
}
