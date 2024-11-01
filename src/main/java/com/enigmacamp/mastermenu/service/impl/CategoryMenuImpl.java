package com.enigmacamp.mastermenu.service.impl;

import com.enigmacamp.mastermenu.model.dto.request.CategoryMenuReq;
import com.enigmacamp.mastermenu.model.dto.response.CategoryMenuDetailRes;
import com.enigmacamp.mastermenu.model.dto.response.CategoryMenuRes;
import com.enigmacamp.mastermenu.model.entity.CategoryMenu;
import com.enigmacamp.mastermenu.repository.CategoryMenuRepository;
import com.enigmacamp.mastermenu.service.CategoryMenuService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryMenuImpl implements CategoryMenuService {

    private final CategoryMenuRepository categoryMenuRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryMenuRes saveCategoryMenu(CategoryMenuReq categoryMenuReq) {
        CategoryMenu categoryMenu = modelMapper.map(categoryMenuReq, CategoryMenu.class);
        CategoryMenu saved = categoryMenuRepository.save(categoryMenu);
        return modelMapper.map(saved, CategoryMenuRes.class);
    }

    @Override
    public List<CategoryMenuDetailRes> getAllCategoryMenu() {
        List<CategoryMenu> categoryMenus = categoryMenuRepository.getAllCategoryMenu();

        return categoryMenus.stream()
            .map(categoryMenu -> modelMapper.map(categoryMenu, CategoryMenuDetailRes.class))
            .toList();
    }

    @Override
    public List<CategoryMenuDetailRes> getCategoryMenuByName(String name) {
        List<CategoryMenu> categoryMenus = categoryMenuRepository.findCategoryMenuByName(name);
        
        if(categoryMenus == null){
            throw new EntityNotFoundException("Category with name " + name + " not found");
        }
        
        return categoryMenus.stream()
            .map(categoryMenu->modelMapper.map(categoryMenu, CategoryMenuDetailRes.class))
            .toList();
       
    }

    @Override
    public CategoryMenuDetailRes getCategoryMenuById(String id) {
        CategoryMenu categoryMenu = categoryMenuRepository.findCategoryMenuByDeletedFalse(id);
        
        if(categoryMenu == null){
            throw new EntityNotFoundException("Category with id " + id + "not found");
        }

        return modelMapper.map(categoryMenu, CategoryMenuDetailRes.class);
    }

    @Override
    public CategoryMenuRes updateCategoryMenu(CategoryMenuReq categoryMenuReq) {
        CategoryMenu existingCategory = categoryMenuRepository.findCategoryMenuByDeletedFalse(categoryMenuReq.getId()); 

        if(existingCategory == null){
            throw new EntityNotFoundException("Category with id "+categoryMenuReq.getId()+" Not Found");
        }

        modelMapper.map(categoryMenuReq, existingCategory);

        CategoryMenu updatedCategory = categoryMenuRepository.save(existingCategory);
        return modelMapper.map(updatedCategory, CategoryMenuRes.class);
    }

    @Override
    public void deleteCategoryMenu(String id) {
        if(categoryMenuRepository.findCategoryMenuByDeletedFalse(id) == null){
            throw new EntityNotFoundException("Category with id "+id+" Not Found");
        }

        categoryMenuRepository.deleteById(id);
    }

    
}
