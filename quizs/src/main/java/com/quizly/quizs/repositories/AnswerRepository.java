package com.quizly.quizs.repositories;

import com.quizly.quizs.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer,Long> {
    Optional<Answer> findById(Long id);
    List<Answer> findByQuestionId(Long id);
}
