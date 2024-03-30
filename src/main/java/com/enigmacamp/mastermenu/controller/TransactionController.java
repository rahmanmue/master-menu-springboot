package com.enigmacamp.mastermenu.controller;

import com.enigmacamp.mastermenu.model.entity.Transaction;
import com.enigmacamp.mastermenu.model.request.TransactionReq;
import com.enigmacamp.mastermenu.service.TransactionService;
import com.enigmacamp.mastermenu.utils.constant.ApiPathConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION + ApiPathConstant.TRANSACTION)
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransaction();
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable String id) {
        return transactionService.getTransactionById(id);
    }

   @PostMapping
    public Transaction createTransaction(@RequestBody TransactionReq transactionReq) {
        return transactionService.createTransaction(transactionReq);
    }

}
