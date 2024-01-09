package com.quizly.quizs.dtos;

import com.quizly.quizs.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DtoConv {
    public static Quiz quizDtoToEntity(QuizDto dto){
        Quiz quiz = new Quiz();
        quiz.setId(dto.getId());
        quiz.setTitle(dto.getTitle());
        quiz.setOwner(dto.getOwner());
        quiz.setAttempts(dto.getAttempts());
//        quiz.setCreatedAt(dto.getCreatedAt());
//        quiz.setExpiresAt(dto.getExpiresAt());

        List<Question> questions;
        if (dto.getQuestions() != null) {
            questions = dto.getQuestions().stream()
                    .map(DtoConv::questionDtoToQuestion)
                    .collect(Collectors.toList());
        } else {
            questions = new ArrayList<>(); // Initialize with an empty list if null
        }
        quiz.setQuestions(questions);

        List<Result> results;
//        if (dto.getResults() != null) {
//            results = dto.getResults().stream()
//                    .map(DtoConv::resultDtoToResult)
//                    .collect(Collectors.toList());
//        } else {
//            results = new ArrayList<>(); // Similarly handle results
//        }
//        quiz.setResults(results);

        return quiz;
    }
    public static QuizDto quizToQuizDto(Quiz quiz){
        QuizDto dto = new QuizDto();
        dto.setId(quiz.getId());
        dto.setTitle(quiz.getTitle());
        dto.setOwner(quiz.getOwner());
        dto.setAttempts(quiz.getAttempts());
        dto.setCreatedAt(quiz.getCreatedAt().toString());
        dto.setExpiresAt(quiz.getExpiresAt().toString());
        List<QuestionDto> questionDtos = quiz.getQuestions().stream()
                .map(DtoConv::questionToQuestionDto)
                    .collect(Collectors.toList());
        dto.setQuestions(questionDtos);
        List<ResultDto> resultDtos = quiz.getResults().stream()
                .map(DtoConv::resultToResultDto)
                    .collect(Collectors.toList());
//        dto.setResults(resultDtos);
        return dto;
    }
    public static Answer answerDtoToAnswer(AnswerDto answerDto){
        Answer answer = new Answer();
        answer.setId(answerDto.getId());
        answer.setText(answerDto.getText());
        answer.setCorrect(answerDto.isCorrect());
//        if (answerDto.getQuestion() != null) {
//            answer.setQuestion(questionDtoToQuestion(answerDto.getQuestion()));
//        }
        return answer;
    }
    public static AnswerDto answerToAnswerDto(Answer answer){
        AnswerDto dto = new AnswerDto();
        dto.setId(answer.getId());
        dto.setText(answer.getText());
        dto.setCorrect(answer.isCorrect());
//        if (answer.getQuestion() != null) {
//            dto.setQuestionId(answer.getQuestion().getId());
//        }
        return dto;
    }
    public static ResultDto resultToResultDto(Result result, boolean convertParticipant) {
        ResultDto dto = new ResultDto();
        dto.setId(result.getId());
        dto.setScore(result.getScore());

        if (convertParticipant) {
            dto.setParticipant(participantToParticipantDto(result.getParticipant(), false));
        }

        // Convert only necessary quiz attributes to avoid recursion
        Quiz quiz = result.getQuiz();
        if (quiz != null) {
            dto.setQuizId(quiz.getId()); // Set only the ID or other necessary fields
        }

        return dto;
    }

    public static ResultDto resultToResultDto(Result result) {
        return resultToResultDto(result, true);
    }

    public static Result resultDtoToResult(ResultDto resultDto){
        Result result = new Result();
        result.setId(resultDto.getId());
        result.setScore(resultDto.getScore());
        result.setParticipant(participantDtoToParticipant(resultDto.getParticipant()));
        result.setQuiz(quizDtoToEntity(resultDto.getQuiz()));
        return result;
    }

    public static Participant participantDtoToParticipant(ParticipantDto participantDto) {
        Participant participant = new Participant();
        participant.setId(participantDto.getId());
        participant.setNameOfParticipant(participantDto.getNameOfParticipant());

        List<Result> results;
        if (participantDto.getResults() != null) {
            results = participantDto.getResults().stream()
                    .map(DtoConv::resultDtoToResult)
                    .collect(Collectors.toList());
        } else {
            results = new ArrayList<>(); // Initialize with an empty list if null
        }
        participant.setResults(results);

        return participant;
    }
    public static ParticipantDto participantToParticipantDto(Participant participant, boolean convertResults) {
        ParticipantDto participantDto = new ParticipantDto();
        participantDto.setId(participant.getId());
        participantDto.setNameOfParticipant(participant.getNameOfParticipant());

        if (convertResults) {
            List<ResultDto> resultDtos = participant.getResults().stream()
                    .map(result -> resultToResultDto(result, false)) // Avoid converting Participant again
                    .collect(Collectors.toList());
            participantDto.setResults(resultDtos);
        }
        return participantDto;
    }

    // Overloaded method for compatibility
    public static ParticipantDto participantToParticipantDto(Participant participant) {
        return participantToParticipantDto(participant, true);
    }
    public static QuestionDto questionToQuestionDto(Question question){
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(question.getId());
        questionDto.setText(question.getText());
        List<AnswerDto> answerDtos = question.getAnswers().stream()
                .map(DtoConv::answerToAnswerDto)
                .collect(Collectors.toList());
        questionDto.setAnswers(answerDtos);
//        if (question.getQuiz() != null) {
//            questionDto.setQuizID(question.getQuiz().getId());
//        }
        return questionDto;
    }
    public static Question questionDtoToQuestion(QuestionDto questionDto) {
        Question question = new Question();
        question.setId(questionDto.getId());
        question.setText(questionDto.getText());
        List<Answer> answers = questionDto.getAnswers().stream()
                .map(DtoConv::answerDtoToAnswer)
                .collect(Collectors.toList());
        question.setAnswers(answers);
//        if (questionDto.getQuiz() != null) {
//            question.setQuiz(quizDtoToEntity(questionDto.getQuiz()));
//        }
        return question;
    }
}
