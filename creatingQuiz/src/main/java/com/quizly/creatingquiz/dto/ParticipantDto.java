package com.quizly.creatingquiz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDto {
    @NotEmpty(message = "a user must have a username")
    private String nameOfParticipant;
    @NotNull(message = "there is no results")
    private int result;
}