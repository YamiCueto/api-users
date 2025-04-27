package com.cl.users.api.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PhoneTest {

    private Phone phone;

    @BeforeEach
    void setUp() {
        phone = new Phone();
        phone.setNumber("123456789");
        phone.setCitycode("1");
        phone.setContrycode("57");
    }

    @Test
    void testPhoneGettersAndSetters() {
        assertEquals("123456789", phone.getNumber());
        assertEquals("1", phone.getCitycode());
        assertEquals("57", phone.getContrycode());
    }

    @Test
    void testEqualsAndHashCode() {
        Phone phone2 = new Phone();
        phone2.setNumber("123456789");
        phone2.setCitycode("1");
        phone2.setContrycode("57");
        
        Phone phone3 = new Phone();
        phone3.setNumber("987654321");
        phone3.setCitycode("2");
        phone3.setContrycode("58");
        
        assertEquals(phone, phone2);
        assertNotEquals(phone, phone3);
        
        assertEquals(phone.hashCode(), phone2.hashCode());
        assertNotEquals(phone.hashCode(), phone3.hashCode());
    }

    @Test
    void testToString() {
        String toString = phone.toString();
        
        assertTrue(toString.contains("123456789"));
        assertTrue(toString.contains("1"));
        assertTrue(toString.contains("57"));
    }
} 