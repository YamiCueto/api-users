package com.cl.users.api.exception;

public class DuplicateEmailException extends UserException {
    public DuplicateEmailException() {
        super("El correo ya está registrado");
    }
}
