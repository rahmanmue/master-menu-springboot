package com.enigmacamp.mastermenu.service.impl;

import com.enigmacamp.mastermenu.model.dto.request.MenuReq;
import com.enigmacamp.mastermenu.model.dto.response.MenuDetailRes;
import com.enigmacamp.mastermenu.model.dto.response.MenuRes;
import com.enigmacamp.mastermenu.model.entity.CategoryMenu;
import com.enigmacamp.mastermenu.model.entity.Menu;
import com.enigmacamp.mastermenu.repository.MenuRepository;
import com.enigmacamp.mastermenu.service.MenuService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;

    @Override
    public MenuRes createMenu(MenuReq menuReq) {
        Menu menu = modelMapper.map(menuReq, Menu.class);
        CategoryMenu categoryMenu = CategoryMenu.builder().id(menuReq.getCategoryId()).build();
        menu.setCategoryMenu(categoryMenu);
        Menu saved =  menuRepository.save(menu);
        return modelMapper.map(saved, MenuRes.class);
    }

    @Override
    public MenuDetailRes getMenuById(String id) {
        Menu menu = menuRepository.findMenuByDeletedFalse(id);

        if (menu == null) {
            throw new EntityNotFoundException("Menu with id " + id + " not found");
        }

        return modelMapper.map(menu, MenuDetailRes.class);
    }

    @Override
    public MenuRes updateMenu(MenuReq menuReq) {
        Menu existingMenu = menuRepository.findMenuByDeletedFalse(menuReq.getId());

        if(existingMenu == null){
            throw new RuntimeException("Menu with id "+ menuReq.getId()+" Not Found");
        }

        modelMapper.map(menuReq, existingMenu);

        Menu updatedMenu = menuRepository.save(existingMenu);

        return modelMapper.map(updatedMenu, MenuRes.class);
    }

    @Override
    public void deleteMenu(String id) {
        if(menuRepository.findMenuByDeletedFalse(id) != null){
            menuRepository.deleteById(id);
        }else{
            throw new RuntimeException("Menu with id "+id+" Not Found");
        }
    }

    @Override
    public List<MenuDetailRes> getAllMenu() {
        List<Menu> menus = menuRepository.getAllMenu();

        return menus.stream()
            .map(menu -> modelMapper.map(menu, MenuDetailRes.class))
            .toList();
    }

    @Override
    public List<MenuDetailRes> getAllMenuByCategory(String category) {
        return menuRepository.getAllMenuByCategoryName(category).stream()
                .map(menu -> modelMapper.map(menu, MenuDetailRes.class))
                .toList();
    }
}
