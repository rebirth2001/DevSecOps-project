package com.quizly.creatingquiz.service;

import com.quizly.creatingquiz.model.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    void saveQuestion(Question question);
    List<Question> getAllQuestions();
    Optional<Question> getQuestionById(Long id);
    void deleteQuestion(Long id);
    void addQuestionsToQuiz(Long quizId, List<Question> questions);
}