package com.cl.users.api.dto;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserRequestDTOTest {

    private UserRequestDTO userRequestDTO;
    private PhoneDTO phoneDTO;

    @BeforeEach
    void setUp() {
        phoneDTO = new PhoneDTO();
        phoneDTO.setNumber("123456789");
        phoneDTO.setCitycode("1");
        phoneDTO.setContrycode("57");
        
        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("Test User");
        userRequestDTO.setEmail("test@example.com");
        userRequestDTO.setPassword("Password123");
        userRequestDTO.setPhones(Collections.singletonList(phoneDTO));
    }

    @Test
    void testUserRequestDTOGettersAndSetters() {
        assertEquals("Test User", userRequestDTO.getName());
        assertEquals("test@example.com", userRequestDTO.getEmail());
        assertEquals("Password123", userRequestDTO.getPassword());
        assertNotNull(userRequestDTO.getPhones());
        assertEquals(1, userRequestDTO.getPhones().size());
        
        PhoneDTO phone = userRequestDTO.getPhones().get(0);
        assertEquals("123456789", phone.getNumber());
        assertEquals("1", phone.getCitycode());
        assertEquals("57", phone.getContrycode());
    }

    @Test
    void testEqualsAndHashCode() {
        UserRequestDTO userRequestDTO2 = new UserRequestDTO();
        userRequestDTO2.setName("Test User");
        userRequestDTO2.setEmail("test@example.com");
        userRequestDTO2.setPassword("Password123");
        userRequestDTO2.setPhones(Arrays.asList(phoneDTO));
        
        UserRequestDTO userRequestDTO3 = new UserRequestDTO();
        userRequestDTO3.setName("Different User");
        userRequestDTO3.setEmail("different@example.com");
        userRequestDTO3.setPassword("Different123");
        
        PhoneDTO differentPhone = new PhoneDTO();
        differentPhone.setNumber("987654321");
        differentPhone.setCitycode("2");
        differentPhone.setContrycode("58");
        userRequestDTO3.setPhones(Arrays.asList(differentPhone));
        
        // Verificar equals
        assertEquals(userRequestDTO, userRequestDTO2);
        assertNotEquals(userRequestDTO, userRequestDTO3);
        
        // Verificar hashCode
        assertEquals(userRequestDTO.hashCode(), userRequestDTO2.hashCode());
        assertNotEquals(userRequestDTO.hashCode(), userRequestDTO3.hashCode());
    }

    @Test
    void testToString() {
        String toString = userRequestDTO.toString();
        
        // Verificar que toString contiene información relevante
        assertTrue(toString.contains("Test User"));
        assertTrue(toString.contains("test@example.com"));
        assertTrue(toString.contains("Password123"));
    }
} 