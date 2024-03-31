package com.enigmacamp.mastermenu.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "mst_transaction")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
// custom delete
@SQLDelete(sql = "UPDATE mst_transaction SET deleted = true WHERE transaction_id = ?")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "transaction_id")
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
    @Column(name = "transaction_date")
    private Date transactionDate;
    @Column(name = "total_price")
    private Integer totalPrice;
    @Column(name = "total_item")
    private Integer totalItem;

    @JsonIgnore
    private boolean deleted = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToMany(mappedBy = "transaction")
    @JsonIgnoreProperties("transaction")
    private List<TransactionDetail> transactionDetail;

}
