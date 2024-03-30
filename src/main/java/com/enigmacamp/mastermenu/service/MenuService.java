package com.enigmacamp.mastermenu.service;

import com.enigmacamp.mastermenu.model.entity.Menu;

import java.util.List;

public interface MenuService {

    Menu createMenu(Menu menu);
    Menu getMenuById(String id);
    Menu updateMenu(Menu menu);
    void deleteMenu(String id);
    List<Menu> getAllMenu();

    List<Menu> getAllMenuByCategory(String category_id);
}
