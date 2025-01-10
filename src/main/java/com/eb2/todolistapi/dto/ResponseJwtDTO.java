package com.eb2.todolistapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseJwtDTO(

        @JsonProperty("access_token")
        String token,
        @JsonProperty("refresh_token")
        String refreshToken
) {
}
