package com.enigmacamp.mastermenu.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.enigmacamp.mastermenu.model.dto.ApiResponse;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException (MethodArgumentNotValidException ex){
        
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

      
        return new ResponseEntity<>(
            ApiResponse.<Map<String, String>>builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Validation failed")
                .data(errors)
                .build(), 
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        
        return new ResponseEntity<>(
            ApiResponse.<String>builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Invalid input data type")
                .data( ex.getLocalizedMessage())
                .build(),
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleHEntityNotFoundException(EntityNotFoundException ex){
        
        return new ResponseEntity<>(
            ApiResponse.<String>builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build(),
            HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFileFormatException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidFileFormatException(InvalidFileFormatException ex, WebRequest request) {
        return new ResponseEntity<>(
            ApiResponse.<String>builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("An error occured")
                .data(ex.getMessage())
                .build(),
            HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGlobalExceptions(Exception ex){
       
        return new ResponseEntity<>(
            ApiResponse.<String>builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An error occured")
                .data(ex.getMessage())
                .build(),
            HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
