package com.example.app.startQuiz.controller;

import com.example.app.participant.DTO.ParticipantDTO;
import com.example.app.participant.model.Participant;
import com.example.app.startQuiz.DTO.AnswerDto;
import com.example.app.startQuiz.DTO.QuestionDto;
import com.example.app.startQuiz.DTO.QuizDto;
import com.example.app.startQuiz.model.Answer;
import com.example.app.startQuiz.model.Question;
import com.example.app.startQuiz.model.Quiz;
import com.example.app.startQuiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/quizzes")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @GetMapping
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
    public ResponseEntity<String> addParticipantToQuiz(
            @PathVariable String code,
            @RequestBody ParticipantDTO participantDTO
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
