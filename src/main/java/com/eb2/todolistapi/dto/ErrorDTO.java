package com.eb2.todolistapi.dto;

public record ErrorDTO(
    String message,
    String code
) {
}
