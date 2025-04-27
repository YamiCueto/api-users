package com.cl.users.api.entity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User user;
    private UUID userId;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        now = LocalDateTime.now();
        
        user = new User();
        user.setId(userId);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("Password123");
        user.setCreated(now);
        user.setModified(now);
        user.setLastLogin(now);
        user.setToken("test-token");
        user.setActive(true);
        
        Phone phone = new Phone();
        phone.setNumber("123456789");
        phone.setCitycode("1");
        phone.setContrycode("57");
        user.setPhones(Arrays.asList(phone));
    }

    @Test
    void testUserGettersAndSetters() {
        assertEquals(userId, user.getId());
        assertEquals("Test User", user.getName());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("Password123", user.getPassword());
        assertEquals(now, user.getCreated());
        assertEquals(now, user.getModified());
        assertEquals(now, user.getLastLogin());
        assertEquals("test-token", user.getToken());
        assertTrue(user.isActive());
        assertNotNull(user.getPhones());
        assertEquals(1, user.getPhones().size());
        
        Phone phone = user.getPhones().get(0);
        assertEquals("123456789", phone.getNumber());
        assertEquals("1", phone.getCitycode());
        assertEquals("57", phone.getContrycode());
    }

    @Test
    void testEqualsAndHashCode() {
        User user2 = new User();
        user2.setId(userId);
        user2.setName("Test User");
        user2.setEmail("test@example.com");
        
        User user3 = new User();
        user3.setId(UUID.randomUUID());
        user3.setName("Different User");
        user3.setEmail("different@example.com");
        
        assertEquals(user.getId(), user2.getId());
        assertEquals(user.getName(), user2.getName());
        assertEquals(user.getEmail(), user2.getEmail());
        
        assertNotEquals(user.getId(), user3.getId());
        assertNotEquals(user.getName(), user3.getName());
        assertNotEquals(user.getEmail(), user3.getEmail());
        
        assertEquals(user.getId().hashCode() + user.getName().hashCode() + user.getEmail().hashCode(), 
                    user2.getId().hashCode() + user2.getName().hashCode() + user2.getEmail().hashCode());
        
        assertNotEquals(user.getId().hashCode() + user.getName().hashCode() + user.getEmail().hashCode(), 
                       user3.getId().hashCode() + user3.getName().hashCode() + user3.getEmail().hashCode());
    }

    @Test
    void testToString() {
        String toString = user.toString();
        
        assertTrue(toString.contains("Test User"));
        assertTrue(toString.contains("test@example.com"));
        assertTrue(toString.contains(userId.toString()));
    }
} 