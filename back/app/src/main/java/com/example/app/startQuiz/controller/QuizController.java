package com.example.app.startQuiz.controller;

import com.example.app.startQuiz.model.Answer;
import com.example.app.startQuiz.model.Question;
import com.example.app.startQuiz.model.Quiz;
import com.example.app.startQuiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/quizzes")
public class QuizController {
    @Autowired
    private QuizService quizService;
    @GetMapping
    public List<Quiz> ListQuizzes(){
        return quizService.getAllQuize();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long id) {
        Quiz quiz = quizService.getQuizById(id);
        return ResponseEntity.ok().body(quiz);
    }

    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
        quizService.saveQuiz(quiz);
        System.out.println("added");
        return ResponseEntity.status(HttpStatus.CREATED).body(quiz);
    }

    @PutMapping("{id}")
    public ResponseEntity<Quiz> updateTask(@PathVariable Long id, @RequestBody Quiz quiz) {
        quizService.modifyQuiz(id, quiz);
        return ResponseEntity.ok().body(quiz);
    }
    @PostMapping("{id}/add-question")
    public ResponseEntity<Quiz> addQuestionToQuiz(@PathVariable Long id, @RequestBody Question question){
        Quiz updatedQuiz = quizService.addQuestionToQuiz(id, question);
        return ResponseEntity.ok().body(updatedQuiz);
    }
    @PostMapping("/{id}/add-answer/{quesionId}")
    public ResponseEntity<Quiz> addAnswerToquesion(
            @PathVariable Long id,
            @PathVariable Long quesionId,
            @RequestBody Answer answer
            ){
        Quiz updatedQuiz = quizService.addAnswerToQuestion(id, quesionId, answer);
        return ResponseEntity.ok().body(updatedQuiz);
    }
}
