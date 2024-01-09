package com.quizly.quizs.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerQuizDto {
    private String owner;
    private QuizDto quiz;
}
