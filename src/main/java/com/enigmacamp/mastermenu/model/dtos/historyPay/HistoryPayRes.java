package com.enigmacamp.mastermenu.model.dtos.historyPay;

import java.time.LocalDateTime;

import com.enigmacamp.mastermenu.utils.enums.EPaymentStatus;
import com.enigmacamp.mastermenu.utils.enums.EPaymentType;

import lombok.Data;

@Data
public class HistoryPayRes {
    private String id;
    private String customerId;
    private Double amount;
    private EPaymentType paymentType;
    private EPaymentStatus paymentStatus;
    private LocalDateTime createdAt;
}
