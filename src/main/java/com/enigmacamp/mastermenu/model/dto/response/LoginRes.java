package com.enigmacamp.mastermenu.model.dto.response;

import java.util.List;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRes {
    private String token;
    private Long expiresIn;
    private List<String> role;
}
