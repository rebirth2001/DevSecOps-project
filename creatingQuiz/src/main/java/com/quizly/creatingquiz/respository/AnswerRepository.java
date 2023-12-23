package com.quizly.creatingquiz.respository;

import com.quizly.creatingquiz.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findAnswerById(Long id);
    List<Answer> findByQuestionId(Long id);
}