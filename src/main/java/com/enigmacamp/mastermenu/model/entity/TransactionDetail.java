package com.enigmacamp.mastermenu.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "mst_transaction_detail")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

// custom delete
@SQLDelete(sql = "UPDATE mst_transaction_detail SET deleted = true WHERE transaction_detail_id = ?")
public class TransactionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "transaction_detail_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    @JsonIgnoreProperties("transactionDetail")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private Integer price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "subtotal")
    private Integer subtotal;

    @JsonIgnore
    private boolean deleted = Boolean.FALSE;


    @Column(name= "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate 
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }  
    
    @PreRemove
    protected void onDelete(){
        updatedAt = LocalDateTime.now();
    }
}
