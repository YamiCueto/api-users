package com.cl.users.api.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cl.users.api.dto.ErrorResponseDTO;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleDuplicateEmail() {
        DuplicateEmailException ex = new DuplicateEmailException();
        ResponseEntity<ErrorResponseDTO> response = handler.handleDuplicateEmail(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("El correo ya está registrado", response.getBody().getMensaje());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void handleInvalidFormat() {
        InvalidFormatException ex = new InvalidFormatException("email");
        ResponseEntity<ErrorResponseDTO> response = handler.handleInvalidFormat(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Formato inválido para el campo: email", response.getBody().getMensaje());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void handleGenericException() {
        Exception ex = new Exception("Error inesperado");
        ResponseEntity<ErrorResponseDTO> response = handler.handleGenericException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Error interno del servidor", response.getBody().getMensaje());
        assertNotNull(response.getBody().getTimestamp());
    }
}
