package com.quizly.creatingquiz.service;

import com.quizly.creatingquiz.model.Answer;
import com.quizly.creatingquiz.model.Participant;
import com.quizly.creatingquiz.model.Question;
import com.quizly.creatingquiz.model.Quiz;

import java.util.List;

public interface QuizService {
    List<Quiz> getAllQuize();
    void saveQuiz(Quiz quiz);
    void deleteQuiz(Long id);
    void modifyQuiz(Long id, Quiz updatedQuiz);
    Quiz getQuizById(Long id);
    Quiz addQuestionToQuiz(Long id, Question question);
    Quiz addAnswerToQuestion(Long quizId, Long quesionId, Answer answer);
    Quiz addParticipantToQuiz(String code, Participant participant);
    Quiz getQuizByCode(String code);
}