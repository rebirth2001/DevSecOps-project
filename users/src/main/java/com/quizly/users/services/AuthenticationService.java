package com.quizly.users.services;

import com.quizly.users.dtos.AuthenticationResponseDto;
import com.quizly.users.dtos.AuthenticationRequestDto;
import com.quizly.users.dtos.RegisterRequestDto;
import com.quizly.users.dtos.RegisterResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;

    public RegisterResponseDto register(RegisterRequestDto request) {
        List<String> errors = new ArrayList<>();
        // Confirm the 2 passwords matches
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            errors.add("Passwords don't match");
            return RegisterResponseDto.builder()
                    .isError(true)
                    .errors(errors)
                    .build();
        }
        final String regex = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{7,}";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(request.getPassword());
        if(!matcher.matches()) {
            errors.add("Password must be at least 8 characters long, and contains 1 number, 1 lowercase and 1 uppercase character.");
            return RegisterResponseDto.builder()
                    .isError(true)
                    .errors(errors)
                    .build();
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        request.setUsername(request.getUsername().toLowerCase());
        request.setEmail(request.getEmail().toLowerCase());
        try {
            userService.createUser(request).orElseThrow();
            return RegisterResponseDto.builder()
                    .isError(false)
                    .build();
        } catch (Exception e) {
            errors.add("An account with this email or username already exists.");
            return RegisterResponseDto.builder()
                    .isError(true)
                    .errors(errors)
                    .build();
        }
    }

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
        request.setEmail(request.getEmail().toLowerCase());
        try {
            var user = userService.findByEmail(request.getEmail())
                    .orElseThrow();

            if(passwordEncoder.matches(request.getPassword(),user.getPasswordHash())){
                // Get a new jwt token from the jwt-service.
                String jwtToken = restTemplate.getForObject("lb://jwt-service/create/" + user.getUsername(),String.class);
                return AuthenticationResponseDto.builder()
                        .isError(false)
                        .username(user.getUsername())
                        .accessToken(jwtToken)
                        .build();
            }else{
                // passwords doesn't match.
                throw new Exception();
            }
        } catch (Exception e) {
            // Log the exception or handle it appropriately
            List<String> errors = new ArrayList<>();
            errors.add("Wrong password or email.");
            return AuthenticationResponseDto.builder()
                    .isError(true)
                    .errors(errors)
                    .build();
        }
    }

}