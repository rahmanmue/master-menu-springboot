package com.enigmacamp.mastermenu.serviceTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.enigmacamp.mastermenu.model.dtos.menu.MenuDetailRes;
import com.enigmacamp.mastermenu.model.dtos.menu.MenuReq;
import com.enigmacamp.mastermenu.model.dtos.menu.MenuRes;
import com.enigmacamp.mastermenu.model.entity.CategoryMenu;
import com.enigmacamp.mastermenu.model.entity.Menu;
import com.enigmacamp.mastermenu.repository.MenuRepository;
import com.enigmacamp.mastermenu.service.CategoryMenuService;
import com.enigmacamp.mastermenu.service.impl.MenuImpl;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;



@ExtendWith(MockitoExtension.class)
class MenuImplTest {

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private CategoryMenuService categoryMenuService; // Dependensi untuk kategori

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ModelMapper modelMapper;

    @InjectMocks
    private MenuImpl menuService;

    @Test
    void createMenu_ShouldSaveAndReturnMenuRes() {
        // Arrange
        MenuReq menuReq = new MenuReq();
        menuReq.setName("Latte");
        menuReq.setPrice(50000);
        menuReq.setStock(10);
        menuReq.setCategoryId("cat1");

        Menu menuEntity = new Menu();
        menuEntity.setName("Latte");

        CategoryMenu categoryMenu = CategoryMenu.builder().id("cat1").build();
        menuEntity.setCategoryMenu(categoryMenu);

        Menu savedMenu = new Menu();
        savedMenu.setId("1");
        savedMenu.setName("Latte");

        MenuRes menuRes = new MenuRes();
        menuRes.setId("1");

        // Mocking
        when(modelMapper.map(menuReq, Menu.class)).thenReturn(menuEntity);
        when(menuRepository.save(menuEntity)).thenReturn(savedMenu);
        when(modelMapper.map(savedMenu, MenuRes.class)).thenReturn(menuRes);

        // Act
        MenuRes result = menuService.createMenu(menuReq);

        // Assert
        assertNotNull(result);
        assertEquals("1", result.getId());

        verify(modelMapper).map(menuReq, Menu.class);
        verify(menuRepository).save(menuEntity);
        verify(modelMapper).map(savedMenu, MenuRes.class);
    }


    @Test
    void getMenuById_ShouldReturnMenuDetailRes() {
        // Arrange
        String menuId = "menu1";

        Menu menuEntity = new Menu();
        menuEntity.setId(menuId);
        menuEntity.setName("Espresso");

        MenuDetailRes menuDetailRes = new MenuDetailRes();
        menuDetailRes.setId(menuId);
        menuDetailRes.setName("Espresso");

        // Mocking
        when(menuRepository.findMenuByDeletedFalse(menuId)).thenReturn(menuEntity);
        when(modelMapper.map(menuEntity, MenuDetailRes.class)).thenReturn(menuDetailRes);

        // Act
        MenuDetailRes result = menuService.getMenuById(menuId);

        // Assert
        assertNotNull(result);
        assertEquals("Espresso", result.getName());

        verify(menuRepository).findMenuByDeletedFalse(menuId);
        verify(modelMapper).map(menuEntity, MenuDetailRes.class);
    }

    @Test
    void getMenuById_ShouldThrowEntityNotFoundException() {
        // Arrange
        String menuId = "invalidId";

        when(menuRepository.findMenuByDeletedFalse(menuId)).thenReturn(null);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> menuService.getMenuById(menuId));

        verify(menuRepository).findMenuByDeletedFalse(menuId);
        verifyNoInteractions(modelMapper);
    }

   
    @Test
    void updateMenu_ShouldUpdateAndReturnMenuRes() {
        // Arrange
        String id = "menu1";
        MenuReq menuReq = new MenuReq();
        menuReq.setName("Updated Latte");

        Menu existingMenu = new Menu();
        existingMenu.setId("menu1");
        existingMenu.setName("Old Latte");

        Menu updatedMenu = new Menu();
        updatedMenu.setId("menu1");
        updatedMenu.setName("Updated Latte");

        MenuRes menuRes = new MenuRes();
        menuRes.setId("menu1");

        // Mocking
        when(menuRepository.findMenuByDeletedFalse("menu1")).thenReturn(existingMenu);
        when(menuRepository.save(existingMenu)).thenReturn(updatedMenu);
        doAnswer(invocation -> {
            MenuReq req = invocation.getArgument(0);
            Menu target = invocation.getArgument(1);
            target.setName(req.getName());
            return null; // No return for void
        }).when(modelMapper).map(any(MenuReq.class), any(Menu.class));
        when(modelMapper.map(eq(updatedMenu), eq(MenuRes.class))).thenReturn(menuRes);

        // Act
        MenuRes result = menuService.updateMenu(id, menuReq);

        // Assert
        assertNotNull(result);
        assertEquals("menu1", result.getId());

        verify(menuRepository).findMenuByDeletedFalse("menu1");
        verify(menuRepository).save(existingMenu);
        verify(modelMapper).map(menuReq, existingMenu);
        verify(modelMapper).map(updatedMenu, MenuRes.class);
    }



    @Test
    void deleteMenu_ShouldDeleteMenuWhenFound() {
        // Arrange
        String menuId = "menu1";
        Menu menuEntity = new Menu();
        menuEntity.setId(menuId);

        when(menuRepository.findMenuByDeletedFalse(menuId)).thenReturn(menuEntity);

        // Act
        menuService.deleteMenu(menuId);

        // Assert
        verify(menuRepository).findMenuByDeletedFalse(menuId);
        verify(menuRepository).deleteById(menuId);
    }

    @Test
    void deleteMenu_ShouldThrowRuntimeExceptionWhenNotFound() {
        // Arrange
        String menuId = "invalidId";

        when(menuRepository.findMenuByDeletedFalse(menuId)).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> menuService.deleteMenu(menuId));

        verify(menuRepository).findMenuByDeletedFalse(menuId);
        verifyNoMoreInteractions(menuRepository);
    }

}
