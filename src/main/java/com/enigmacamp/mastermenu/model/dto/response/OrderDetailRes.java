package com.enigmacamp.mastermenu.model.dto.response;

import com.enigmacamp.mastermenu.utils.enums.EOrderStatus;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDetailRes {
    private String id;
    private Date date;
    private String customerId;
    private EOrderStatus status;
}
