package com.quizly.creatingquiz.respository;

import com.quizly.creatingquiz.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Optional<Quiz> findQuizById(Long id);
    Quiz findQuizzesByCodeOfInvitation(String code);
}