package com.enigmacamp.mastermenu.model.entity;

import com.enigmacamp.mastermenu.utils.enums.ERole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mst_role")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "role_id")
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ERole role;
}
