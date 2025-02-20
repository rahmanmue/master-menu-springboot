package com.enigmacamp.mastermenu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enigmacamp.mastermenu.model.entity.HistoryPay;
import com.enigmacamp.mastermenu.service.HistoryPayService;
import com.enigmacamp.mastermenu.utils.constant.ApiPathConstant;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION + ApiPathConstant.HISTORYPAY)
public class HistoryPayController {
    private final HistoryPayService historyPayService;

    @GetMapping()
    public List<HistoryPay> getHistoryPay(@RequestParam String customerId) {
        return historyPayService.getAllHistoryPay(customerId);
    }
}
