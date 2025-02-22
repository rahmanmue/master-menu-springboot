package com.enigmacamp.mastermenu.service;

import com.enigmacamp.mastermenu.model.dtos.transaction.TransactionListRes;
import com.enigmacamp.mastermenu.model.dtos.transaction.TransactionReq;
import com.enigmacamp.mastermenu.model.dtos.transaction.TransactionRes;

import java.util.List;

public interface TransactionService {

    TransactionRes createTransaction(TransactionReq transactionReq);
    TransactionRes updateTransaction(TransactionReq transactionReq);

    List<TransactionListRes> getAllTransaction();
    List<TransactionListRes> getAllTransactionByCustomerName(String customerName);
    TransactionListRes getTransactionById(String id);
    void deleteTransaction(String id);


}
