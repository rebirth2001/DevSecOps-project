package com.example.app.login_and_registration.controller;

import com.example.app.login_and_registration.http.RegistrationResponse;
import com.example.app.login_and_registration.service.AuthenticationService;
import com.example.app.login_and_registration.http.AuthenticationRequest;
import com.example.app.login_and_registration.http.AuthenticationResponse;
import com.example.app.login_and_registration.http.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/sign-up")
    public ResponseEntity<RegistrationResponse> signUp(
            @RequestBody @Valid RegisterRequest request,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for(FieldError error:bindingResult.getFieldErrors()){
                errors.add(error.getField() + ":" + error.getDefaultMessage());
            }
            RegistrationResponse errorResponse = RegistrationResponse.builder()
                            .errors(errors)
                                    .isError(true)
                                            .build();
            System.out.println(errors);
            return ResponseEntity.badRequest().body(errorResponse);
        }
        var resp = service.register(request);
        if (resp.isError()){
            return ResponseEntity.badRequest().body(resp);
        } else {
            return ResponseEntity.ok(resp);
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticationResponse> signIn(
            @RequestBody AuthenticationRequest request
    ) {
        System.out.println("calling sign in");
        return ResponseEntity.ok(service.authenticate(request));

    }

    @PostMapping("/sign-out")
    public ResponseEntity<AuthenticationResponse> signOut(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(ex.getBindingResult().getFieldError().getDefaultMessage());
    }
}