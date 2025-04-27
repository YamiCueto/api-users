package com.cl.users.api.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PhoneDTOTest {

    private PhoneDTO phoneDTO;

    @BeforeEach
    void setUp() {
        phoneDTO = new PhoneDTO();
        phoneDTO.setNumber("123456789");
        phoneDTO.setCitycode("1");
        phoneDTO.setContrycode("57");
    }

    @Test
    void testPhoneDTOGettersAndSetters() {
        assertEquals("123456789", phoneDTO.getNumber());
        assertEquals("1", phoneDTO.getCitycode());
        assertEquals("57", phoneDTO.getContrycode());
    }

    @Test
    void testEqualsAndHashCode() {
        PhoneDTO phoneDTO2 = new PhoneDTO();
        phoneDTO2.setNumber("123456789");
        phoneDTO2.setCitycode("1");
        phoneDTO2.setContrycode("57");
        
        PhoneDTO phoneDTO3 = new PhoneDTO();
        phoneDTO3.setNumber("987654321");
        phoneDTO3.setCitycode("2");
        phoneDTO3.setContrycode("58");
        
        assertEquals(phoneDTO, phoneDTO2);
        assertNotEquals(phoneDTO, phoneDTO3);
        
        assertEquals(phoneDTO.hashCode(), phoneDTO2.hashCode());
        assertNotEquals(phoneDTO.hashCode(), phoneDTO3.hashCode());
    }

    @Test
    void testToString() {
        String toString = phoneDTO.toString();
        
        assertTrue(toString.contains("123456789"));
        assertTrue(toString.contains("1"));
        assertTrue(toString.contains("57"));
    }
} 