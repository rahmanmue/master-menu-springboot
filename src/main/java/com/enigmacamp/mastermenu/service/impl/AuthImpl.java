package com.enigmacamp.mastermenu.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.enigmacamp.mastermenu.model.dto.request.LoginUserReq;
import com.enigmacamp.mastermenu.model.dto.request.RegisterUserReq;
import com.enigmacamp.mastermenu.model.dto.response.LoginRes;
import com.enigmacamp.mastermenu.model.dto.response.RegisterRes;
import com.enigmacamp.mastermenu.model.entity.Customer;
import com.enigmacamp.mastermenu.model.entity.Employee;
import com.enigmacamp.mastermenu.model.entity.Role;
import com.enigmacamp.mastermenu.model.entity.User;
import com.enigmacamp.mastermenu.model.entity.Wallet;
import com.enigmacamp.mastermenu.repository.CustomerRepository;
import com.enigmacamp.mastermenu.repository.EmployeeRepository;
import com.enigmacamp.mastermenu.repository.RoleRepository;
import com.enigmacamp.mastermenu.repository.UserRepository;
import com.enigmacamp.mastermenu.repository.WalletRepository;
import com.enigmacamp.mastermenu.service.AuthService;
import com.enigmacamp.mastermenu.service.JwtService;
import com.enigmacamp.mastermenu.utils.enums.ERole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;
    private final WalletRepository walletRepository;
    private final CustomerRepository customerRepository;
    private final JwtService jwtService;

    @Override
    public RegisterRes signUp(RegisterUserReq input){
        Role userRole = roleRepository.findByName(input.getRole())
            .orElseThrow(() -> new RuntimeException("Error: Role not found."));
        
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        User user = User.builder().email(input.getEmail()).password(passwordEncoder.encode(input.getPassword())).roles(roles).build();
        User savedUser = userRepository.save(user);
        
        if(input.getRole().equals(ERole.ROLE_ADMIN)){
            Employee employee = Employee.builder().fullName(input.getFullName()).user(savedUser).build();
            employeeRepository.save(employee);
        } else if (input.getRole().equals(ERole.ROLE_USER)){
            Customer customer = Customer.builder().fullName(input.getFullName()).user(savedUser).build();
            customerRepository.save(customer);
            Wallet wallet = Wallet.builder().user(savedUser).balance(0.00).build();
            walletRepository.save(wallet);
        }

        return RegisterRes.builder().email(savedUser.getUsername()).build();

    }

    @Override
    public LoginRes authenticate(LoginUserReq input){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword())
        );

        User authenticatedUser = userRepository.findByEmail(input.getEmail()).orElseThrow();

        String jwtToken = jwtService.generateToken(authenticatedUser);

        List<String> role = authenticatedUser.getAuthorities().stream().map(auth -> auth.getAuthority()).toList();

        LoginRes loginResponse = LoginRes.builder()
            .token(jwtToken)
            .expiresIn(jwtService.getExpirationTime())
            .role(role)
            .build();

        return loginResponse;

    }


}
