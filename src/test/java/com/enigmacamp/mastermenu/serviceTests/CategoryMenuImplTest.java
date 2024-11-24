package com.enigmacamp.mastermenu.serviceTests;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.enigmacamp.mastermenu.model.dto.request.CategoryMenuReq;
import com.enigmacamp.mastermenu.model.dto.response.CategoryMenuDetailRes;
import com.enigmacamp.mastermenu.model.dto.response.CategoryMenuRes;
import com.enigmacamp.mastermenu.model.entity.CategoryMenu;
import com.enigmacamp.mastermenu.repository.CategoryMenuRepository;
import com.enigmacamp.mastermenu.service.impl.CategoryMenuImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
// import org.junit.jupiter.api.BeforeEach;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class CategoryMenuImplTest {

    @Mock
    private CategoryMenuRepository categoryMenuRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CategoryMenuImpl categoryMenuService;

    // private CategoryMenuRepository categoryMenuRepository;
    // private ModelMapper modelMapper;
    // private CategoryMenuImpl categoryMenuService;

    // @BeforeEach
    // void setUp() {
    //     categoryMenuRepository = mock(CategoryMenuRepository.class);
    //     modelMapper = mock(ModelMapper.class);
    //     categoryMenuService = new CategoryMenuImpl(categoryMenuRepository, modelMapper);
    // }

    @Test
    void saveCategoryMenu_ShouldSaveAndReturnCategoryMenuRes() {
        // Arrange
        CategoryMenuReq req = new CategoryMenuReq();
        req.setName("Beverages");
        req.setDescription("Drinks and juices");

        CategoryMenu categoryMenu = new CategoryMenu();
        categoryMenu.setName("Beverages");

        CategoryMenu savedCategory = new CategoryMenu();
        savedCategory.setName("Beverages");

        CategoryMenuRes res = new CategoryMenuRes();
        res.setName("Beverages");

        when(modelMapper.map(req, CategoryMenu.class)).thenReturn(categoryMenu);
        when(categoryMenuRepository.save(categoryMenu)).thenReturn(savedCategory);
        when(modelMapper.map(savedCategory, CategoryMenuRes.class)).thenReturn(res);

        // Act
        CategoryMenuRes result = categoryMenuService.saveCategoryMenu(req);

        // Assert
        assertNotNull(result);
        assertEquals("Beverages", result.getName());
        verify(modelMapper).map(req, CategoryMenu.class);
        verify(categoryMenuRepository).save(categoryMenu);
        verify(modelMapper).map(savedCategory, CategoryMenuRes.class);
    }

    @Test
    void getAllCategoryMenu_ShouldReturnListOfCategoryMenuDetailRes() {
        // Arrange
        CategoryMenu category1 = new CategoryMenu();
        category1.setName("Beverages");

        CategoryMenu category2 = new CategoryMenu();
        category2.setName("Snacks");

        List<CategoryMenu> categories = List.of(category1, category2);

        CategoryMenuDetailRes res1 = new CategoryMenuDetailRes();
        res1.setName("Beverages");

        CategoryMenuDetailRes res2 = new CategoryMenuDetailRes();
        res2.setName("Snacks");

        when(categoryMenuRepository.getAllCategoryMenu()).thenReturn(categories);
        when(modelMapper.map(category1, CategoryMenuDetailRes.class)).thenReturn(res1);
        when(modelMapper.map(category2, CategoryMenuDetailRes.class)).thenReturn(res2);

        // Act
        List<CategoryMenuDetailRes> result = categoryMenuService.getAllCategoryMenu();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Beverages", result.get(0).getName());
        assertEquals("Snacks", result.get(1).getName());
        verify(categoryMenuRepository).getAllCategoryMenu();
        verify(modelMapper).map(category1, CategoryMenuDetailRes.class);
        verify(modelMapper).map(category2, CategoryMenuDetailRes.class);
    }

    @Test
    void getCategoryMenuByName_ShouldReturnListOfCategoryMenuDetailRes() {
        // Arrange
        String name = "Beverages";

        CategoryMenu category = new CategoryMenu();
        category.setName(name);

        List<CategoryMenu> categories = List.of(category);

        CategoryMenuDetailRes res = new CategoryMenuDetailRes();
        res.setName(name);

        when(categoryMenuRepository.findCategoryMenuByName(name)).thenReturn(categories);
        when(modelMapper.map(category, CategoryMenuDetailRes.class)).thenReturn(res);

        // Act
        List<CategoryMenuDetailRes> result = categoryMenuService.getCategoryMenuByName(name);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(name, result.get(0).getName());
        verify(categoryMenuRepository).findCategoryMenuByName(name);
        verify(modelMapper).map(category, CategoryMenuDetailRes.class);
    }

    @Test
    void getCategoryMenuById_ShouldReturnCategoryMenuDetailRes() {
        // Arrange
        String id = "1";

        CategoryMenu category = new CategoryMenu();
        category.setId(id);
        category.setName("Beverages");

        CategoryMenuDetailRes res = new CategoryMenuDetailRes();
        res.setId(id);
        res.setName("Beverages");

        when(categoryMenuRepository.findCategoryMenuByDeletedFalse(id)).thenReturn(category);
        when(modelMapper.map(category, CategoryMenuDetailRes.class)).thenReturn(res);

        // Act
        CategoryMenuDetailRes result = categoryMenuService.getCategoryMenuById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Beverages", result.getName());
        verify(categoryMenuRepository).findCategoryMenuByDeletedFalse(id);
        verify(modelMapper).map(category, CategoryMenuDetailRes.class);
    }

    @Test
    void updateCategoryMenu_ShouldUpdateAndReturnCategoryMenuRes() {
        // Arrange
        CategoryMenuReq req = new CategoryMenuReq();
        req.setId("1");
        req.setName("Updated Name");

        CategoryMenu existingCategory = new CategoryMenu();
        existingCategory.setId("1");
        existingCategory.setName("Old Name");

        CategoryMenu updatedCategory = new CategoryMenu();
        updatedCategory.setId("1");
        updatedCategory.setName("Updated Name");

        CategoryMenuRes res = new CategoryMenuRes();
        res.setName("Updated Name");

        when(categoryMenuRepository.findCategoryMenuByDeletedFalse("1")).thenReturn(existingCategory);
        when(categoryMenuRepository.save(existingCategory)).thenReturn(updatedCategory);
        when(modelMapper.map(updatedCategory, CategoryMenuRes.class)).thenReturn(res);

        // Act
        CategoryMenuRes result = categoryMenuService.updateCategoryMenu(req);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        verify(categoryMenuRepository).findCategoryMenuByDeletedFalse("1");
        verify(modelMapper).map(req, existingCategory);
        verify(categoryMenuRepository).save(existingCategory);
        verify(modelMapper).map(updatedCategory, CategoryMenuRes.class);
    }

    @Test
    void deleteCategoryMenu_ShouldDeleteCategory() {
        // Arrange
        String id = "1";

        when(categoryMenuRepository.findCategoryMenuByDeletedFalse(id)).thenReturn(new CategoryMenu());

        // Act
        categoryMenuService.deleteCategoryMenu(id);

        // Assert
        verify(categoryMenuRepository).findCategoryMenuByDeletedFalse(id);
        verify(categoryMenuRepository).deleteById(id);
    }

    @Test
    void deleteCategoryMenu_ShouldThrowExceptionWhenNotFound() {
        // Arrange
        String id = "999";

        when(categoryMenuRepository.findCategoryMenuByDeletedFalse(id)).thenReturn(null);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> categoryMenuService.deleteCategoryMenu(id));

        assertEquals("Category with id 999 Not Found", exception.getMessage());
        verify(categoryMenuRepository).findCategoryMenuByDeletedFalse(id);
        verify(categoryMenuRepository, never()).deleteById(anyString());
    }
}

