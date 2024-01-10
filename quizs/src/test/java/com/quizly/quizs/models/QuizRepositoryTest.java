package com.quizly.quizs.models;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class QuizRepositoryTest {
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testQuizEntityMapping() {
        // Create a Quiz entity and persist it
        Quiz quiz = new Quiz();
        quiz.setTitle("Sample Quiz");
        quiz.setOwner("TestOwner");
        quiz.setCreatedAt(Instant.now());
        quiz.setExpiresAt(Instant.now().plusSeconds(3600));
        quiz.setAttempts(0L);

        entityManager.persist(quiz);
        entityManager.flush();
        entityManager.clear();

        // Retrieve the persisted entity from the database
        Quiz retrievedQuiz = entityManager.find(Quiz.class, quiz.getId());

        // Assert that the retrieved entity matches the expected values
        assertNotNull(retrievedQuiz);
        assertEquals("Sample Quiz", retrievedQuiz.getTitle());
        assertEquals("TestOwner", retrievedQuiz.getOwner());
        assertNotNull(retrievedQuiz.getCreatedAt());
        assertNotNull(retrievedQuiz.getExpiresAt());
        assertEquals(0L, retrievedQuiz.getAttempts());
    }
}
