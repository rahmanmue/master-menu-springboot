package com.enigmacamp.mastermenu.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ApiResponseError<T> {
    private int statusCode;
    private T errors;
}
