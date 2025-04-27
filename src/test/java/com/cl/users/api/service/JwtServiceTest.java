package com.cl.users.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import io.jsonwebtoken.JwtException;

class JwtServiceTest {

    private JwtService jwtService;
    private static final String TEST_SECRET = "testSecretKey12345678901234567890";
    private static final Long TEST_EXPIRATION = 24L;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", TEST_SECRET);
        ReflectionTestUtils.setField(jwtService, "expirationHours", TEST_EXPIRATION);
    }

    @Test
    void generateTokenAndValidate() {
        String email = "test@example.com";
        
        String token = jwtService.generateToken(email);
        
        assertNotNull(token);
        String validatedEmail = jwtService.validateTokenAndGetEmail(token);
        assertEquals(email, validatedEmail);
    }

    @Test
    void validateInvalidTokenShouldThrowException() {
        String invalidToken = "invalid.token.here";
        
        assertThrows(JwtException.class, () -> {
            jwtService.validateTokenAndGetEmail(invalidToken);
        });
    }

    @Test
    void validateTokenWithDifferentSecretShouldFail() {
        String email = "test@example.com";
        String token = jwtService.generateToken(email);
        
        // Cambiar el secreto para simular una clave diferente
        ReflectionTestUtils.setField(jwtService, "secret", "differentSecret12345678901234567890");
        
        assertThrows(JwtException.class, () -> {
            jwtService.validateTokenAndGetEmail(token);
        });
    }
}