package com.enigmacamp.mastermenu.service;

import com.enigmacamp.mastermenu.model.dto.request.CategoryMenuReq;
import com.enigmacamp.mastermenu.model.dto.response.CategoryMenuDetailRes;
import com.enigmacamp.mastermenu.model.dto.response.CategoryMenuRes;

import java.util.List;

public interface CategoryMenuService {
    CategoryMenuRes saveCategoryMenu(CategoryMenuReq categoryMenuReq);
    List<CategoryMenuDetailRes> getAllCategoryMenu();
    CategoryMenuDetailRes getCategoryMenuById(String id);
    List<CategoryMenuDetailRes> getCategoryMenuByName(String name);
    CategoryMenuRes updateCategoryMenu(CategoryMenuReq categoryMenuReq);
    void deleteCategoryMenu(String id);
}
