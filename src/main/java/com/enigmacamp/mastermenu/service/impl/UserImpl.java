package com.enigmacamp.mastermenu.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.enigmacamp.mastermenu.model.dtos.user.UserReq;
import com.enigmacamp.mastermenu.model.dtos.user.UserRes;
import com.enigmacamp.mastermenu.model.entity.User;
import com.enigmacamp.mastermenu.repository.UserRepository;
import com.enigmacamp.mastermenu.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UserRes getUserCurrent(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new EntityNotFoundException("User Not Found")
            );

        UserRes userRes = modelMapper.map(user, UserRes.class);
        return userRes;
    }

    @Override
    public List<UserRes> getAllUser() {
       List<User> users = userRepository.findAll();
       List<UserRes> usersRes = users.stream()
                    .map((user) -> modelMapper.map(user, UserRes.class))
                    .toList();
        
       return usersRes;             
    }

    @Override
    public UserRes updateUser(String email, UserReq userReq) {
        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new EntityNotFoundException("User Not Found")
        );

        String passwordBcrypt = passwordEncoder.encode(userReq.getPassword());
        userReq.setPassword(passwordBcrypt);

        modelMapper.map(userReq, user);
        user.setId(user.getId());

        User updatedUser = userRepository.save(user);

        return modelMapper.map(updatedUser, UserRes.class);

    }
    
}
