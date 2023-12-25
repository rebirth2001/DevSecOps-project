package com.quizly.creatingquiz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class QuizDto {
    @NotBlank(message = "title is required")
    private String title;
    @NotEmpty(message = "description is required")
    private String description;
    @NotNull(message = "questions are required")
    private List<QuestionDto> questions;
    private String codeOfInvitation;
    private int questionNumber;
    private List<ParticipantDto> participants;
    private Long id;
}
