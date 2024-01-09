package com.quizly.quizs.controller;

import com.quizly.quizs.dtos.OwnerQuizDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizCashe {
    private Map<String, List<OwnerQuizDto>> quizCache = new HashMap<>();}
