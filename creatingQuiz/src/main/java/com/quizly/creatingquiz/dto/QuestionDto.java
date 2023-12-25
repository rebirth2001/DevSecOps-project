package com.quizly.creatingquiz.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    @NotEmpty(message = "statement is required")
    private String statement;
    @NotNull(message = "Questions must have at least 2 questions")
    @Size(min = 2, message = "more than one answer")
    @Size(max = 4, message = "no more than 4 questions")
    private List<AnswerDto> answers;
}
