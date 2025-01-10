package com.eb2.todolistapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DuplicateEmailException extends RuntimeException {

    private final String code;
    private final HttpStatus status;

    public DuplicateEmailException(String message, String code, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }
}
