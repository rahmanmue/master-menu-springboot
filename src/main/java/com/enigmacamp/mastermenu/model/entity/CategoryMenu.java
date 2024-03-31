package com.enigmacamp.mastermenu.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "mst_category_menu")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

// custom delete
@SQLDelete(sql = "UPDATE mst_category_menu SET deleted = true WHERE category_id = ?")
public class CategoryMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id")
    private String id;
    private String name;
    private String description;
    @JsonIgnore
    private boolean deleted = Boolean.FALSE;
}
