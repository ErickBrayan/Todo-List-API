package com.eb2.todolistapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record TaskDTO(

        @NotBlank(message = "Title is Required")
        @NotEmpty(message = "Title is Required")
        String title,

        String description
) {
}
