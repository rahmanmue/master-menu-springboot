package com.enigmacamp.mastermenu.model.dtos.wallet;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class WalletRes {
    private String userId;
    private Double balance;
    private LocalDateTime updatedAt;
}