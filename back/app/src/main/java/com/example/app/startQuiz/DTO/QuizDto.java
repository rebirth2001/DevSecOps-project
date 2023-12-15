package com.example.app.startQuiz.DTO;

import lombok.Data;

import java.util.List;

@Data
public class QuizDto {
    private String title;
    private String description;
    private List<QuestionDto> questions;
    private String codeOfInvitation;
    private int questionNumber;
    private Long id;
}
