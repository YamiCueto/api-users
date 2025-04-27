package com.cl.users.api.exception;

public class InvalidFormatException extends UserException {
    public InvalidFormatException(String field) {
        super("Formato inválido para el campo: " + field);
    }
}
