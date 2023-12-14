package com.example.app.login_and_registration.config;

import com.example.app.login_and_registration.filter.JWTAuthorizationFilter;
import com.example.app.login_and_registration.service.JwtService;
import com.example.app.login_and_registration.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;

    private final UserService userService;

    private final JwtService jwtService;

    @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .anyRequest().authenticated()

                )
                .addFilter(new JWTAuthorizationFilter(usersAuthenticationManager(),jwtService,userService))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager usersAuthenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
