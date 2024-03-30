package com.enigmacamp.mastermenu.model.entity;

import com.enigmacamp.mastermenu.utils.enums.EGender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.util.Date;

@Entity
@Table(name = "mst_employee")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
// custom delete
@SQLDelete(sql = "UPDATE mst_employee SET deleted = true WHERE employee_id = ?")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "employee_id")
    private String id;
    @Column(nullable = false, unique = true)
    private String nip;
    private String position;
    @Column(name="full_name")
    private String fullName;
    @Column(name = "date_of_birth")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
    private Date dateOfBirth;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private EGender gender;
    private String phone;
    private String address;
    private boolean deleted = Boolean.FALSE;

//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private User user;
}
