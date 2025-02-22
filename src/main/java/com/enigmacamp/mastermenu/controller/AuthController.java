package com.enigmacamp.mastermenu.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enigmacamp.mastermenu.model.dtos.ApiResponse;
import com.enigmacamp.mastermenu.model.dtos.auth.LoginRes;
import com.enigmacamp.mastermenu.model.dtos.auth.LoginUserReq;
import com.enigmacamp.mastermenu.model.dtos.auth.RegisterRes;
import com.enigmacamp.mastermenu.model.dtos.auth.RegisterUserReq;
import com.enigmacamp.mastermenu.service.AuthService;
import com.enigmacamp.mastermenu.utils.constant.ApiPathConstant;

import lombok.RequiredArgsConstructor;

@RequestMapping(ApiPathConstant.API+ ApiPathConstant.VERSION + "/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterRes>> register(@RequestBody RegisterUserReq registerUserReq) {

        RegisterRes registeredUser = authService.signUp(registerUserReq);

        return new ResponseEntity<>(
            ApiResponse.<RegisterRes>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Register Success")
                .data(registeredUser)
                .build(),
                HttpStatus.OK
        );

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginRes>> authenticate(@RequestBody LoginUserReq loginUserReq) {

        LoginRes authenticatedUser = authService.authenticate(loginUserReq);
        return new ResponseEntity<>(
            ApiResponse.<LoginRes>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Login Success")
                .data(authenticatedUser)
                .build(),
                HttpStatus.OK
        );
    
       
    }
}
