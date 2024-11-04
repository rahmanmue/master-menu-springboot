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

import java.time.LocalDateTime;
import java.util.Date;


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
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EOrderStatus status;

    @JsonIgnore
    private boolean deleted = Boolean.FALSE;

    // join
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @Column(name= "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        if (date == null) {
            date = new Date();
        }
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
