package com.enigmacamp.mastermenu.controllerTests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.enigmacamp.mastermenu.config.JwtAuthenticationFilter;
import com.enigmacamp.mastermenu.config.TestSecurityConfig;
import com.enigmacamp.mastermenu.controller.AuthController;
import com.enigmacamp.mastermenu.model.dto.request.LoginUserReq;
import com.enigmacamp.mastermenu.model.dto.request.RegisterUserReq;
import com.enigmacamp.mastermenu.model.dto.response.LoginRes;
import com.enigmacamp.mastermenu.model.dto.response.RegisterRes;
import com.enigmacamp.mastermenu.service.JwtService;
import com.enigmacamp.mastermenu.service.impl.AuthImpl;
import com.enigmacamp.mastermenu.utils.enums.ERole;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AuthController.class)
@ExtendWith(MockitoExtension.class)
@Import(TestSecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthImpl authService; // Mock AuthService

    @MockBean
    private JwtService jwtService; // Mock JwtService

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private AuthenticationManager authenticationManager; // Mock AuthenticationManager


    @Test
    void testSignUp() throws Exception {
        RegisterUserReq registerRequest = new RegisterUserReq();
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password");
        registerRequest.setRole(ERole.ROLE_USER);
        registerRequest.setFullName("Test User");
    
        RegisterRes registerResponse = new RegisterRes();
        registerResponse.setEmail("test@example.com");
        when(authService.signUp(any(RegisterUserReq.class))).thenReturn(registerResponse);
    
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andReturn(); // Capture the response

        // Print the response body
        String responseBody = result.getResponse().getContentAsString();
        System.out.println("Response Body: " + responseBody);

        // Assert JSON content
        // assertThat(responseBody).contains("statusCode", "Register Success", "test@example.com");

    }
    

    @Test
    void testAuthenticate() throws Exception {
        // Setup mock behavior
        LoginUserReq loginRequest = new LoginUserReq();
        loginRequest.setEmail("email@example.com");
        loginRequest.setPassword("password");
        LoginRes loginResponse = LoginRes.builder().token("jwtToken").expiresIn(3600L).role(List.of("ROLE_USER")).build();
        

        when(authService.authenticate(any(LoginUserReq.class))).thenReturn(loginResponse);

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }
}
