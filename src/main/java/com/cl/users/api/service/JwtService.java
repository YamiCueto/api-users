package com.cl.users.api.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JwtService {

    public String generateToken(String email) {
        return UUID.randomUUID().toString();
    }
}
