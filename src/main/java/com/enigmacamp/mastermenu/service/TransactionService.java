package com.enigmacamp.mastermenu.service;

import com.enigmacamp.mastermenu.model.dto.request.TransactionReq;
import com.enigmacamp.mastermenu.model.dto.response.TransactionRes;
import com.enigmacamp.mastermenu.model.dto.response.TransactionListRes;



import java.util.List;

public interface TransactionService {

    TransactionRes createTransaction(TransactionReq transactionReq);
    TransactionRes updateTransaction(TransactionReq transactionReq);

    List<TransactionListRes> getAllTransaction();
    List<TransactionListRes> getAllTransactionByCustomerName(String customerName);
    TransactionListRes getTransactionById(String id);
    void deleteTransaction(String id);


}
