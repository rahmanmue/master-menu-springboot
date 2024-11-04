package com.enigmacamp.mastermenu.controller;

import com.enigmacamp.mastermenu.model.dto.ApiResponse;
import com.enigmacamp.mastermenu.model.dto.request.CategoryMenuReq;
import com.enigmacamp.mastermenu.model.dto.response.CategoryMenuDetailRes;
import com.enigmacamp.mastermenu.model.dto.response.CategoryMenuRes;
import com.enigmacamp.mastermenu.service.CategoryMenuService;
import com.enigmacamp.mastermenu.utils.constant.ApiPathConstant;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPathConstant.API+ ApiPathConstant.VERSION + ApiPathConstant.CATEGORY)
public class CategoryMenuController {

    private final CategoryMenuService categoryMenuService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryMenuDetailRes>>> getAllCategory() {
    
        return new ResponseEntity<>(
            ApiResponse.<List<CategoryMenuDetailRes>>builder()
            .statusCode(HttpStatus.OK.value())
            .message("Success")
            .data(categoryMenuService.getAllCategoryMenu())
            .build(),
            HttpStatus.OK
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CategoryMenuDetailRes>>> getCategoryMenuByName(@RequestParam("name") String name) {
        List<CategoryMenuDetailRes> categories = categoryMenuService.getCategoryMenuByName(name);
  
        return new ResponseEntity<>(
            ApiResponse.<List<CategoryMenuDetailRes>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Category retrieved successfully")
                .data(categories) 
                .build(),
            HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryMenuDetailRes>> getCategoryMenuById(@PathVariable String id) {
        CategoryMenuDetailRes category = categoryMenuService.getCategoryMenuById(id);
  
        return new ResponseEntity<>(
            ApiResponse.<CategoryMenuDetailRes>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Category retrieved successfully")
                .data(category) 
                .build(),
            HttpStatus.OK
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CategoryMenuRes>> saveCategoryMenu(@Valid @RequestBody CategoryMenuReq categoryMenuReq) {
        CategoryMenuRes savedCategory =  categoryMenuService.saveCategoryMenu(categoryMenuReq); 
        
        return new ResponseEntity<>(
            ApiResponse.<CategoryMenuRes>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Category created successfully")
                .data(savedCategory)
                .build(),
            HttpStatus.CREATED
        );
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CategoryMenuRes>> updateCategoryMenu(@Valid @RequestBody CategoryMenuReq categoryMenuReq) {
        CategoryMenuRes updatedCategory = categoryMenuService.updateCategoryMenu(categoryMenuReq);

        return new ResponseEntity<>(
            ApiResponse.<CategoryMenuRes>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Category updated sucessfully")
                .data(updatedCategory)
                .build(),
            HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteCategoryMenu(@PathVariable String id) {
        categoryMenuService.deleteCategoryMenu(id);

        return new ResponseEntity<>(
            ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Category deleted sucessfully")
                .data("Category with id "+ id + " has been deleted")
                .build(),
            HttpStatus.OK
        );
    }



}
