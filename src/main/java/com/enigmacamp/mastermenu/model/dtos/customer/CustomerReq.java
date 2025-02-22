package com.enigmacamp.mastermenu.model.dtos.customer;

import com.enigmacamp.mastermenu.utils.enums.EGender;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class CustomerReq {
    private String id;

    @NotBlank(message = "Full name cannot be blank")
    @Size(max = 50, message = "Full name should not exceed 50 characters")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Full name must contain only letters and spaces")
    private String fullName;

    // @NotNull(message = "Date of birth cannot be null")
    @Past(message = "Date of birth must be a date in the past")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
    private Date dateOfBirth;

    // @NotNull(message = "Address cannot be null")
    @NotBlank(message = "Address cannot be blank")
    @Size(max = 255, message = "Address should not exceed 255 characters")
    private String address;

    // @NotNull(message = "Phone number cannot be null")
    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^[0-9]+$", message = "Phone number must contain only digits")
    private String phone;

    // @NotNull(message = "Gender cannot be null")
    private EGender gender;
}
