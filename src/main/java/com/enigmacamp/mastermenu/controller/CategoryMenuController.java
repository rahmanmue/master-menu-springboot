package com.enigmacamp.mastermenu.controller;

import com.enigmacamp.mastermenu.model.entity.CategoryMenu;
import com.enigmacamp.mastermenu.service.CategoryMenuService;
import com.enigmacamp.mastermenu.utils.constant.ApiPathConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPathConstant.API+ ApiPathConstant.VERSION + ApiPathConstant.CATEGORY)
public class CategoryMenuController {

    private final CategoryMenuService categoryMenuService;

    @GetMapping
    public List<CategoryMenu> getAllCategory() {
        return categoryMenuService.getAllCategoryMenu();
    }

    @GetMapping("/{id}")
    public CategoryMenu getCategoryMenuById(@PathVariable String id) {
        return categoryMenuService.getCategoryMenuById(id);
    }

    @PostMapping
    public CategoryMenu saveCategoryMenu(@RequestBody CategoryMenu categoryMenu) {
        return categoryMenuService.saveCategoryMenu(categoryMenu);
    }

    @PutMapping
    public CategoryMenu updateCategoryMenu(@RequestBody CategoryMenu categoryMenu) {
        return categoryMenuService.updateCategoryMenu(categoryMenu);
    }

    @DeleteMapping("/{id}")
    public void deleteCategoryMenu(@PathVariable String id) {
        categoryMenuService.deleteCategoryMenu(id);
    }



}
