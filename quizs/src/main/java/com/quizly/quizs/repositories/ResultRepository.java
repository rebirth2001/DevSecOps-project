package com.quizly.quizs.repositories;

import com.quizly.quizs.models.Quiz;
import com.quizly.quizs.models.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {

    List<Result> findResultsByQuiz(Quiz quiz);
}
