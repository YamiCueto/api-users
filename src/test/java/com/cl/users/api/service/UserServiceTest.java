package com.cl.users.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.cl.users.api.dto.PhoneDTO;
import com.cl.users.api.dto.UserRequestDTO;
import com.cl.users.api.dto.UserResponseDTO;
import com.cl.users.api.entity.Phone;
import com.cl.users.api.entity.User;
import com.cl.users.api.exception.DuplicateEmailException;
import com.cl.users.api.exception.InvalidFormatException;
import com.cl.users.api.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    private UserRequestDTO userRequestDTO;
    private User user;
    private UUID userId;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(userService, "emailRegex", "^[A-Za-z0-9+_.-]+@(.+)$");
        ReflectionTestUtils.setField(userService, "passwordRegex", "^(?=.*[A-Z])(?=.*[0-9].*[0-9])(?=.*[a-z]).{8,}$");

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
        
        user = new User();
        user.setId(userId);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("Password123");
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setActive(true);
        user.setToken("test-token");
        
        Phone phone = new Phone();
        phone.setNumber("123456789");
        phone.setCitycode("1");
        phone.setContrycode("57");
        user.setPhones(List.of(phone));
    }

    @Test
    void registerUser_Success() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(jwtService.generateToken(anyString())).thenReturn("test-token");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            return savedUser;
        });

        UserResponseDTO response = userService.registerUser(userRequestDTO);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertTrue(response.isActive());
        assertEquals("test-token", response.getToken());
        
        verify(userRepository).existsByEmail(userRequestDTO.getEmail());
        verify(jwtService).generateToken(userRequestDTO.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerUserEmailAlreadyExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        DuplicateEmailException exception = assertThrows(DuplicateEmailException.class, 
            () -> userService.registerUser(userRequestDTO));
        
        assertEquals("El correo ya está registrado", exception.getMessage());
        verify(userRepository).existsByEmail(userRequestDTO.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerUserInvalidEmail() {
        userRequestDTO.setEmail("invalid-email");

        InvalidFormatException exception = assertThrows(InvalidFormatException.class, 
            () -> userService.registerUser(userRequestDTO));
        
        assertEquals("Formato inválido para el campo: email", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerUserInvalidPassword() {
        userRequestDTO.setPassword("weak");

        InvalidFormatException exception = assertThrows(InvalidFormatException.class, 
            () -> userService.registerUser(userRequestDTO));
        
        assertEquals("Formato inválido para el campo: password", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }
} 