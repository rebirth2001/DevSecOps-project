package com.example.app.startQuiz.service;

import com.example.app.startQuiz.model.Question;
import com.example.app.startQuiz.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuizService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    // Save or update a question
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    // Get all questions
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    // Get a list of questions by their IDs
    public List<Question> getQuestionsByIds(List<Long> questionIds) {
        return questionRepository.findAllById(questionIds);
    }

    // Get question by ID
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    // Delete question by ID
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    // Other methods specific to your business logic...
}
