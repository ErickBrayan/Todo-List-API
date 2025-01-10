package com.eb2.todolistapi.security.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@ConfigurationProperties(prefix = "rsa")
@Getter
@Setter
public class RsaProperties {

    Resource publicKey;
    Resource privateKey;
}
