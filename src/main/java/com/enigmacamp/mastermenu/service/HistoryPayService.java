package com.enigmacamp.mastermenu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.enigmacamp.mastermenu.model.entity.Customer;
import com.enigmacamp.mastermenu.model.entity.HistoryPay;
import com.enigmacamp.mastermenu.model.entity.User;
import com.enigmacamp.mastermenu.model.entity.Wallet;
import com.enigmacamp.mastermenu.repository.CustomerRepository;
import com.enigmacamp.mastermenu.repository.HistoryPayRepository;
import com.enigmacamp.mastermenu.repository.UserRepository;
import com.enigmacamp.mastermenu.repository.WalletRepository;
import com.enigmacamp.mastermenu.utils.enums.EPaymentStatus;
import com.enigmacamp.mastermenu.utils.enums.EPaymentType;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryPayService {
    private final HistoryPayRepository historyPayRepository;
    private final CustomerRepository customerRepository;
    private final WalletRepository walletRepository;

    public List<HistoryPay> getAllHistoryPay(String customerId){
        return historyPayRepository.findAllByCustomer_Id(customerId);
    }

    @Transactional
    public HistoryPay createHistoryPayTopUp(
        String OrderId, String amount, EPaymentType ePaymentType, EPaymentStatus ePaymentStatus){
            String customerId = OrderId.split("::")[0];
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new EntityNotFoundException("Customer Not Found")
            );
            
            String userId = customer.getUser().getId();
           
            if(ePaymentStatus == EPaymentStatus.SUCCESS){
                Wallet walletUser = walletRepository.findByUser_Id(userId);
                walletUser.setBalance(walletUser.getBalance() + Double.parseDouble(amount));
                walletRepository.save(walletUser);
            }

            HistoryPay historyPay = HistoryPay.builder()
                .customer(customer)
                .amount(Double.parseDouble(amount))
                .paymentStatus(ePaymentStatus)
                .paymentType(ePaymentType)
                .build();

            return historyPayRepository.save(historyPay);

    }


    @Transactional
    public HistoryPay createHistoryPayPayment(Customer customer, int price, EPaymentType ePaymentType, EPaymentStatus ePaymentStatus){
        String userId = customer.getUser().getId();

        if(ePaymentStatus == EPaymentStatus.FAILED){
            Wallet walletUser = walletRepository.findByUser_Id(userId);
            walletUser.setBalance(walletUser.getBalance() + Double.valueOf(price));
            walletRepository.save(walletUser);
        }else if(ePaymentStatus == EPaymentStatus.SUCCESS){
            Wallet walletUser = walletRepository.findByUser_Id(userId);
            walletUser.setBalance(walletUser.getBalance() - Double.valueOf(price));
            walletRepository.save(walletUser);
        }

        HistoryPay historyPay = HistoryPay.builder()
            .customer(customer)
            .amount(Double.valueOf(price))
            .paymentStatus(ePaymentStatus)
            .paymentType(ePaymentType)
            .build();

        return historyPayRepository.save(historyPay);

    }

       

       
}
