package com.eb2.todolistapi.security.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
@Getter
@ConfigurationProperties(prefix = "jwt.time.expiration")
public class JwtProperties {
    private long token;
    private long refreshToken;
}
