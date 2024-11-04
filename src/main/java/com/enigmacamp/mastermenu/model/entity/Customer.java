package com.enigmacamp.mastermenu.model.entity;

import com.enigmacamp.mastermenu.utils.enums.EGender;
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
@Table(name = "mst_customer")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

// custom delete
@SQLDelete(sql = "UPDATE mst_customer SET deleted = true WHERE customer_id = ?")
public class Customer {
    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name="full_name")
    private String fullName;
    @Column(name = "date_of_birth")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
    private Date dateOfBirth;
    private String address;
    private String phone;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private EGender gender;
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

    //join
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
