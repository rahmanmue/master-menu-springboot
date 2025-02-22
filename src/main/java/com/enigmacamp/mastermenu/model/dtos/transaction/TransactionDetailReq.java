package com.enigmacamp.mastermenu.model.dtos.transaction;

import lombok.Data;

@Data
public class TransactionDetailReq {
    private String id;
    private String menuId;
    private Integer quantity;
}
