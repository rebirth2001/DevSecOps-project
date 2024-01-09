package com.quizly.quizs.controller;

import com.quizly.quizs.constants.SecurityConstants;
import com.quizly.quizs.dtos.*;
import com.quizly.quizs.models.*;
import com.quizly.quizs.services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/")
public class QuizController {
    @Autowired
    private QuizService quizService;
    @Autowired
    private ParticipantService participantService;
    @Autowired
    private ResultService resultService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    @PostMapping("/{quizId}")
    public ResponseEntity<?> addParticipant(
            @PathVariable Long quizId,
            @RequestBody ResultDto resultDto
    ){
        try{
        Optional<Quiz> quizOptional = quizService.getQuizById(quizId);
        if (quizOptional.isEmpty()){
            return new ResponseEntity<>("Quiz not found", HttpStatus.NOT_FOUND);
        }
        Quiz quiz = quizOptional.get();
        Result result = DtoConv.resultDtoToResult(resultDto);
        result.setQuiz(quiz);
        resultService.saveResult(result);
        return new ResponseEntity<>("Result added to the quiz", HttpStatus.CREATED);
        } catch (Exception e){
            System.out.println("Error adding result to the quiz" + e.getMessage());
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/statistics/{user}")
    public ResponseEntity<?> getUserStatistics(@PathVariable String user){
        try {
            System.out.println("working");
            int createdQuizzesCount = quizService.countQuizzesByOwner(user);
            int participatedQuizzesCount = participantService.countParticipationByUsername(user);
            Map<String, Integer> stats = new HashMap<>();
            stats.put("createdQuizzes", createdQuizzesCount);
            stats.put("participatedQuizzes", participatedQuizzesCount);
            return ResponseEntity.ok(stats);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving user statistics");
        }
    }
    @GetMapping("/quizzes/{owner}")
    public ResponseEntity<List<QuizDto>> getQuizByOwner(
            @PathVariable String owner
    ) {
        try {
            List<Quiz> quizzes = quizService.getQuizByOwner(owner);
            if (quizzes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<QuizDto> quizDtos = quizzes.stream().map(quiz -> {
                QuizDto quizDto = DtoConv.quizToQuizDto(quiz);

                // Directly use the returned QuestionDto list from the service
                List<QuestionDto> questionDtos = questionService.getQuestionByQuiz(quiz);
                quizDto.setQuestions(questionDtos);

                // Manually set the Results in QuizDto
                List<ResultDto> resultDtos = resultService.getResultByQuiz(quiz);
                quizDto.setResults(resultDtos);

                return quizDto;
            }).collect(Collectors.toList());

            return new ResponseEntity<>(quizDtos, HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<CreateQuizResponse> createQuiz(@RequestHeader(name = SecurityConstants.AUTHORIZED_USER_HEADER) String authorizedUser, @Valid @RequestBody CreateQuizRequest quizDto){
        try{
            Quiz quiz = new Quiz();
            quiz.setTitle(quizDto.getTitle());
            quiz.setOwner(authorizedUser);
            quiz.setCreatedAt(Instant.now());
            quiz.setExpiresAt(quizDto.getExpiryDate());
            List<Question> questions = quizDto.getQuestionsList().stream()
                    .map(questionDto -> {
                        Question question = new Question();
                        question.setText(questionDto.getText());
                        question.setQuiz(quiz);
                        List<Answer> answers = questionDto.getAnswers().stream()
                                .map(answerDto -> {
                                    Answer answer = new Answer();
                                    answer.setText(answerDto.getText());
                                    answer.setCorrect(answerDto.isCorrect());
                                    answer.setQuestion(question);
                                    return answer;
                                })
                                .collect(Collectors.toList());
                        question.setAnswers(answers);
                        return question;
                    })
                    .collect(Collectors.toList());
            quiz.setQuestions(questions);
            quizService.saveQuiz(quiz);
            return new ResponseEntity(new CreateQuizResponse(),HttpStatus.CREATED);
        } catch (Exception e){
            System.out.println("error creating quiz" + e.getMessage());
            return new ResponseEntity<CreateQuizResponse>(new CreateQuizResponse(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}