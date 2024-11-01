package com.enigmacamp.mastermenu.service.impl;

import com.enigmacamp.mastermenu.model.entity.TransactionDetail;
import com.enigmacamp.mastermenu.repository.TransactionDetailRepository;
import com.enigmacamp.mastermenu.service.TransactionDetailService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TransactionDetailImpl implements TransactionDetailService {

    private final TransactionDetailRepository transactionDetailRepository;


    @Override
    public TransactionDetail getTransactionDetailById(String id) {
        TransactionDetail transactionDetail = transactionDetailRepository.getTransactionDetailById(id);

        if(transactionDetail == null){
            throw new EntityNotFoundException("Transaction Detail Not Found");
        }

        return transactionDetail; 
    }

    @Override
    public TransactionDetail saveTransactionDetail(TransactionDetail transactionDetail) {
        return transactionDetailRepository.save(transactionDetail);
    }

    @Override
    public TransactionDetail updateTransactionDetail(TransactionDetail transactionDetail) {
        
        if(!transactionDetailRepository.existsById(transactionDetail.getId())){
            throw new EntityNotFoundException("Transaction Detail Not Found");
        }

        return transactionDetailRepository.save(transactionDetail);
    }

    @Override
    public void deleteTransactionDetail(String id) {

        if(!transactionDetailRepository.existsById(id)){
            throw new EntityNotFoundException("Transaction Detail Not Found");
        }

        transactionDetailRepository.deleteById(id);
    }
}
