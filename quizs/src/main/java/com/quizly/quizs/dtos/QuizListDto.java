package com.quizly.quizs.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class QuizListDto {
    @JsonProperty("quizs")
    private  List<QuizDto> quizs;
    @JsonProperty("isLast")
    private  boolean isLast;
    @JsonProperty("page")
    private  Long page;
}
