package com.eb2.todolistapi.exception;

import com.eb2.todolistapi.dto.ErrorDTO;
import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFoundException(NotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO(
                e.getMessage(),
                e.getCode()
        );

        return new ResponseEntity<>(errorDTO, e.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorDTO> handleDuplicateEmailException(DuplicateEmailException e) {

        ErrorDTO errorDTO = new ErrorDTO(e.getMessage(), e.getCode());

        return new ResponseEntity<>(errorDTO, e.getStatus());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorDTO> handleExpiredJwtException(ExpiredJwtException e) {

        ErrorDTO errorDTO = new ErrorDTO(e.getMessage(), "400");

        return new ResponseEntity<>(errorDTO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorDTO> handleMalformedJwtException(MalformedJwtException e) {

        ErrorDTO errorDTO = new ErrorDTO(e.getLocalizedMessage(), "403");

        return new ResponseEntity<>(errorDTO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorDTO> handleJwtException(JwtException e) {

        ErrorDTO errorDTO = new ErrorDTO(e.getLocalizedMessage(), "400");

        return new ResponseEntity<>(errorDTO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EmptyJwtException.class)
    public ResponseEntity<ErrorDTO> handleEmptyJwtException(EmptyJwtException e) {

        ErrorDTO errorDTO = new ErrorDTO(e.getLocalizedMessage(), "400");

        return new ResponseEntity<>(errorDTO, HttpStatus.FORBIDDEN);
    }
}