package com.enigmacamp.mastermenu.model.entity;

import com.enigmacamp.mastermenu.utils.enums.EOrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDate;
import java.util.Date;

import static java.util.Date.*;

@Entity
@Table(name = "mst_order")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

// custom delete
@SQLDelete(sql = "UPDATE mst_order SET deleted = true WHERE order_id = ?")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
    private Date date = java.sql.Date.valueOf(LocalDate.now());

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EOrderStatus status;

    @JsonIgnore
    private boolean deleted = Boolean.FALSE;

    // join
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
