package com.enigmacamp.mastermenu.controller;

import com.enigmacamp.mastermenu.model.dtos.ApiResponse;
import com.enigmacamp.mastermenu.model.dtos.menu.MenuDetailRes;
import com.enigmacamp.mastermenu.model.dtos.menu.MenuReq;
import com.enigmacamp.mastermenu.model.dtos.menu.MenuRes;
import com.enigmacamp.mastermenu.service.MenuService;
import com.enigmacamp.mastermenu.service.S3Service;
import com.enigmacamp.mastermenu.utils.constant.ApiPathConstant;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPathConstant.API+ ApiPathConstant.VERSION + ApiPathConstant.MENU)
public class MenuController {

    private final MenuService menuService;
    private final S3Service s3Service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MenuDetailRes>>> getAllMenu() {
        List<MenuDetailRes> menus = menuService.getAllMenu();
        return new ResponseEntity<>(
            ApiResponse.<List<MenuDetailRes>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success")
                .data(menus)
                .build(),
                HttpStatus.OK      
            );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<MenuDetailRes>>> getAllMenuByCategory(@RequestParam(name="category") String category) {
        List<MenuDetailRes> menus = menuService.getAllMenuByCategory(category);
        return new ResponseEntity<>(
            ApiResponse.<List<MenuDetailRes>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success")
                .data(menus)
                .build(),
                HttpStatus.OK      
            );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MenuDetailRes>> getMenuById(@PathVariable String id) {
        MenuDetailRes menu = menuService.getMenuById(id);
        return new ResponseEntity<>(
            ApiResponse.<MenuDetailRes>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success retrieve data")
                .data(menu)
                .build(),
            HttpStatus.OK   
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<MenuRes>> createMenu(
        @RequestParam("file") MultipartFile file,
        @ModelAttribute @Valid MenuReq menuReq) {
        
        String imageUrl = s3Service.uploadFile(file);
        menuReq.setImageUrl(imageUrl);
        
        MenuRes savedMenu = menuService.createMenu(menuReq);
        return new ResponseEntity<>(
            ApiResponse.<MenuRes>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Menu created successfully")
                .data(savedMenu)
                .build(),
            HttpStatus.CREATED
        );
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<MenuRes>> updateMenu(
        @PathVariable String id,
        @RequestParam(value ="file", required = false) MultipartFile file,
        @ModelAttribute @Valid MenuReq menuReq) {
        
        String newImageUrl = s3Service.updateFile(file, id);
        menuReq.setImageUrl(newImageUrl);
        

        MenuRes updated = menuService.updateMenu(id, menuReq);
        return new ResponseEntity<>(
            ApiResponse.<MenuRes>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Menu updated successfully")
                .data(updated)
                .build(),
            HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteMenu(@PathVariable String id) {
        
        String imageUrl = menuService.getMenuById(id).getImageUrl();
        String fileName = imageUrl.split("/")[ imageUrl.split("/").length - 1];
        
        s3Service.deleteFile(fileName);
        menuService.deleteMenu(id);

        return new ResponseEntity<>(
            ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Menu deleted sucessfully")
                .data("Menu with id "+ id + " has been deleted")
                .build(),
            HttpStatus.OK
        );
    }

    // @PostMapping("/upload")
    // public String postImage(@RequestParam("file") MultipartFile file) {
    //     String url = s3Service.uploadFile(file);
    //     return url;
    // }
    


}
