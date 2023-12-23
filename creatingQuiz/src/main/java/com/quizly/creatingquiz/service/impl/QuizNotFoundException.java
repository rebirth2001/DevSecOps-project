package com.quizly.creatingquiz.service.impl;

public class QuizNotFoundException extends RuntimeException {
    public QuizNotFoundException(String quizNotFound) {
        super(quizNotFound);
    }
}
