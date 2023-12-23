package com.quizly.creatingquiz.respository;

import com.quizly.creatingquiz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findQuestionById(Long id);

}