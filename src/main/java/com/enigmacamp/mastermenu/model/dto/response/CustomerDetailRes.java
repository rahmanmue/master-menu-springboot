package com.enigmacamp.mastermenu.model.dto.response;

import com.enigmacamp.mastermenu.utils.enums.EGender;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
// import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CustomerDetailRes {
    private String id;
    private String fullName;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
    private Date dateOfBirth;
    private String address;
    private String phone;
    private EGender gender;
    // private LocalDateTime createdAt;
    // private LocalDateTime updatedAt;
}
