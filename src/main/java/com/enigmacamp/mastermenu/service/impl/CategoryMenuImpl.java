package com.enigmacamp.mastermenu.service.impl;

import com.enigmacamp.mastermenu.model.entity.CategoryMenu;
import com.enigmacamp.mastermenu.repository.CategoryMenuRepository;
import com.enigmacamp.mastermenu.service.CategoryMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryMenuImpl implements CategoryMenuService {

    private final CategoryMenuRepository categoryMenuRepository;
    @Override
    public CategoryMenu saveCategoryMenu(CategoryMenu categoryMenu) {
        return categoryMenuRepository.save(categoryMenu);
    }

    @Override
    public List<CategoryMenu> getAllCategoryMenu() {
        return categoryMenuRepository.getAllCategoryMenu();
    }

    @Override
    public CategoryMenu getCategoryMenuById(String id) {
        return categoryMenuRepository.findCategoryMenuByDeletedFalse(id);
    }

    @Override
    public CategoryMenu updateCategoryMenu(CategoryMenu categoryMenu) {
        if(categoryMenuRepository.findCategoryMenuByDeletedFalse(categoryMenu.getId()) != null){
            return categoryMenuRepository.save(categoryMenu);
        }else{
            throw new RuntimeException("Category with id "+categoryMenu.getId()+" Not Found");
        }
    }

    @Override
    public void deleteCategoryMenu(String id) {
        if(categoryMenuRepository.findCategoryMenuByDeletedFalse(id) != null){
            categoryMenuRepository.deleteById(id);
        }else{
            throw new RuntimeException("Category with id "+id+" Not Found");
        }
    }
}
