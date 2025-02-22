package com.enigmacamp.mastermenu.model.dtos.transaction;

import lombok.Data;

import java.util.List;

@Data
public class TransactionReq {
    private String id;
    private String customerId;
    private String orderId;
    private String employeeId;
    private List<TransactionDetailReq> transactionDetail;
}
