package com.enigmacamp.mastermenu.controller;

import com.enigmacamp.mastermenu.model.entity.Menu;
import com.enigmacamp.mastermenu.service.MenuService;
import com.enigmacamp.mastermenu.utils.constant.ApiPathConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPathConstant.API+ ApiPathConstant.VERSION + ApiPathConstant.MENU)
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public List<Menu> getAllMenu() {
        return menuService.getAllMenu();
    }

    @GetMapping("/by-category")
    public List<Menu> getAllMenuByCategory(@RequestParam(name="category") String category) {
        return menuService.getAllMenuByCategory(category);
    }

    @GetMapping("/{id}")
    public Menu getMenuById(@PathVariable String id) {
        return menuService.getMenuById(id);
    }

    @PostMapping
    public Menu createMenu(@RequestBody Menu menu) {
        return menuService.createMenu(menu);
    }

    @PutMapping
    public Menu updateMenu(@RequestBody Menu menu) {
        return menuService.updateMenu(menu);
    }

    @DeleteMapping("/{id}")
    public void deleteMenu(@PathVariable String id) {
        menuService.deleteMenu(id);
    }


}
