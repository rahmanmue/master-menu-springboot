package com.enigmacamp.mastermenu.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import com.enigmacamp.mastermenu.model.dtos.ApiResponse;
import com.midtrans.httpclient.error.MidtransError;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse<String>> handleJwtExpiredException(ExpiredJwtException ex) {
        return new ResponseEntity<>(
            ApiResponse.<String>builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message("JWT Expired")
                .build(), 
            HttpStatus.UNAUTHORIZED);
    }

    // @ExceptionHandler(RuntimeException.class)
    // public ResponseEntity<Object> handleMidtransError(RuntimeException ex){
    //     Map<String, Object> body = new HashMap<>();
    //     body.put("timestamp", LocalDateTime.now());
    //     body.put("message", "Midtrans error occurred: " + ex.getMessage());
    //     return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);
    // }

    @ExceptionHandler(MidtransError.class)
    public ResponseEntity<Object> handleMidtransError(MidtransError err){
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Midtrans error occurred: " + err.getMessage());
        return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);
    }
    
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

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<String>> handleAccesDenied(AccessDeniedException ex){
       
        return new ResponseEntity<>(
            ApiResponse.<String>builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .message(ex.getMessage())
                .build(),
            HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGlobalExceptions(Exception ex){
        System.out.println(ex);
        return new ResponseEntity<>(
            ApiResponse.<String>builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An error occured")
                .data(ex.getMessage())
                .build(),
            HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
