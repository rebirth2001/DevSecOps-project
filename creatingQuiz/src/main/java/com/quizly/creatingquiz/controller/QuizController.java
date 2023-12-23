package com.quizly.creatingquiz.controller;

import com.quizly.creatingquiz.dto.AnswerDto;
import com.quizly.creatingquiz.dto.ParticipantDto;
import com.quizly.creatingquiz.dto.QuestionDto;
import com.quizly.creatingquiz.dto.QuizDto;
import com.quizly.creatingquiz.model.Answer;
import com.quizly.creatingquiz.model.Participant;
import com.quizly.creatingquiz.model.Question;
import com.quizly.creatingquiz.model.Quiz;
import com.quizly.creatingquiz.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/quizzes")
@Tag(name = "Quiz controller", description = "this controller handle the creation of quizzes and it adds participant after passing a quiz.")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @GetMapping
    @Operation(summary = "List all quizzes", description = "retrieve the list of all quizzes and dispalays them. It also get the quiz ID")
    public ResponseEntity<List<QuizDto>> getAllQuizzes(){
        List<QuizDto> quizzes = quizService.getAllQuize().stream()
                .map(quiz -> {
                    QuizDto quizDto = new QuizDto();
                    quizDto.setTitle(quiz.getTitle());
                    quizDto.setDescription(quiz.getDescription());
                    quizDto.setQuestions(quiz.getQuestions().stream()
                            .map(this::convertQuestionToDto)
                            .collect(Collectors.toList()));
                    quizDto.setCodeOfInvitation(quiz.getCodeOfInvitation());
                    quizDto.setQuestionNumber(quiz.getQuestionNumber());
                    quizDto.setId(quiz.getId());
                    return quizDto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(quizzes);
    }

    private QuestionDto convertQuestionToDto(Question question) {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setAnswers(question.getAnswers().stream().map(answer -> {
            return new AnswerDto(answer.getText(),answer.isCorrect());
        }).collect(Collectors.toList()));
        questionDto.setStatement(question.getStatement());
        return questionDto;
    }
    private QuizDto convertQuizToDto(Quiz quiz){
        QuizDto quizDto = new QuizDto();
        quizDto.setTitle(quiz.getTitle());
        quizDto.setDescription(quiz.getDescription());
        quizDto.setQuestionNumber(quiz.getQuestionNumber());
        List<QuestionDto> questionDtos = quiz.getQuestions().stream()
                .map(question -> new QuestionDto(question.getStatement(), convertAnswersToDtos(question.getAnswers())))
                .collect(Collectors.toList());
        quizDto.setQuestions(questionDtos);
        return quizDto;
    }

    private List<AnswerDto> convertAnswersToDtos(List<Answer> answers) {
        return answers.stream()
                .map(answer -> new AnswerDto(answer.getText(), answer.isCorrect()))
                .collect(Collectors.toList());
    }

    @PutMapping("/{code}/participants")
    @Operation(summary = "Add Participant to Quiz", description = "Add a new participant to a specific quiz")
    @ApiResponse(responseCode = "200", description = "Participant added successfully")
    @ApiResponse(responseCode = "404", description = "Quiz not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<String> addParticipantToQuiz(
            @Parameter(description = "unique code of the quiz the participant has passed.") @PathVariable String code,
            @RequestBody ParticipantDto participantDTO
    ){
        try {
            Quiz quiz = quizService.getQuizByCode(code);
            if (quiz == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Participant participant = new Participant();
            participant.setNameOfParticipant(participantDTO.getNameOfParticipant());
            participant.setResult(participantDTO.getResult());
            participant.setQuiz(quiz);
            quiz.getParticipant().add(participant);
            quizService.saveQuiz(quiz);
            return ResponseEntity.ok("participant added to the quiz");
        }catch (Exception e){
            return new ResponseEntity<>("error adding quiz" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/create")
    @Operation(summary = "Create a Quiz", description = "Create a new quiz with provided details. The request should include all the necessary fields so the creation of the quiz is after all the details are provided by the quiz creator.")
    @ApiResponse(responseCode = "201", description = "Quiz created successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
        try{
            Quiz quiz = new Quiz();
            quiz.setTitle(quizDto.getTitle());
            quiz.setDescription(quizDto.getDescription());
            quiz.setQuestionNumber(quizDto.getQuestions().size());
            for (QuestionDto questionDto : quizDto.getQuestions()){
                Question question = new Question();
                question.setStatement(questionDto.getStatement());
                for (AnswerDto answerDto : questionDto.getAnswers()){
                    Answer answer = new Answer();
                    answer.setText(answerDto.getText());
                    answer.setCorrect(answerDto.isCorrect());
                    answer.setQuestion(question);
                    question.getAnswers().add(answer);
                }
                question.setQuiz(quiz);
                quiz.getQuestions().add(question);
            }
            quizService.saveQuiz(quiz);
            return new ResponseEntity<>("Quiz created successfully", HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>("error creating quiz" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
