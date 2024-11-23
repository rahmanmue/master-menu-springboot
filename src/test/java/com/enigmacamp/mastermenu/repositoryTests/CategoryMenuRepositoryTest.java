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
import com.enigmacamp.mastermenu.repository.CategoryMenuRepository;

@ActiveProfiles("test")
@DataJpaTest
public class CategoryMenuRepositoryTest {
    
    @Autowired
    private CategoryMenuRepository categoryMenuRepository;

    // Test Save
    @Test
    public void testSavedCategoryMenu(){
        String name = "Spicy Food";
        String description = "This is Spicy Food";

        CategoryMenu categoryMenu = CategoryMenu.builder()
                                    .name(name)
                                    .description(description)
                                    .build();
        
        CategoryMenu savedCategoryMenu = categoryMenuRepository.save(categoryMenu);

        assertNotNull(savedCategoryMenu);
        assertNotNull(savedCategoryMenu.getId());
        assertEquals(name, savedCategoryMenu.getName());
        assertEquals(description, savedCategoryMenu.getDescription());
    }

    // Test Get All
    @Test
    public void testGetAllCategoryMenu(){
        CategoryMenu categoryMenu1 = CategoryMenu.builder()
                                    .name("Food A")
                                    .description("Description Food A")
                                    .build();
        CategoryMenu categoryMenu2 = CategoryMenu.builder()
                                    .name("Food B")
                                    .description("Description Food B")
                                    .build();

        categoryMenuRepository.save(categoryMenu1);
        categoryMenuRepository.save(categoryMenu2);


        List<CategoryMenu> categoryMenus = categoryMenuRepository.getAllCategoryMenu();
        assertNotNull(categoryMenus);
        assertEquals(2, categoryMenus.size());
    }


    // Test Find By Name 
    @Test
    public void testFindCategoryMenuByName(){
        String name = "Spicy Food";
        CategoryMenu categoryMenu = CategoryMenu.builder()
                                    .name(name)
                                    .description("Description")
                                    .build();
        categoryMenuRepository.save(categoryMenu);

        List<CategoryMenu> foundCategoryMenus = categoryMenuRepository.findCategoryMenuByName(name);

        assertNotNull(foundCategoryMenus);
        assertEquals(1, foundCategoryMenus.size());
        assertEquals(name, foundCategoryMenus.get(0).getName());
    }


    // Test Soft Delete
    @Test
    public void testSoftDeleteCategoryMenu(){
        CategoryMenu categoryMenu = CategoryMenu.builder()
                                    .name("Food A")
                                    .description("Description")
                                    .build();
        
        CategoryMenu savedCategoryMenu = categoryMenuRepository.save(categoryMenu);

        categoryMenuRepository.deleteById(savedCategoryMenu.getId());

        List<CategoryMenu> categoryMenus = categoryMenuRepository.getAllCategoryMenu();
        assertTrue(categoryMenus.isEmpty());

        Optional<CategoryMenu> deleteCategoryMenu = categoryMenuRepository.findById(savedCategoryMenu.getId());
        assertTrue(deleteCategoryMenu.isPresent());
        assertTrue(deleteCategoryMenu.get().isDeleted());
    }


    



}
