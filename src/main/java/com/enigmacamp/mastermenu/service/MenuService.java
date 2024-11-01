package com.enigmacamp.mastermenu.service;

import com.enigmacamp.mastermenu.model.dto.request.MenuReq;
import com.enigmacamp.mastermenu.model.dto.response.MenuDetailRes;
import com.enigmacamp.mastermenu.model.dto.response.MenuRes;

import java.util.List;

public interface MenuService {

    MenuRes createMenu(MenuReq menuReq);
    MenuDetailRes getMenuById(String id);
    MenuRes updateMenu(MenuReq menuReq);
    void deleteMenu(String id);
    List<MenuDetailRes> getAllMenu();

    List<MenuDetailRes> getAllMenuByCategory(String category_id);
}
