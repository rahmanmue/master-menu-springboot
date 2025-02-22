package com.enigmacamp.mastermenu.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enigmacamp.mastermenu.model.dtos.ApiResponse;
import com.enigmacamp.mastermenu.model.dtos.user.UserReq;
import com.enigmacamp.mastermenu.model.dtos.user.UserRes;
import com.enigmacamp.mastermenu.service.UserService;
import com.enigmacamp.mastermenu.utils.constant.ApiPathConstant;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION + ApiPathConstant.USER)
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserRes>>> getUsers(){
        List<UserRes> users = userService.getAllUser();
        return new ResponseEntity<>(
            ApiResponse.<List<UserRes>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success")
                .data(users)
                .build(),
                HttpStatus.OK      
            ); 
    }

    @GetMapping("/user/current")
    public ResponseEntity<ApiResponse<UserRes>> getUserCurrent(@AuthenticationPrincipal UserDetails userDetails){
        UserRes user = userService.getUserCurrent(userDetails.getUsername());
        return new ResponseEntity<>(
            ApiResponse.<UserRes>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success")
                .data(user)
                .build(),
                HttpStatus.OK      
            ); 
    }

    @PatchMapping("/user/edit")
    public ResponseEntity<ApiResponse<UserRes>> updateUser(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserReq userReq){
        UserRes user = userService.updateUser(userDetails.getUsername(), userReq);
        return new ResponseEntity<>(
            ApiResponse.<UserRes>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success")
                .data(user)
                .build(),
                HttpStatus.OK      
            ); 
    }





}
