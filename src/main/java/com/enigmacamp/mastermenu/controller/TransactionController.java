package com.enigmacamp.mastermenu.controller;

import com.enigmacamp.mastermenu.model.dto.ApiResponse;
import com.enigmacamp.mastermenu.model.dto.request.TransactionReq;
import com.enigmacamp.mastermenu.model.dto.response.TransactionListRes;
import com.enigmacamp.mastermenu.model.dto.response.TransactionRes;
import com.enigmacamp.mastermenu.service.TransactionService;
import com.enigmacamp.mastermenu.utils.constant.ApiPathConstant;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION + ApiPathConstant.TRANSACTION)
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TransactionListRes>>> getAllTransactions() {
        return new ResponseEntity<>(
            ApiResponse.<List<TransactionListRes>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success")
                .data(transactionService.getAllTransaction())
                .build(),
                HttpStatus.OK      
            );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<TransactionListRes>>> getAllTransactionsByCustomer(@RequestParam(name= "customerName") String customerName) {
        return new ResponseEntity<>(
            ApiResponse.<List<TransactionListRes>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success")
                .data(transactionService.getAllTransactionByCustomerName(customerName))
                .build(),
                HttpStatus.OK      
            );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TransactionListRes>> getTransactionById(@PathVariable String id) {
        TransactionListRes transactionListRes = transactionService.getTransactionById(id);
        return new ResponseEntity<>(
            ApiResponse.<TransactionListRes>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success retrivied data")
                .data(transactionListRes)
                .build(),
                HttpStatus.OK      
            );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TransactionRes>> createTransaction(@Valid @RequestBody TransactionReq transactionReq) {
        TransactionRes savedtransaction = transactionService.createTransaction(transactionReq);
        
        return new ResponseEntity<>(
            ApiResponse.<TransactionRes>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Transaction created successfully")
                .data(savedtransaction)
                .build(),
                HttpStatus.CREATED      
            );
    }

    @PutMapping
    public  ResponseEntity<ApiResponse<TransactionRes>>  updateTransaction(@Valid @RequestBody TransactionReq transactionReq) {
        TransactionRes updafedtransaction = transactionService.updateTransaction(transactionReq);
        
        return new ResponseEntity<>(
            ApiResponse.<TransactionRes>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Transaction updated successfully")
                .data(updafedtransaction)
                .build(),
                HttpStatus.OK      
            );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTransaction(@PathVariable String id) {
        transactionService.deleteTransaction(id);

        return new ResponseEntity<>(
            ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Transaction deleted sucessfully")
                .data("Transaction with id "+ id + " has been deleted")
                .build(),
            HttpStatus.OK
        );

    }

}
