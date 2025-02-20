package com.enigmacamp.mastermenu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enigmacamp.mastermenu.model.entity.HistoryPay;

@Repository
public interface HistoryPayRepository extends JpaRepository<HistoryPay, String> {
    List<HistoryPay> findAllByCustomer_Id(String customerId);
}
