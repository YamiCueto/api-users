package com.cl.users.api.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cl.users.api.dto.PhoneDTO;
import com.cl.users.api.dto.UserRequestDTO;
import com.cl.users.api.dto.UserResponseDTO;
import com.cl.users.api.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserRequestDTO userRequestDTO;
    private UserResponseDTO userResponseDTO;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        
        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setNumber("123456789");
        phoneDTO.setCitycode("1");
        phoneDTO.setContrycode("57");
        
        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("Test User");
        userRequestDTO.setEmail("test@example.com");
        userRequestDTO.setPassword("Password123");
        userRequestDTO.setPhones(List.of(phoneDTO));
        
        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(userId);
        userResponseDTO.setCreated(LocalDateTime.now());
        userResponseDTO.setModified(LocalDateTime.now());
        userResponseDTO.setLastLogin(LocalDateTime.now());
        userResponseDTO.setToken("test-token");
        userResponseDTO.setActive(true);
    }

    @Test
    void registerUserSuccess() {
        when(userService.registerUser(any(UserRequestDTO.class))).thenReturn(userResponseDTO);

        ResponseEntity<?> response = userController.registerUser(userRequestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(UserResponseDTO.class, response.getBody());
        
        UserResponseDTO responseBody = (UserResponseDTO) response.getBody();
        assertEquals(userId, responseBody.getId());
        assertEquals("test-token", responseBody.getToken());
        assertTrue(responseBody.isActive());
        
        verify(userService).registerUser(userRequestDTO);
    }

    @Test
    void registerUserError() {
        String errorMessage = "El correo ya registrado";
        when(userService.registerUser(any(UserRequestDTO.class))).thenThrow(new RuntimeException(errorMessage));

        ResponseEntity<?> response = userController.registerUser(userRequestDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(Map.class, response.getBody());
        
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getBody();
        assertEquals(errorMessage, responseBody.get("mensaje"));
        
        verify(userService).registerUser(userRequestDTO);
    }
} 