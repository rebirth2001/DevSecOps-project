package com.example.app.participant.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParticipantDTO {
    private Long id;
    private String nameOfParticipant;
    private int result;
    private Long quizId;
}
