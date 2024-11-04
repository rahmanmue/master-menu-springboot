package com.enigmacamp.mastermenu.model.dto.request;

import com.enigmacamp.mastermenu.utils.enums.ERole;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserReq {

    @NotNull(message = "Full name cannot be null")
    @NotBlank(message = "Full name cannot be blank")
    @Size(max = 50, message = "Full name should not exceed 50 characters")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Full name must contain only letters and spaces")
    private String fullName;

    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    private String password;
    private ERole role;
    
}
