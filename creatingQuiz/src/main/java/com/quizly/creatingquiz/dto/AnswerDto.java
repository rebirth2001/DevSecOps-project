package com.quizly.creatingquiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerDto {
    private String text;
    private boolean correct;
}