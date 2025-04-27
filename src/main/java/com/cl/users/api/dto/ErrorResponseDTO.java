package com.cl.users.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ErrorResponseDTO {
    private String mensaje;
    private LocalDateTime timestamp;

    public ErrorResponseDTO(String mensaje) {
        this.mensaje = mensaje;
        this.timestamp = LocalDateTime.now();
    }

}
