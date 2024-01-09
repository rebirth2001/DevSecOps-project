package com.quizly.quizs.dtos;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class QuizDto {
    private Long id;
    private String createdAt;
    private String expiresAt;
    private String title;
    private String owner;
    private Long attempts;
    private List<QuestionDto> questions;
//    private List<ResultDto> results;
}
