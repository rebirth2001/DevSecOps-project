package com.quizly.quizs.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserQuizsDto {
    private Long quizsTaken;
    private Long quizsCreated;
}
