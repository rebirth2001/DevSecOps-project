package com.example.app.login_and_registration.controller;

import com.example.app.login_and_registration.service.AuthenticationService;
import com.example.app.login_and_registration.http.AuthenticationRequest;
import com.example.app.login_and_registration.http.AuthenticationResponse;
import com.example.app.login_and_registration.http.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthenticationResponse> signUp(
            @RequestBody @Valid RegisterRequest request
    ) {
        System.out.println("Calling Sing Up");
        var resp  = service.register(request);
        if(resp.isError()){
            return ResponseEntity.badRequest().body(resp);
        }else{
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

}