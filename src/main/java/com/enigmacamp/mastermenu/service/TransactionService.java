package com.enigmacamp.mastermenu.service;

import com.enigmacamp.mastermenu.model.entity.Transaction;
import com.enigmacamp.mastermenu.model.entity.TransactionDetail;
import com.enigmacamp.mastermenu.model.request.TransactionReq;
import jakarta.transaction.Transactional;

import java.util.List;

public interface TransactionService {

    @Transactional
    Transaction createTransaction(TransactionReq transactionReq);
    @Transactional
    Transaction updateTransaction(Transaction transaction);
    List<Transaction> getAllTransaction();
    Transaction getTransactionById(String id);
    void deleteTransaction(Transaction transaction);


}
