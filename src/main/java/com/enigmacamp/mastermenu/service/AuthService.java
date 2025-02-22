package com.enigmacamp.mastermenu.service;

import com.enigmacamp.mastermenu.model.dtos.auth.LoginRes;
import com.enigmacamp.mastermenu.model.dtos.auth.LoginUserReq;
import com.enigmacamp.mastermenu.model.dtos.auth.RegisterRes;
import com.enigmacamp.mastermenu.model.dtos.auth.RegisterUserReq;

public interface AuthService {
    RegisterRes signUp(RegisterUserReq input);
    LoginRes authenticate(LoginUserReq input);
}
