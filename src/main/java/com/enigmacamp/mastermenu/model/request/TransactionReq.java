package com.enigmacamp.mastermenu.model.request;

import lombok.Data;

import java.util.List;

@Data
public class TransactionReq {
    private String customerId;
    private String orderId;
    private String employeeId;
    private List<TransactionDetailReq> transactionDetail;
}
