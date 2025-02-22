package com.enigmacamp.mastermenu.service;

import java.util.List;

import com.enigmacamp.mastermenu.model.dtos.historyPay.HistoryPayRes;
import com.enigmacamp.mastermenu.model.entity.Customer;
import com.enigmacamp.mastermenu.model.entity.HistoryPay;
import com.enigmacamp.mastermenu.utils.enums.EPaymentStatus;
import com.enigmacamp.mastermenu.utils.enums.EPaymentType;

public interface HistoryPayService {
    List<HistoryPayRes> getAllHistoryPay(String email);
    HistoryPay createHistoryPayTopUp(String OrderId, String amount, EPaymentType ePaymentType, EPaymentStatus ePaymentStatus);
    HistoryPay createHistoryPayPayment(Customer customer, int price, EPaymentType ePaymentType, EPaymentStatus ePaymentStatus);
} 