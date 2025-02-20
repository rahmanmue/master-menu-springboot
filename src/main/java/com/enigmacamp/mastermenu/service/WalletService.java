package com.enigmacamp.mastermenu.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.enigmacamp.mastermenu.model.entity.User;
import com.enigmacamp.mastermenu.model.entity.Wallet;
import com.enigmacamp.mastermenu.repository.UserRepository;
import com.enigmacamp.mastermenu.repository.WalletRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    public Wallet getWalletUser(String email){
        Optional<User> user = userRepository.findByEmail(email);
        return walletRepository.findByUser_Id(user.get().getId());
    }
}
