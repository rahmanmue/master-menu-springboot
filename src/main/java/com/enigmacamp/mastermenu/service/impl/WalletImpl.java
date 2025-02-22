package com.enigmacamp.mastermenu.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.enigmacamp.mastermenu.model.dtos.wallet.WalletRes;
import com.enigmacamp.mastermenu.model.entity.User;
import com.enigmacamp.mastermenu.model.entity.Wallet;
import com.enigmacamp.mastermenu.repository.UserRepository;
import com.enigmacamp.mastermenu.repository.WalletRepository;
import com.enigmacamp.mastermenu.service.WalletService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public WalletRes getWalletUser(String email){
        Optional<User> user = userRepository.findByEmail(email);
        Wallet wallet = walletRepository.findByUser_Id(user.get().getId());
        return modelMapper.map(wallet, WalletRes.class);
    }
}
