package com.example.app.startQuiz.repository;
import java.util.Optional;

import com.example.app.startQuiz.model.Quiz;
import com.example.app.login_and_registration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Optional<Quiz> findQuizById(Long id);
    Quiz findQuizzesByCodeOfInvitation(String code);
}