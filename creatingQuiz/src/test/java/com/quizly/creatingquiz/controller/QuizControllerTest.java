package com.quizly.creatingquiz.controller;

import com.quizly.creatingquiz.dto.AnswerDto;
import com.quizly.creatingquiz.dto.QuestionDto;
import com.quizly.creatingquiz.model.Quiz;
import com.quizly.creatingquiz.service.QuizService;
import com.quizly.creatingquiz.dto.ParticipantDto;
import com.quizly.creatingquiz.dto.QuizDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizService quizService;

    @Autowired
    private ObjectMapper objectMapper;

    // Test for 'Get Quiz by Code' endpoint
    @Test
    public void whenGetQuizByCode_thenReturnQuiz() throws Exception {
        String code = "testCode";
        Quiz quiz = new Quiz();
        given(quizService.getQuizByCode(code)).willReturn(quiz);

        mockMvc.perform(get("/api/v1/quizzes/quiz/{code}", code))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(quiz.getTitle()));
    }

    // Test for 'List All Quizzes' endpoint
    @Test
    public void whenGetAllQuizzes_thenReturnQuizzesList() throws Exception {
        List<Quiz> quizzes = Arrays.asList(new Quiz(), new Quiz()); // Populate list with quizzes
        given(quizService.getAllQuize()).willReturn(quizzes);

        mockMvc.perform(get("/api/v1/quizzes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(quizzes.size()));
    }

    // Test for 'Add Participant to Quiz' endpoint
    @Test
    public void whenAddParticipantToQuiz_thenSuccess() throws Exception {
        String code = "testCode";
        Quiz quiz = new Quiz();
        quiz.setCodeOfInvitation(code);

        // Mock the behavior of quizService to return the quiz when getQuizByCode is called with "testCode"
        given(quizService.getQuizByCode(code)).willReturn(quiz);

        ParticipantDto participantDto = new ParticipantDto();
        participantDto.setNameOfParticipant("test");
        participantDto.setResult(80);

        String participantJson = objectMapper.writeValueAsString(participantDto);

        mockMvc.perform(put("/api/v1/quizzes/{code}/participants", code)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(participantJson))
                .andExpect(status().isOk());
    }

    // Test for 'Create a Quiz' endpoint
    @Test
    public void whenCreateQuiz_thenSuccess() throws Exception {
        QuizDto quizDto = new QuizDto();
        quizDto.setTitle("Sample Quiz");
        quizDto.setDescription("Sample Description");

        QuestionDto questionDto1 = new QuestionDto();
        questionDto1.setStatement("Sample Question 1");
        questionDto1.setAnswers(Arrays.asList(new AnswerDto("Answer 1", true), new AnswerDto("Answer 2", false)));

        QuestionDto questionDto2 = new QuestionDto();
        questionDto2.setStatement("Sample Question 2");
        questionDto2.setAnswers(Arrays.asList(new AnswerDto("Answer 3", false), new AnswerDto("Answer 4", true)));

        quizDto.setQuestions(Arrays.asList(questionDto1, questionDto2));

        String quizJson = objectMapper.writeValueAsString(quizDto);

        mockMvc.perform(post("/api/v1/quizzes/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(quizJson))
                .andExpect(status().isCreated());
    }

}