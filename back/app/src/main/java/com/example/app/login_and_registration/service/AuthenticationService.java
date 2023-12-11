package com.example.app.login_and_registration.service;



import com.example.app.login_and_registration.http.AuthenticationRequest;
import com.example.app.login_and_registration.http.AuthenticationResponse;
import com.example.app.login_and_registration.http.RegisterRequest;
import com.example.app.login_and_registration.http.RegistrationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;

    public RegistrationResponse register(RegisterRequest request) {
        // Confirm the 2 passwords matches
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            List<String> errors = new ArrayList<>();
            errors.add("Passwords don't match");
            return RegistrationResponse.builder()
                    .isError(true)
                    .errors(errors)
                    .build();
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        try {
            var user = userService.createUser(request).orElseThrow();
            return RegistrationResponse.builder()
                    .isError(false)
                    .build();
        } catch (Exception e) {
            List<String> errors = new ArrayList<>();
            errors.add("Email already in use");
            return RegistrationResponse.builder()
                    .isError(true)
                    .errors(errors)
                    .build();
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var user = userService.findByEmail(request.getEmail())
                    .orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .build();
        } catch (AuthenticationException e) {
            // Log the exception or handle it appropriately
            System.out.println("Authentication failed: " + e.getMessage());
            return AuthenticationResponse.builder()
                    .isError(true)
                    .errorMsg("Authentication failed")
                    .build();
        }
    }
}