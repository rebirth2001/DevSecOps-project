package com.quizly.quizs.repositories;

import com.quizly.quizs.dtos.QuizDto;
import com.quizly.quizs.models.Question;
import com.quizly.quizs.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    Optional<Question> findById(Long id);
    List<Question> findByQuizId(Long id);
    List<Question> findQuestionsByQuiz(Quiz quiz);
}
