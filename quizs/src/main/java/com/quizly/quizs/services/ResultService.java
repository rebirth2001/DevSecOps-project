package com.quizly.quizs.services;

import com.quizly.quizs.dtos.DtoConv;
import com.quizly.quizs.dtos.ResultDto;
import com.quizly.quizs.models.Quiz;
import com.quizly.quizs.models.Result;
import com.quizly.quizs.repositories.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResultService {
    private final ResultRepository resultRepository;

    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }
    public ParticipantService participantService;
    @Autowired
    public ResultService(ResultRepository resultRepository, ParticipantService participantService) {
        this.resultRepository = resultRepository;
        this.participantService = participantService;
    }
    // Save or update a result
    public Result saveResult(Result result) {
        if (result.getParticipant() != null && result.getParticipant().getId() == null) {
            // Assuming you have a method in ParticipantService to save a participant
            participantService.saveParticipant(result.getParticipant());
        }
        return resultRepository.save(result);
    }
    public List<ResultDto> getResultByQuiz(Quiz quiz){
        List<Result> results = resultRepository.findResultsByQuiz(quiz);
        List<ResultDto> resultDtos = new ArrayList<>();
        for (Result result : results){
            ResultDto dto = DtoConv.resultToResultDto(result);
            resultDtos.add(dto);
        }
        return resultDtos;
    }
}
