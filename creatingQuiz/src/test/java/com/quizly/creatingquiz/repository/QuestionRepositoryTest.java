package com.quizly.creatingquiz.repository;

import com.quizly.creatingquiz.model.Question;
import com.quizly.creatingquiz.respository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class QuestionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private QuestionRepository questionRepository;

    // Utility method to create a question
    private Question createQuestion(String statement) {
        Question question = new Question();
        question.setStatement(statement);
        // Set other properties of Question as needed
        return question;
    }

    // Test for findQuestionById method
    @Test
    public void whenFindById_thenReturnQuestion() {
        // Arrange
        String statement = "What is the capital of France?";
        Question savedQuestion = createQuestion(statement);
        entityManager.persist(savedQuestion);
        entityManager.flush();

        // Act
        Optional<Question> foundQuestion = questionRepository.findQuestionById(savedQuestion.getId());

        // Assert
        assertTrue(foundQuestion.isPresent(), "Question should be found by ID");
        assertEquals(statement, foundQuestion.get().getStatement(), "Question statement should match");
    }
}
