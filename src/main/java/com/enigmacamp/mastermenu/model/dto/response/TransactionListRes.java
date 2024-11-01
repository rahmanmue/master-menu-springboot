package com.enigmacamp.mastermenu.model.dto.response;

import java.util.List;
import lombok.Data;

@Data
public class TransactionListRes {
    private String id;
    private String customerId;
    private String orderId;
    private String employeeId;
    private Integer totalItem;
    private Integer totalPrice;
    private List<TransactionDetailRes> transactionDetail;
}
