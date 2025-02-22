package com.enigmacamp.mastermenu.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;


import java.util.function.Function;

public interface JwtService {
    String extractUsername(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    String generateToken(UserDetails userDetails);
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);
    long getExpirationTime();
    boolean isTokenValid(String token, UserDetails userDetails); 
   
}
