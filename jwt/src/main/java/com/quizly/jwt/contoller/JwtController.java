package com.quizly.jwt.contoller;

import com.quizly.jwt.Dto.UserDto;
import com.quizly.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class JwtController {
    private final JwtService jwtService;

    @GetMapping("/create/{username}")
    public ResponseEntity<String> generateToken(@PathVariable("username") String username){
        var user = new UserDto(username);
        return ResponseEntity.ok(jwtService.generateToken(user));
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyToken(@RequestParam("token") String token){
        System.out.println("Token to validate="+token);
        if (jwtService.isTokenExpired(token)) {
            return ResponseEntity.notFound().build();
        }
        String username = jwtService.extractUsername(token);
        if(username == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(username);
    }
}
