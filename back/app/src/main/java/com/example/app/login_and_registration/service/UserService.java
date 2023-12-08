package com.example.app.login_and_registration.service;

import com.example.app.login_and_registration.http.RegisterRequest;
import com.example.app.login_and_registration.model.User;
import com.example.app.login_and_registration.repository.UserRepository;
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
