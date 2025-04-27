package com.cl.users.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserResponseDTOTest {

    private UserResponseDTO userResponseDTO;
    private UUID userId;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        now = LocalDateTime.now();
        
        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(userId);
        userResponseDTO.setCreated(now);
        userResponseDTO.setModified(now);
        userResponseDTO.setLastLogin(now);
        userResponseDTO.setToken("test-token");
        userResponseDTO.setActive(true);
    }

    @Test
    void testUserResponseDTOGettersAndSetters() {
        assertEquals(userId, userResponseDTO.getId());
        assertEquals(now, userResponseDTO.getCreated());
        assertEquals(now, userResponseDTO.getModified());
        assertEquals(now, userResponseDTO.getLastLogin());
        assertEquals("test-token", userResponseDTO.getToken());
        assertTrue(userResponseDTO.isActive());
    }

    @Test
    void testEqualsAndHashCode() {
        UserResponseDTO userResponseDTO2 = new UserResponseDTO();
        userResponseDTO2.setId(userId);
        userResponseDTO2.setCreated(now);
        userResponseDTO2.setModified(now);
        userResponseDTO2.setLastLogin(now);
        userResponseDTO2.setToken("test-token");
        userResponseDTO2.setActive(true);
        
        UserResponseDTO userResponseDTO3 = new UserResponseDTO();
        userResponseDTO3.setId(UUID.randomUUID());
        userResponseDTO3.setCreated(LocalDateTime.now());
        userResponseDTO3.setModified(LocalDateTime.now());
        userResponseDTO3.setLastLogin(LocalDateTime.now());
        userResponseDTO3.setToken("different-token");
        userResponseDTO3.setActive(false);
        
        assertEquals(userResponseDTO, userResponseDTO2);
        assertNotEquals(userResponseDTO, userResponseDTO3);
        
        assertEquals(userResponseDTO.hashCode(), userResponseDTO2.hashCode());
        assertNotEquals(userResponseDTO.hashCode(), userResponseDTO3.hashCode());
    }

    @Test
    void testToString() {
        String toString = userResponseDTO.toString();
        assertTrue(toString.contains(userId.toString()));
        assertTrue(toString.contains("test-token"));
        assertTrue(toString.contains("true"));
    }
} 