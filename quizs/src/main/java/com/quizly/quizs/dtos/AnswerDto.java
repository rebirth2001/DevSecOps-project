package com.quizly.quizs.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {
    private Long id;
    private String text;
    private boolean correct;
    //private QuestionDto question;
    //private Long questionId;
}
