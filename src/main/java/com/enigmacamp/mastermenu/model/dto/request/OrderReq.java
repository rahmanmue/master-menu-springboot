package com.enigmacamp.mastermenu.model.dto.request;

import com.enigmacamp.mastermenu.utils.enums.EOrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class OrderReq {
    private String id;

    @NotNull(message = "EmployeeId cannot be null")
    @NotBlank(message = "EmployeeId cannot be blank")
    private String employeeId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
    private Date date;
    
    @NotNull(message = "Status cannot be null")
    private EOrderStatus status;
}
