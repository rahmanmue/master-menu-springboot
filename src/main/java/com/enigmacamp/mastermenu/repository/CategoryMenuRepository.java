package com.enigmacamp.mastermenu.repository;

import com.enigmacamp.mastermenu.model.entity.CategoryMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryMenuRepository extends JpaRepository<CategoryMenu, String> {

//    @Query("Delete from CategoryMenu c where c.id = :category_id")
//    void deleteCategoryMenu(@Param("category_id") String category_id);

    @Query("Select c from CategoryMenu c where c.deleted = false")
    List<CategoryMenu> getAllCategoryMenu();

    @Query("Select c from CategoryMenu c where c.deleted = false and c.id = :category_id")
    CategoryMenu findCategoryMenuByDeletedFalse(@Param("category_id") String category_id);


}
