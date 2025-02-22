package com.enigmacamp.mastermenu.service;

import com.enigmacamp.mastermenu.model.dtos.category.CategoryMenuDetailRes;
import com.enigmacamp.mastermenu.model.dtos.category.CategoryMenuReq;
import com.enigmacamp.mastermenu.model.dtos.category.CategoryMenuRes;

import java.util.List;

public interface CategoryMenuService {
    CategoryMenuRes saveCategoryMenu(CategoryMenuReq categoryMenuReq);
    List<CategoryMenuDetailRes> getAllCategoryMenu();
    CategoryMenuDetailRes getCategoryMenuById(String id);
    List<CategoryMenuDetailRes> getCategoryMenuByName(String name);
    CategoryMenuRes updateCategoryMenu(String id, CategoryMenuReq categoryMenuReq);
    void deleteCategoryMenu(String id);
}
