package com.quizly.quizs.controller;

import com.quizly.quizs.constants.SecurityConstants;
import com.quizly.quizs.dtos.CreateQuizRequest;
import com.quizly.quizs.dtos.CreateQuizResponse;
import com.quizly.quizs.models.Question;
import com.quizly.quizs.models.Quiz;
import com.quizly.quizs.services.QuizService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/")
public class QuizController {
    @Autowired
    private QuizService quizService;

//    @GetMapping("/quiz/{code}")
//    public ResponseEntity<?> getQuizBycode(
//            @Parameter(description = "Unique code of the quiz.")
//            @PathVariable String code){
//        Quiz quiz = quizService.getQuizByCode(code);
//        if (quiz == null){
//            return new ResponseEntity<>("no quizzes qith this code", HttpStatus.NOT_FOUND);
//        }
//        QuizDto quizDto = convertQuizToDto(quiz);
//        return ResponseEntity.ok(quizDto);
//    }
//    private @NotNull ParticipantDto convertParticipantToDto(@NotNull Participant participant){
//        ParticipantDto participantDto = new ParticipantDto();
//        participantDto.setNameOfParticipant(participant.getNameOfParticipant());
//        participantDto.setResult(participantDto.getResult());
//        return participantDto;
//    }
//    private @NotNull QuestionDto convertQuestionToDto(@NotNull Question question) {
//        QuestionDto questionDto = new QuestionDto();
//        questionDto.setAnswers(question.getAnswers().stream().map(answer -> {
//            return new AnswerDto(answer.getText(),answer.isCorrect());
//        }).collect(Collectors.toList()));
//        questionDto.setStatement(question.getStatement());
//        return questionDto;
//    }

    @PostMapping("/create")
    public ResponseEntity<CreateQuizResponse> createQuiz(@RequestHeader(name = SecurityConstants.AUTHORIZED_USER_HEADER) String authorizedUser, @Valid @RequestBody CreateQuizRequest quizDto){
        try{
            Quiz quiz = new Quiz();
            quiz.setTitle(quizDto.getTitle());
            quiz.setQuestions(quizDto.getQuestionsList());
            quiz.setOwner(authorizedUser);
            quiz.setCreatedAt(Instant.now());
            quiz.setExpiresAt(quizDto.getExpiryDate());
            quizService.saveQuiz(quiz);
            return new ResponseEntity(new CreateQuizResponse(),HttpStatus.CREATED);
        } catch (Exception e){
            System.out.println("error creating quiz" + e.getMessage());
            return new ResponseEntity<CreateQuizResponse>(new CreateQuizResponse(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}