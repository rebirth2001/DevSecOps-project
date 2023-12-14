package com.example.app.login_and_registration.service;



import com.example.app.login_and_registration.http.AuthenticationRequest;
import com.example.app.login_and_registration.http.AuthenticationResponse;
import com.example.app.login_and_registration.http.RegisterRequest;
import com.example.app.login_and_registration.http.RegistrationResponse;
import com.example.app.login_and_registration.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final AuthenticationManager authenticationManager;


    public RegistrationResponse register(RegisterRequest request) {
        List<String> errors = new ArrayList<>();
        // Confirm the 2 passwords matches
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            errors.add("Passwords don't match");
            return RegistrationResponse.builder()
                    .isError(true)
                    .errors(errors)
                    .build();
        }
        final String regex = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{7,}";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(request.getPassword());
        if(!matcher.matches()) {
            errors.add("Password must be at least 8 characters long, and contains 1 number, 1 lowercase and 1 uppercase character.");
            return RegistrationResponse.builder()
                    .isError(true)
                    .errors(errors)
                    .build();
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        request.setUsername(request.getUsername().toLowerCase());
        request.setEmail(request.getEmail().toLowerCase());
        try {
            var user = userService.createUser(request).orElseThrow();
            return RegistrationResponse.builder()
                    .isError(false)
                    .build();
        } catch (Exception e) {
            errors.add("An account with this email already exists.");
            return RegistrationResponse.builder()
                    .isError(true)
                    .errors(errors)
                    .build();
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        request.setEmail(request.getEmail().toLowerCase());
        try {
            var user = userService.findByEmail(request.getEmail())
                    .orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .build();
        } catch (AuthenticationException e) {
            // Log the exception or handle it appropriately
            List<String> errors = new ArrayList<>();
            errors.add("Wrong password or email.");
            return AuthenticationResponse.builder()
                    .isError(true)
                    .errors(errors)
                    .build();
        }
    }
}