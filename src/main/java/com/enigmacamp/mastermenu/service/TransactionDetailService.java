package com.enigmacamp.mastermenu.service;

import com.enigmacamp.mastermenu.model.entity.TransactionDetail;


public interface TransactionDetailService {

    TransactionDetail getTransactionDetailById(String id);

    TransactionDetail saveTransactionDetail(TransactionDetail transactionDetail);
    TransactionDetail updateTransactionDetail(TransactionDetail transactionDetail);
    void deleteTransactionDetail(String id);

}
