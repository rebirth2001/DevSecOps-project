package com.quizly.quizs.services;

import com.quizly.quizs.models.Answer;
import com.quizly.quizs.repositories.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    // Save or update an answer
    public Answer saveAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    // Get all answers
    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }

    // Get answer by ID
    public Optional<Answer> getAnswerById(Long id) {
        return answerRepository.findById(id);
    }

    // Delete answer by ID
    public void deleteAnswer(Long id) {
        answerRepository.deleteById(id);
    }

    // Get answers for a specific question
    public List<Answer> getAnswersForQuestion(Long questionId) {
        return answerRepository.findByQuestionId(questionId);
    }
}
