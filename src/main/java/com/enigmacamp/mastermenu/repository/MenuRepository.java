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

    @Query("Select m from Menu m where m.deleted = false and LOWER(m.categoryMenu.name) LIKE LOWER(CONCAT('%',:categoryName, '%'))")
    List<Menu> getAllMenuByCategoryName(@Param("categoryName") String categoryName);

    @Query("Select m from Menu m where m.deleted = false and m.id = :menu_id")
    Menu findMenuByDeletedFalse(@Param("menu_id") String menu_id);

}
