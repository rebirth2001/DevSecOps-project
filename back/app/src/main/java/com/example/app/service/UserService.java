package com.example.app.service;

import com.example.app.http.RegisterRequest;
import com.example.app.model.User;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> createUser(RegisterRequest request){
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(request.getPassword())
                .createdAt(LocalDateTime.now())
                .build();
        try {
            userRepository.save(user);
        }catch(Exception e){
            return Optional.empty();
        }

        return Optional.of(user);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
