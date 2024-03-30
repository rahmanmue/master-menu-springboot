package com.enigmacamp.mastermenu.model.request;

import lombok.Data;

@Data
public class TransactionDetailReq {
    private String MenuId;
    private Integer quantity;
}
