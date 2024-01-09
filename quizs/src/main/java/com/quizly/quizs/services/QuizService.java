package com.quizly.quizs.services;

import com.quizly.quizs.dtos.DtoConv;
import com.quizly.quizs.dtos.QuizDto;
import com.quizly.quizs.models.Question;
import com.quizly.quizs.models.Quiz;
import com.quizly.quizs.models.Result;
import com.quizly.quizs.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizService {
    private final QuizRepository quizRepo;
    @Autowired
    public QuizService(QuizRepository quizRepo) {
        this.quizRepo = quizRepo;
    }
    public Optional<Quiz> getQuizById(Long id){ return quizRepo.findById(id);}
    // Save or update a question
    public Quiz saveQuiz(Quiz quiz) {
        return quizRepo.save(quiz);
    }

    public List<Quiz> getQuizByOwner(String owner) {return quizRepo.findQuizzesByOwner(owner);}
    public Quiz updateQuiz(Long quizId, QuizDto quizDto){
        Quiz quiz = quizRepo.findById(quizId)
                .orElseThrow(() -> new RuntimeException("quiz not found"));
        quiz.setTitle(quizDto.getTitle());
        quiz.setOwner(quizDto.getOwner());
//        List<Result> results = quizDto.getResults().stream()
//                .map(DtoConv::resultDtoToResult)
//                .collect(Collectors.toList());
//        quiz.setResults(results);
//        quiz.setExpiresAt(quizDto.getExpiresAt());
//        quiz.setCreatedAt(quizDto.getCreatedAt());
        quiz.setId(quizDto.getId());
        quiz.setAttempts(quizDto.getAttempts());
        List<Question> questions = quizDto.getQuestions().stream()
                        .map(DtoConv::questionDtoToQuestion)
                                .collect(Collectors.toList());
        quiz.setQuestions(questions);
        return quizRepo.save(quiz);
    }

    public Long countQuizzesByOwner(String user) {
        return quizRepo.countQuizzesByOwner(user);
    }
}
