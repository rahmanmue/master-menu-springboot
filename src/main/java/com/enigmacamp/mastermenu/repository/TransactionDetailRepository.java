package com.enigmacamp.mastermenu.repository;

import com.enigmacamp.mastermenu.model.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, String> {
    @Query("Select t from TransactionDetail t where t.deleted = false")
    List<TransactionDetailRepository> getAllTransactionDetail();

    @Query("Select t from TransactionDetail t where t.deleted = false and t.id = :transaction_detail_id")
    TransactionDetail getTransactionDetailById(@Param("transaction_detail_id") String transaction_detail_id);

}
