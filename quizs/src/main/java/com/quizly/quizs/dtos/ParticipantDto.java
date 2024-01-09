package com.quizly.quizs.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ParticipantDto {
    private Long id;
    private String nameOfParticipant;
    private List<ResultDto> results;
}
