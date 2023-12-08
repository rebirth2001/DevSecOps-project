package com.example.app.login_and_registration.repository;
import java.util.Optional;

import com.example.app.login_and_registration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

}