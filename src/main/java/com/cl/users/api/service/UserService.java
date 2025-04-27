package com.cl.users.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cl.users.api.dto.UserRequestDTO;
import com.cl.users.api.dto.UserResponseDTO;
import com.cl.users.api.entity.Phone;
import com.cl.users.api.entity.User;
import com.cl.users.api.exception.DuplicateEmailException;
import com.cl.users.api.exception.InvalidFormatException;
import com.cl.users.api.repository.UserRepository;

@Service
public class UserService {
    @Value("${regex.email}")
    private String emailRegex;

    @Value("${regex.password}")
    private String passwordRegex;

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new DuplicateEmailException();
        }

        if (!userRequestDTO.getEmail().matches(emailRegex)) {
            throw new InvalidFormatException("email");
        }

        if (!userRequestDTO.getPassword().matches(passwordRegex)) {
            throw new InvalidFormatException("password");
        }

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setActive(true);
        user.setToken(jwtService.generateToken(user.getEmail()));

        List<Phone> phones = userRequestDTO.getPhones().stream().map(phoneDTO -> {
            Phone phone = new Phone();
            phone.setNumber(phoneDTO.getNumber());
            phone.setCitycode(phoneDTO.getCitycode());
            phone.setContrycode(phoneDTO.getContrycode());
            return phone;
        }).toList();
        user.setPhones(phones);

        userRepository.save(user);

        UserResponseDTO response = new UserResponseDTO();
        response.setId(user.getId());
        response.setCreated(user.getCreated());
        response.setModified(user.getModified());
        response.setLastLogin(user.getLastLogin());
        response.setToken(user.getToken());
        response.setActive(user.isActive());

        return response;
    }
}
