package com.quizly.users.controllers;

import com.quizly.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validation")
@RequiredArgsConstructor
public class ValidationController {
    private final UserService userService;
    @GetMapping("/checkEmailAvailability")
    public ResponseEntity<Boolean> checkEmailAvailability(@RequestParam("email") String email){
        return ResponseEntity.ok(userService.findByEmail(email).isEmpty());
    }

    @GetMapping("/checkUsernameAvailability")
    public ResponseEntity<Boolean> checkUsernameAvailability(@RequestParam("username") String username){
        return ResponseEntity.ok(userService.findByUsername(username).isEmpty());
    }
}
