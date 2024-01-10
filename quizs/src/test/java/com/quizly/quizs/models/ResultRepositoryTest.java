package com.quizly.quizs.models;

import com.quizly.quizs.models.Quiz;
import com.quizly.quizs.models.Result;
import com.quizly.quizs.repositories.ResultRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ResultRepositoryTest {

    @Mock
    private ResultRepository resultRepository;

    private Quiz quiz;
    private Result result;

    @BeforeEach
    void setUp() {
        quiz = new Quiz();
        quiz.setId(1L);
        // Set other necessary fields of the Quiz object

        result = new Result();
        result.setId(1L);
        result.setQuiz(quiz);
        // Set other necessary fields of the Result object
    }

    @Test
    void testFindResultsByQuiz() {
        List<Result> results = Arrays.asList(result);
        when(resultRepository.findResultsByQuiz(quiz)).thenReturn(results);

        List<Result> foundResults = resultRepository.findResultsByQuiz(quiz);

        assertFalse(foundResults.isEmpty());
        assertEquals(1, foundResults.size());
        assertEquals(result.getId(), foundResults.get(0).getId());
        assertSame(quiz, foundResults.get(0).getQuiz());
        verify(resultRepository, times(1)).findResultsByQuiz(quiz);
    }
}
