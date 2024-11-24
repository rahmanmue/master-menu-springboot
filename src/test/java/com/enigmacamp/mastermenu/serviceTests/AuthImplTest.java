package com.enigmacamp.mastermenu.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.enigmacamp.mastermenu.model.dto.request.LoginUserReq;
import com.enigmacamp.mastermenu.model.dto.request.RegisterUserReq;
import com.enigmacamp.mastermenu.model.dto.response.LoginRes;
import com.enigmacamp.mastermenu.model.dto.response.RegisterRes;
import com.enigmacamp.mastermenu.model.entity.Employee;
import com.enigmacamp.mastermenu.model.entity.Role;
import com.enigmacamp.mastermenu.model.entity.User;
import com.enigmacamp.mastermenu.repository.CustomerRepository;
import com.enigmacamp.mastermenu.repository.EmployeeRepository;
import com.enigmacamp.mastermenu.repository.RoleRepository;
import com.enigmacamp.mastermenu.repository.UserRepository;
import com.enigmacamp.mastermenu.service.JwtService;
import com.enigmacamp.mastermenu.service.impl.AuthImpl;
import com.enigmacamp.mastermenu.utils.enums.ERole;

@ExtendWith(MockitoExtension.class)
class AuthImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthImpl authService;

    @Test
    void signUp_ShouldRegisterUserSuccessfully_WhenRoleIsAdmin() {
        // Arrange
        RegisterUserReq registerReq = new RegisterUserReq();
        registerReq.setFullName("John Doe");
        registerReq.setEmail("johndoe@example.com");
        registerReq.setPassword("password123");
        registerReq.setRole(ERole.ROLE_ADMIN);

        Role adminRole = new Role();
        adminRole.setName(ERole.ROLE_ADMIN);

        User savedUser = User.builder()
            .email("johndoe@example.com")
            .roles(Set.of(adminRole))
            .build();

        // Employee employee = Employee.builder()
        //     .fullName("John Doe")
        //     .user(savedUser)
        //     .build();

        when(roleRepository.findByName(ERole.ROLE_ADMIN)).thenReturn(Optional.of(adminRole));
        when(passwordEncoder.encode("password123")).thenReturn("encoded_password123");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        RegisterRes response = authService.signUp(registerReq);

        // Assert
        assertNotNull(response);
        assertEquals("johndoe@example.com", response.getEmail());
        verify(roleRepository).findByName(ERole.ROLE_ADMIN);
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(User.class));
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void signUp_ShouldThrowException_WhenRoleNotFound() {
        // Arrange
        RegisterUserReq registerReq = new RegisterUserReq();
        registerReq.setFullName("John Doe");
        registerReq.setEmail("johndoe@example.com");
        registerReq.setPassword("password123");
        registerReq.setRole(ERole.ROLE_ADMIN);

        when(roleRepository.findByName(ERole.ROLE_ADMIN)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.signUp(registerReq));
        assertEquals("Error: Role not found.", exception.getMessage());
        verify(roleRepository).findByName(ERole.ROLE_ADMIN);
    }

    @Test
    void authenticate_ShouldAuthenticateUserSuccessfully() {
        // Arrange
        LoginUserReq loginReq = new LoginUserReq();
        loginReq.setEmail("johndoe@example.com");
        loginReq.setPassword("password123");
        
        Role adminRole = new Role();
        adminRole.setName(ERole.ROLE_ADMIN);

        User user = User.builder()
            .email("johndoe@example.com")
            .roles(Set.of(adminRole))
            .build();

        String token = "mockedJwtToken";
        when(userRepository.findByEmail("johndoe@example.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn(token);
        when(jwtService.getExpirationTime()).thenReturn(3600L);

        // Act
        LoginRes response = authService.authenticate(loginReq);

        // Assert
        assertNotNull(response);
        assertEquals(token, response.getToken());
        assertEquals(3600L, response.getExpiresIn());
        assertEquals(List.of(ERole.ROLE_ADMIN.name()), response.getRole());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail("johndoe@example.com");
        verify(jwtService).generateToken(user);
        verify(jwtService).getExpirationTime();
    }

}
