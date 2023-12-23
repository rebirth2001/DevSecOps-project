package com.example.app.startQuiz.service.impl;

public class QuizNotFoundException extends RuntimeException {
    public QuizNotFoundException(String quizNotFound) {
        super(quizNotFound);
    }
}
