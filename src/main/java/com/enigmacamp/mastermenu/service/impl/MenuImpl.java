package com.enigmacamp.mastermenu.service.impl;

import com.enigmacamp.mastermenu.model.entity.Menu;
import com.enigmacamp.mastermenu.repository.MenuRepository;
import com.enigmacamp.mastermenu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuImpl implements MenuService {

    private final MenuRepository menuRepository;
    @Override
    public Menu createMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public Menu getMenuById(String id) {
        return menuRepository.findMenuByDeletedFalse(id);
    }

    @Override
    public Menu updateMenu(Menu menu) {
        if(menuRepository.findMenuByDeletedFalse(menu.getId()) != null){
            return menuRepository.save(menu);
        }else{
            throw new RuntimeException("Menu with id "+menu.getId()+" Not Found");
        }
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
    public List<Menu> getAllMenu() {
        return menuRepository.getAllMenu();
    }

    @Override
    public List<Menu> getAllMenuByCategory(String category) {
        List<Menu> menuListByCategoryName = menuRepository.getAllMenu().stream()
                .filter(menu -> menu.getCategoryMenu().getName().equalsIgnoreCase(category))
                .toList();


        return menuListByCategoryName;
    }
}
