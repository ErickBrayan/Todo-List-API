package com.eb2.todolistapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record SignInDTO(

        @NotBlank(message = "Email is Required")
        @NotEmpty(message = "Email is Required")
        @Email(message = "Email isn't correct format")
        String email,

        @Size(min = 8, message = "Password should not be less than 3")
        @NotBlank(message = "Password is Required")
        String password
) {
}
