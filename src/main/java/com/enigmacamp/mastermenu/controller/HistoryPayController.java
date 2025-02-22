package com.enigmacamp.mastermenu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enigmacamp.mastermenu.model.dtos.ApiResponse;
import com.enigmacamp.mastermenu.model.dtos.historyPay.HistoryPayRes;
import com.enigmacamp.mastermenu.service.HistoryPayService;
import com.enigmacamp.mastermenu.utils.constant.ApiPathConstant;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION + ApiPathConstant.HISTORYPAY)
public class HistoryPayController {
    private final HistoryPayService historyPayService;

    @GetMapping()
    public ResponseEntity<ApiResponse<List<HistoryPayRes>>> getHistoryPay(@AuthenticationPrincipal UserDetails userDetails) {
        List<HistoryPayRes> historyPay = historyPayService.getAllHistoryPay(userDetails.getUsername());

        return new ResponseEntity<>(
            ApiResponse.<List<HistoryPayRes>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success")
                .data(historyPay)
                .build(),
                HttpStatus.OK      
            );
    }
}
