package com.quizly.quizs.dtos;

import lombok.Data;

@Data
public class ResultDto {
    private Long id;
    private int score;
    private ParticipantDto participant;
    private QuizDto quiz;
    private Long quizId;
}
