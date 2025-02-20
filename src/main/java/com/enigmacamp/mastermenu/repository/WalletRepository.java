package com.enigmacamp.mastermenu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enigmacamp.mastermenu.model.entity.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, String> {
    <Optional>Wallet findByUser_Id(String userId);
} 