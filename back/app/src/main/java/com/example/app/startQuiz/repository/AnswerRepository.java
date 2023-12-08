package com.example.app.startQuiz.repository;
import java.util.List;
import java.util.Optional;

import com.example.app.startQuiz.model.Answer;
import com.example.app.login_and_registration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findAnswerById(Long id);
    List<Answer> findByQuestionId(Long id);


}