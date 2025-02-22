package com.enigmacamp.mastermenu.model.dtos.user;

import lombok.Data;

@Data
public class UserReq {
    private String id;
    private String email;
    private String password;
}
