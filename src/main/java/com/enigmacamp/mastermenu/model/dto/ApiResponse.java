package com.enigmacamp.mastermenu.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private int statusCode;
    private String message;
    private T data;
}

