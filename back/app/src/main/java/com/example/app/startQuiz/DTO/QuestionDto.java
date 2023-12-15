package com.example.app.startQuiz.DTO;

import lombok.Data;

import java.util.List;

@Data
public class QuestionDto {
    private String statement;
    private List<AnswerDto> answers;
}
