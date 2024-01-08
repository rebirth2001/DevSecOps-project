package com.quizly.quizs.services;

import com.quizly.quizs.models.Quiz;
import com.quizly.quizs.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizService {
    private final QuizRepository quizRepo;

    @Autowired
    public QuizService(QuizRepository quizRepo) {
        this.quizRepo = quizRepo;
    }

    // Save or update a question
    public Quiz saveQuiz(Quiz quiz) {
        return quizRepo.save(quiz);
    }

}
