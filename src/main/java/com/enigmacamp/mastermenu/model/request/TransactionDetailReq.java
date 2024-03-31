package com.enigmacamp.mastermenu.model.request;

import lombok.Data;

@Data
public class TransactionDetailReq {
    private String id;
    private String menuId;
    private Integer quantity;
}
