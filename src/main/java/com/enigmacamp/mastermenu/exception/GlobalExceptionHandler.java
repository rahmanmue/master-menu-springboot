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

import com.enigmacamp.mastermenu.model.dtos.ApiResponseError;
import com.midtrans.httpclient.error.MidtransError;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponseError<String>> handleJwtExpiredException(ExpiredJwtException ex) {
        return new ResponseEntity<>(
            ApiResponseError.<String>builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .errors("JWT Expired " + ex.getMessage())
                .build(), 
            HttpStatus.UNAUTHORIZED);
    }

  
    @ExceptionHandler(MidtransError.class)
    public ResponseEntity<ApiResponseError<String>> handleMidtransError(MidtransError err){
            int statusCode = err.getStatusCode();

            return new ResponseEntity<>(
                ApiResponseError.<String>builder()
                    .statusCode(HttpStatus.valueOf(statusCode).value())
                    .errors(err.getLocalizedMessage())
                    .build(),
                HttpStatus.valueOf(statusCode));
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseError<Map<String, String>>> handleValidationException (MethodArgumentNotValidException ex){
        
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

      
        return new ResponseEntity<>(
            ApiResponseError.<Map<String, String>>builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .errors(errors)
                .build(), 
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponseError<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        
        return new ResponseEntity<>(
            ApiResponseError.<String>builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .errors("Invalid input data type " + ex.getLocalizedMessage())
                .build(),
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponseError<String>> handleHEntityNotFoundException(EntityNotFoundException ex){
        
        return new ResponseEntity<>(
            ApiResponseError.<String>builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .errors(ex.getMessage())
                .build(),
            HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFileFormatException.class)
    public ResponseEntity<ApiResponseError<String>> handleInvalidFileFormatException(InvalidFileFormatException ex, WebRequest request) {
        return new ResponseEntity<>(
            ApiResponseError.<String>builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .errors("An error occured " + ex.getMessage())
                .build(),
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponseError<String>> handleAccesDenied(AccessDeniedException ex){
       
        return new ResponseEntity<>(
            ApiResponseError.<String>builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .errors(ex.getMessage())
                .build(),
            HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseError<String>> handleGlobalExceptions(Exception ex){
        System.out.println(ex);
        return new ResponseEntity<>(
            ApiResponseError.<String>builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errors("An error occured "+ ex.getMessage())
                .build(),
            HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
