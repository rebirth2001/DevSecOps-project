package com.example.app.startQuiz.service.impl;

public class QuizNotFoundExeption extends RuntimeException {
    public QuizNotFoundExeption(String quizNotFound) {
        super(quizNotFound);
    }
}
