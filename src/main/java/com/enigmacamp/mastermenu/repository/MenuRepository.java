package com.enigmacamp.mastermenu.repository;

import com.enigmacamp.mastermenu.model.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {

    @Query("Select m from Menu m where m.deleted = false")
    List<Menu> getAllMenu();

    @Query("Select m from Menu m where m.deleted = false and m.categoryMenu.id = :category_id")
    List<Menu> getAllMenuByCategory(@Param("category_id") String category_id);

    @Query("Select m from Menu m where m.deleted = false and m.id = :menu_id")
    Menu findMenuByDeletedFalse(@Param("menu_id") String menu_id);

}
