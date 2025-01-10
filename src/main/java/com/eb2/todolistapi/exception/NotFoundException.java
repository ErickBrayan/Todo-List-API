package com.eb2.todolistapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends RuntimeException {

    String code;
    HttpStatus status;

    public NotFoundException(String message, String code, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }

}
