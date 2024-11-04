package com.enigmacamp.mastermenu.model.dto.request;

import com.enigmacamp.mastermenu.utils.enums.ERole;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginUserReq {
    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotNull(message = "Pssword cannot be null")
    @NotBlank(message = "Pssword cannot be blank")
    private String password;
    private ERole role;
}
