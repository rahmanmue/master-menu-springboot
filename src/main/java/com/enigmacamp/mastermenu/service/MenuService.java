package com.enigmacamp.mastermenu.service;

import com.enigmacamp.mastermenu.model.dtos.menu.MenuDetailRes;
import com.enigmacamp.mastermenu.model.dtos.menu.MenuReq;
import com.enigmacamp.mastermenu.model.dtos.menu.MenuRes;

import java.util.List;

public interface MenuService {

    MenuRes createMenu(MenuReq menuReq);
    MenuDetailRes getMenuById(String id);
    MenuRes updateMenu(String id, MenuReq menuReq);
    void deleteMenu(String id);
    List<MenuDetailRes> getAllMenu();

    List<MenuDetailRes> getAllMenuByCategory(String category_id);
}
