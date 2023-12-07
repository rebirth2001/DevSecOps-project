package com.example.app.login_and_registration.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "jwt.config")
@Configuration
@Getter
@Setter
public class JWTConfig {
    private String secret;
    private long expiration;
}
