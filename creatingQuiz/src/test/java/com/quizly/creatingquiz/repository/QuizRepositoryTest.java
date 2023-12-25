package com.quizly.creatingquiz.repository;

import com.quizly.creatingquiz.model.Quiz;
import com.quizly.creatingquiz.respository.QuizRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class QuizRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private QuizRepository quizRepository;

    // Utility method to create and persist a quiz
    private Quiz createAndPersistQuiz(String title, String invitationCode) {
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setDescription("Sample description");
        quiz.setCodeOfInvitation(invitationCode);
        quiz.setQuestionNumber(10);
        entityManager.persist(quiz);
        return quiz;
    }

    // Test for findQuizById
    @Test
    public void whenFindById_thenReturnQuiz() {
        // Arrange
        Quiz quiz = createAndPersistQuiz("Java Quiz", "JAVA123");
        entityManager.flush();

        // Act
        Optional<Quiz> found = quizRepository.findQuizById(quiz.getId());

        // Assert
        assertTrue(found.isPresent());
        assertEquals("Java Quiz", found.get().getTitle());
    }

    // Test for findQuizzesByCodeOfInvitation
    @Test
    public void whenFindByCodeOfInvitation_thenReturnQuiz() {
        // Arrange
        String invitationCode = "INVITE123";
        Quiz quiz = createAndPersistQuiz("Python Quiz", invitationCode);
        entityManager.flush();

        // Act
        Quiz found = quizRepository.findQuizzesByCodeOfInvitation(invitationCode);

        // Assert
        assertNotNull(found);
        assertEquals("Python Quiz", found.getTitle());
        assertEquals(invitationCode, found.getCodeOfInvitation());
    }
}
