package com.enigmacamp.mastermenu.repository;

import com.enigmacamp.mastermenu.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    @Query("Select t from Transaction t where t.deleted = false")
    List<Transaction> getAllTransaction();

    @Query("SELECT t FROM Transaction t WHERE t.deleted = false AND LOWER(t.customer.fullName) LIKE LOWER(CONCAT('%', :customerName, '%'))")
    List<Transaction> findTransactionsByCustomerName(@Param("customerName") String customerName);

    @Query("Select t from Transaction t where t.deleted = false and t.id = :transaction_id")
    Transaction getTransactionById(@Param("transaction_id") String transaction_id);

    @Query("Select t from Transaction t where t.deleted = false and t.order.id = :order_id")
    Transaction getTransactionByOrderId(@Param("order_id") String order_id);
}

