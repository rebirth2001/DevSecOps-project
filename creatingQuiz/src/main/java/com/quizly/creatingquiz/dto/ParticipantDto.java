package com.quizly.creatingquiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDto {
    private Long id;
    private String nameOfParticipant;
    private int result;
    private Long quizId;
}