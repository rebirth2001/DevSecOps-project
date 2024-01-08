package com.quizly.users.controllers;

import com.quizly.users.dtos.*;
import com.quizly.users.services.AuthenticationService;
import com.quizly.users.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public ResponseEntity<RegisterResponseDto> signUp(
            @RequestBody @Valid RegisterRequestDto request,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for(FieldError error:bindingResult.getFieldErrors()){
                errors.add(error.getDefaultMessage());
            }
            RegisterResponseDto errorResponse = RegisterResponseDto.builder()
                    .errors(errors)
                    .isError(true)
                    .build();
            return ResponseEntity.badRequest().body(errorResponse);
        }
        var resp = authenticationService.register(request);
        if (resp.isError()){
            return ResponseEntity.badRequest().body(resp);
        } else {
            return ResponseEntity.ok(resp);
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticationResponseDto> signIn(
            @RequestBody @Valid AuthenticationRequestDto request,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for(FieldError error:bindingResult.getFieldErrors()){
                errors.add(error.getDefaultMessage());
            }
            AuthenticationResponseDto errorResponse = AuthenticationResponseDto.builder()
                    .errors(errors)
                    .isError(true)
                    .build();
            return ResponseEntity.badRequest().body(errorResponse);
        }

        var resp = authenticationService.authenticate(request);
        if (resp.isError()){
            return ResponseEntity.badRequest().body(resp);
        } else {
            return ResponseEntity.ok(resp);
        }
    }

    @GetMapping("/hello")
    public ResponseEntity hello(){
        return ResponseEntity.ok().build();
    }
}