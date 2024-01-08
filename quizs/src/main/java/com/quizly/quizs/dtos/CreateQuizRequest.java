package com.quizly.quizs.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.quizly.quizs.models.Answer;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@RequiredArgsConstructor
public class CreateQuizRequest {
    @NotBlank
    @JsonProperty("title")
    private String title;
    @NotNull
    @JsonProperty("questions")
    private List<Question> questions;
    @NotNull
    @Min(0)
    @JsonProperty("length")
    private Long length;
    @JsonProperty("duration")
    private Duration duration;

    public List<com.quizly.quizs.models.Question> getQuestionsList(){
        return this.questions.stream().map(question -> {
            com.quizly.quizs.models.Question model = new com.quizly.quizs.models.Question();
            model.setText(question.getText());
            model.setAnswers(question.getAnswersList());
            return model;
        })
        .toList();
    }

    public Instant getExpiryDate(){
        return Instant.now().plus(this.duration.toDuration());
    }
}

@Data
@RequiredArgsConstructor
class Question {
    @NotBlank
    private String text;
    @NotNull
    private List<Choice> choices;


    public List<Answer> getAnswersList(){
        return this.choices.stream().map(choice -> {
            Answer answer = new Answer();
            answer.setText(choice.getText());
            answer.setCorrect(choice.isCorrect());
            //answer.setQuestion(model);
            return answer;
        })
        .toList();
    }

}

@Data
@RequiredArgsConstructor
class Choice {
    @NotBlank
    private String text;
    @NotNull
    private boolean correct;
}
@Data
@RequiredArgsConstructor
class Duration {
    @Min(0)
    @NotNull
    private Long days;
    @Min(0)
    @NotNull
    private Long hours;

    public java.time.Duration toDuration(){
        return java.time.Duration.ofDays(this.days).plusHours(this.hours);
    }
}
