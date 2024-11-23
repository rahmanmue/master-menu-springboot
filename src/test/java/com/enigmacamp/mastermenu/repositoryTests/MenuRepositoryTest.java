package com.enigmacamp.mastermenu.repositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.enigmacamp.mastermenu.model.entity.CategoryMenu;
import com.enigmacamp.mastermenu.model.entity.Menu;
import com.enigmacamp.mastermenu.repository.CategoryMenuRepository;
import com.enigmacamp.mastermenu.repository.MenuRepository;

@ActiveProfiles("test")
@DataJpaTest
public class MenuRepositoryTest {
    
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private CategoryMenuRepository categoryMenuRepository;

    // Test Save
    @Test
    public void testSavedMenu(){
        CategoryMenu categoryMenu = CategoryMenu.builder()
                                    .name("Food A")
                                    .description("Description")
                                    .build();

        CategoryMenu savedCategoryMenu = categoryMenuRepository.save(categoryMenu);

        String name = "Menu A";
        String description = "Description";
        String imgUrl = "https://image-menu-a.com";
        int price = 1000;


        Menu menu = Menu.builder()
                    .imageUrl(imgUrl)
                    .name(name).price(price)
                    .description(description)
                    .categoryMenu(savedCategoryMenu)
                    .build();
        
        Menu savedMenu = menuRepository.save(menu);

        assertNotNull(savedMenu);
        assertNotNull(savedMenu.getId());
        assertEquals(imgUrl, savedMenu.getImageUrl());
        assertEquals(name, savedMenu.getName());
        assertEquals(price, savedMenu.getPrice());
        assertEquals(description, savedMenu.getDescription());
        assertEquals(categoryMenu.getId(), savedMenu.getCategoryMenu().getId());
    
    }


    // Test Get All
    @Test
    public void testGetAllMenu(){
        CategoryMenu categoryMenu = CategoryMenu.builder()
                                    .name("Food A")
                                    .description("Description")
                                    .build();

        CategoryMenu savedCategoryMenu = categoryMenuRepository.save(categoryMenu);


        Menu menuA = Menu.builder()
                        .imageUrl("https://image-a.com")
                        .name("Menu A")
                        .description("Description")
                        .price(15000)
                        .categoryMenu(savedCategoryMenu)
                        .build();
        
        Menu menuB = Menu.builder()
                        .imageUrl("https://image-b.com")
                        .name("Menu B")
                        .description("Description")
                        .price(10000)
                        .categoryMenu(savedCategoryMenu)
                        .build();

        menuRepository.save(menuA);
        menuRepository.save(menuB);

        List<Menu> menus = menuRepository.getAllMenu();
        assertNotNull(menus);
        assertEquals(2, menus.size());

    }


    // Test Find By Name 
    @Test
    public void testFindMenuByName(){
        String categoryName = "Spicy Food";
        CategoryMenu categoryMenu = CategoryMenu.builder()
                                    .name(categoryName)
                                    .description("Description")
                                    .build();

        CategoryMenu savedCategoryMenu = categoryMenuRepository.save(categoryMenu);


        Menu menuA = Menu.builder()
                    .imageUrl("https://image-a.com")
                    .name("Menu A")
                    .description("Description")
                    .price(15000)
                    .categoryMenu(savedCategoryMenu)
                    .build();

                    Menu menuB = Menu.builder()
                    .imageUrl("https://image-b.com")
                    .name("Menu B")
                    .description("Description")
                    .price(10000)
                    .categoryMenu(savedCategoryMenu)
                    .build();

        menuRepository.save(menuA);
        menuRepository.save(menuB);

        List<Menu> foundMenus = menuRepository.getAllMenuByCategoryName(categoryName);
        assertNotNull(foundMenus);
        assertEquals(2, foundMenus.size());
        assertEquals("Menu A", foundMenus.get(0).getName());

    }


    // Test Soft Delete
    @Test
    public void testSoftDeleteMenu(){
        CategoryMenu categoryMenu = CategoryMenu.builder()
                                    .name("Food A")
                                    .description("Description")
                                    .build();

        CategoryMenu savedCategoryMenu = categoryMenuRepository.save(categoryMenu);


        Menu menu = Menu.builder()
                    .imageUrl("https://image-a.com")
                    .name("Menu A")
                    .description("Description")
                    .price(15000)
                    .categoryMenu(savedCategoryMenu)
                    .build();
        
        Menu savedMenu = menuRepository.save(menu);

        menuRepository.deleteById(savedMenu.getId());

        List<Menu> menus = menuRepository.getAllMenu();
        assertTrue(menus.isEmpty());


        Optional<Menu> deleteMenu = menuRepository.findById(savedMenu.getId());
        assertTrue(deleteMenu.isPresent());
        assertTrue(deleteMenu.get().isDeleted());

        Optional<CategoryMenu> categoryMenus = categoryMenuRepository.findById(savedCategoryMenu.getId());
        assertTrue(categoryMenus.isPresent());

    }
}
