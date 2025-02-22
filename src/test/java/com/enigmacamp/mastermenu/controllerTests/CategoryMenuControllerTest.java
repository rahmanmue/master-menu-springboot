package com.enigmacamp.mastermenu.controllerTests;

import com.enigmacamp.mastermenu.config.JwtAuthenticationFilter;
import com.enigmacamp.mastermenu.controller.CategoryMenuController;
import com.enigmacamp.mastermenu.model.dtos.category.CategoryMenuDetailRes;
import com.enigmacamp.mastermenu.service.CategoryMenuService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;


@WebMvcTest(CategoryMenuController.class)
@ExtendWith(MockitoExtension.class)
class CategoryMenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter; // Mock filter keamanan

    @MockBean
    private AuthenticationProvider authenticationProvider;

    @MockBean
    private CategoryMenuService categoryMenuService;

    @Test
    @WithMockUser(roles = "USER")
    void testGetAllCategory() throws Exception {
        CategoryMenuDetailRes categoryMenuDetailRes1 = new CategoryMenuDetailRes();
        categoryMenuDetailRes1.setId("1");
        categoryMenuDetailRes1.setName("Category 1");

        CategoryMenuDetailRes categoryMenuDetailRes2 = new CategoryMenuDetailRes();
        categoryMenuDetailRes2.setId("2");
        categoryMenuDetailRes2.setName("Category 2");

        List<CategoryMenuDetailRes> categories = Arrays.asList(categoryMenuDetailRes1, categoryMenuDetailRes2);

        when(categoryMenuService.getAllCategoryMenu()).thenReturn(
            categories
        );

         // Melakukan pengujian
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/category-menu"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk());

    }
}
