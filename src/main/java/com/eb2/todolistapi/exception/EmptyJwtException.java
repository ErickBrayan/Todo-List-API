package com.eb2.todolistapi.exception;

public class EmptyJwtException extends RuntimeException {

  public EmptyJwtException(String message) {
    super(message);
  }
}
