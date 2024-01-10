package com.quizly.quizs.models;

import com.quizly.quizs.models.Answer;
import com.quizly.quizs.repositories.AnswerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AnswerRepositoryTest {

    @Mock
    private AnswerRepository answerRepository;

    private Answer answer;

    @BeforeEach
    void setUp() {
        answer = new Answer();
        answer.setId(1L);
        // Set other necessary fields of the Answer object
    }

    @Test
    void testFindById() {
        when(answerRepository.findById(1L)).thenReturn(Optional.of(answer));

        Optional<Answer> found = answerRepository.findById(1L);

        assertTrue(found.isPresent());
        assertEquals(answer.getId(), found.get().getId());
        verify(answerRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByQuestionId() {
        List<Answer> answers = Arrays.asList(answer);
        when(answerRepository.findByQuestionId(1L)).thenReturn(answers);

        List<Answer> foundAnswers = answerRepository.findByQuestionId(1L);

        assertFalse(foundAnswers.isEmpty());
        assertEquals(1, foundAnswers.size());
        assertEquals(answer.getId(), foundAnswers.get(0).getId());
        verify(answerRepository, times(1)).findByQuestionId(1L);
    }
}
