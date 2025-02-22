package com.enigmacamp.mastermenu.service;
import com.enigmacamp.mastermenu.model.dtos.wallet.WalletRes;

public interface WalletService {
    WalletRes getWalletUser(String email);
} 