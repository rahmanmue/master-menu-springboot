package com.enigmacamp.mastermenu.service;

import com.enigmacamp.mastermenu.model.entity.CategoryMenu;

import java.util.List;

public interface CategoryMenuService {
    CategoryMenu saveCategoryMenu(CategoryMenu categoryMenu);
    List<CategoryMenu> getAllCategoryMenu();
    CategoryMenu getCategoryMenuById(String id);
    CategoryMenu updateCategoryMenu(CategoryMenu categoryMenu);
    void deleteCategoryMenu(String id);


}
