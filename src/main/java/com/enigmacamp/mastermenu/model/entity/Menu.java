package com.enigmacamp.mastermenu.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "mst_menu")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

// custom delete
@SQLDelete(sql = "UPDATE mst_menu SET deleted = true WHERE menu_id = ?")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "menu_id")
    private String id;
    private String name;
    private String description;
    private Integer price;
    private Integer stock;
    // private String image;
    private boolean deleted = Boolean.FALSE;

    // join
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryMenu categoryMenu;

}
