package com.example.app.startQuiz.repository;
import java.util.Optional;

import com.example.app.startQuiz.model.Question;
import com.example.app.login_and_registration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findQuestionById(Long id);

}