package com.enigmacamp.mastermenu.service.impl;

import com.enigmacamp.mastermenu.model.entity.TransactionDetail;
import com.enigmacamp.mastermenu.repository.TransactionDetailRepository;
import com.enigmacamp.mastermenu.service.TransactionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionDetailImpl implements TransactionDetailService {

    private final TransactionDetailRepository transactionDetailRepository;


    @Override
    public TransactionDetail getTransactionDetailById(String id) {
        return transactionDetailRepository.getTransactionDetailById(id);
    }

    @Override
    public TransactionDetail saveTransactionDetail(TransactionDetail transactionDetail) {
        return transactionDetailRepository.save(transactionDetail);
    }

    @Override
    public TransactionDetail updateTransactionDetail(TransactionDetail transactionDetail) {
        if(transactionDetailRepository.existsById(transactionDetail.getId())){
            return transactionDetailRepository.save(transactionDetail);
        }else{
            throw new RuntimeException("Transaction Detail Not Found");
        }
    }

    @Override
    public void deleteTransactionDetail(String id) {
        if(transactionDetailRepository.existsById(id)){
            transactionDetailRepository.deleteById(id);
        }else{
            throw new RuntimeException("Transaction Detail Not Found");
        }
    }
}
