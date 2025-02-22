package com.enigmacamp.mastermenu.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.enigmacamp.mastermenu.model.dtos.historyPay.HistoryPayRes;
import com.enigmacamp.mastermenu.model.entity.Customer;
import com.enigmacamp.mastermenu.model.entity.HistoryPay;
import com.enigmacamp.mastermenu.model.entity.User;
import com.enigmacamp.mastermenu.model.entity.Wallet;
import com.enigmacamp.mastermenu.repository.CustomerRepository;
import com.enigmacamp.mastermenu.repository.HistoryPayRepository;
import com.enigmacamp.mastermenu.repository.UserRepository;
import com.enigmacamp.mastermenu.repository.WalletRepository;
import com.enigmacamp.mastermenu.service.HistoryPayService;
import com.enigmacamp.mastermenu.utils.enums.EPaymentStatus;
import com.enigmacamp.mastermenu.utils.enums.EPaymentType;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryPayImpl implements HistoryPayService {
    private final HistoryPayRepository historyPayRepository;
    private final CustomerRepository customerRepository;
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<HistoryPayRes> getAllHistoryPay(String email){
        User user = userRepository.findByEmail(email).orElseThrow(
            () -> new EntityNotFoundException("user not found")
        );
     
        Customer customer = customerRepository.findByUser_Id(user.getId());

        if(customer.getId().isEmpty()){
            throw new EntityNotFoundException("customer not found");
        }

        List<HistoryPay> histories = historyPayRepository.findAllByCustomer_Id(customer.getId());
        if(histories.isEmpty()){
            throw new EntityNotFoundException("Customer with id " + customer.getId() + " not found");
        }
        return histories.stream()
            .map((history) -> modelMapper.map(history, HistoryPayRes.class))
            .toList();
       
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
