package com.example.app.startQuiz.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerDto {
    private String text;
    private boolean correct;
}
