package com.example.app.service;



import com.example.app.http.AuthenticationRequest;
import com.example.app.http.AuthenticationResponse;
import com.example.app.http.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        // Confirm the 2 passwords matches
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return AuthenticationResponse.builder()
                    .isError(true)
                    .errorMsg("Passwords don't match")
                    .build();
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        try {
            var user = userService.createUser(request).orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .build();
        } catch (Exception e) {
            return AuthenticationResponse.builder()
                    .isError(true)
                    .errorMsg("Email already in use")
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