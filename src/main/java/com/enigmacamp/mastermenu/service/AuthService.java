package com.enigmacamp.mastermenu.service;

import com.enigmacamp.mastermenu.model.dto.request.LoginUserReq;
import com.enigmacamp.mastermenu.model.dto.request.RegisterUserReq;
import com.enigmacamp.mastermenu.model.dto.response.LoginRes;
import com.enigmacamp.mastermenu.model.dto.response.RegisterRes;

public interface AuthService {
    RegisterRes signUp(RegisterUserReq input);
    LoginRes authenticate(LoginUserReq input);
}
