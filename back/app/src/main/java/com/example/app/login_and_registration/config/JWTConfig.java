package com.example.app.login_and_registration.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt.config")
@RequiredArgsConstructor
@Getter
@Setter
public class JWTConfig {
    private String secret = "ad81fcba52342929672c0d8097c66e3f856db127798a02a890519b6fe79c19af";
    private long expiration = 900_000; // 15 minutes as dictated by the standard.
    private String header = "Authorization" ;
    private String prefix = "Bearer " ;
}
