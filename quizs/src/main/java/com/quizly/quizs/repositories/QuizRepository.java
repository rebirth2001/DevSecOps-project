package com.quizly.quizs.repositories;

import com.quizly.quizs.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz,Long> {
    Optional<Quiz> findById(Long id);
    List<Quiz> findQuizzesByOwner(String owner);
    Long countQuizzesByOwner(String owner);
}
