package com.quizly.users.services;


import com.quizly.users.dtos.RegisterRequestDto;
import com.quizly.users.models.User;
import com.quizly.users.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;


@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Optional<User> createUser(RegisterRequestDto request){
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(request.getPassword())
                .createdAt(Instant.now())
                .build();
        try {
            userRepository.save(user);
            return Optional.of(user);
        }catch(Exception e){
            return Optional.empty();
        }
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByUsername(String username){return userRepository.findByUsername(username);}
}
