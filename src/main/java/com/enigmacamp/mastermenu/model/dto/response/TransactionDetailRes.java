package com.enigmacamp.mastermenu.model.dto.response;

import lombok.Data;

@Data
public class TransactionDetailRes {
    private String id;
    private String menuId;
    private Integer quantity;
    private Integer price;
    private Integer subtotal;
}
