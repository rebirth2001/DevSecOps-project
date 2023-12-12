package com.example.app.startQuiz.controller;

import com.example.app.startQuiz.model.Question;
import com.example.app.startQuiz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @PostMapping("/add/{quizId}")
    public ResponseEntity<Void> addQuestionsToQuizz(
            @PathVariable long quizId,
            @RequestBody List<Question> questions){
        questionService.addQuestionsToQuiz(quizId, questions);
        return  ResponseEntity.ok().build();
    }
}
