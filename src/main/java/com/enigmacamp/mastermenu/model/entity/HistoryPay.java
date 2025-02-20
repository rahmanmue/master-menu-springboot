package com.enigmacamp.mastermenu.model.entity;

import java.time.LocalDateTime;
import com.enigmacamp.mastermenu.utils.enums.EPaymentStatus;
import com.enigmacamp.mastermenu.utils.enums.EPaymentType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="mst_history_pay")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HistoryPay {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private String id;

    //join
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private EPaymentType paymentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private EPaymentStatus paymentStatus;

    @Column(name = "created_at",  nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }
}
