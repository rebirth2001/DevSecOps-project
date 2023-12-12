package com.example.app.startQuiz.service;

import com.example.app.startQuiz.model.Answer;
import com.example.app.startQuiz.model.Question;
import com.example.app.startQuiz.model.Quiz;
import com.example.app.startQuiz.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface QuizService {
    List<Quiz> getAllQuize();
    void saveQuiz(Quiz quiz);
    void deleteQuiz(Long id);
    void modifyQuiz(Long id, Quiz updatedQuiz);
    Quiz getQuizById(Long id);
    Quiz addQuestionToQuiz(Long id, Question question);
    Quiz addAnswerToQuestion(Long quizId, Long quesionId, Answer answer);
}