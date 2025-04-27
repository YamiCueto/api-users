package com.cl.users.api.service;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
    }

    @Test
    void generateTokenShouldReturnValidUUID() {
        String email = "test@example.com";
        
        String token = jwtService.generateToken(email);
        
        assertNotNull(token);
        assertTrue(token.matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"));
    }

    @Test
    void generateTokenShouldReturnDifferentTokensForSameEmail() {
        String email = "test@example.com";
        
        String token1 = jwtService.generateToken(email);
        String token2 = jwtService.generateToken(email);
        
        assertNotEquals(token1, token2);
    }
} 