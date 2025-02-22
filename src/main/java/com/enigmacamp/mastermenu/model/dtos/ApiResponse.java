package com.enigmacamp.mastermenu.model.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiResponse<T> {
    private int statusCode;
    private String message;
    private T data;
}

