package com.enigmacamp.mastermenu.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enigmacamp.mastermenu.model.dtos.ApiResponse;
import com.enigmacamp.mastermenu.model.dtos.wallet.WalletRes;
import com.enigmacamp.mastermenu.service.PaymentService;
import com.enigmacamp.mastermenu.service.WalletService;

import lombok.RequiredArgsConstructor;
import com.enigmacamp.mastermenu.utils.constant.ApiPathConstant;
import com.midtrans.httpclient.error.MidtransError;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION + ApiPathConstant.WALLET)
@PreAuthorize("hasRole('USER')")
public class WalletController {
    private final PaymentService paymentService;
    private final WalletService walletService;

    @GetMapping("/my-balance")
    public ResponseEntity<ApiResponse<WalletRes>> getBalance(@AuthenticationPrincipal UserDetails userDetails) {
        WalletRes wallet = walletService.getWalletUser(userDetails.getUsername());
        return new ResponseEntity<>(
            ApiResponse.<WalletRes>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success")
                .data(wallet)
                .build(),
                HttpStatus.OK      
            );
    }

    @PostMapping("/topup")
    public ResponseEntity<ApiResponse<Map<String, Object>>> topUp(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Double amount) throws MidtransError{

        Map<String, Object> data = paymentService.createTopUpTransaction(userDetails.getUsername(), amount); 
        return new ResponseEntity<>(
            ApiResponse.<Map<String, Object>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success")
                .data(data)
                .build(),
                HttpStatus.OK
            );
       
    }

    
    
}
