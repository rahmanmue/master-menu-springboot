package com.enigmacamp.mastermenu.service;

import java.util.List;

import com.enigmacamp.mastermenu.model.dtos.user.UserReq;
import com.enigmacamp.mastermenu.model.dtos.user.UserRes;

public interface UserService {
    List<UserRes> getAllUser();
    UserRes getUserCurrent(String email);
    UserRes updateUser(String email, UserReq userReq);
}
