package com.quizly.quizs.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private Long id;
    private String text;
    private List<AnswerDto> answers;
    private QuizDto quiz;
    private Long quizID;
}
