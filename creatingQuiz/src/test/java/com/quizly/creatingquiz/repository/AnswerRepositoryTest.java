package com.quizly.creatingquiz.repository;

import com.quizly.creatingquiz.model.Answer;
import com.quizly.creatingquiz.model.Question;
import com.quizly.creatingquiz.respository.AnswerRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AnswerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AnswerRepository answerRepository;

    //method to create a question
    private Question createQuestion() {
        Question question = new Question();
        question.setStatement("Example Question");
        return question;
    }

    // Utility method to create and persist an answer
    private Answer createAndPersistAnswer(String text, boolean isCorrect, Question question) {
        Answer answer = new Answer();
        answer.setText(text);
        answer.setCorrect(isCorrect);
        answer.setQuestion(question);
        entityManager.persist(answer);
        return answer;
    }

    // Test for findAnswerById method
    @Test
    public void whenFindById_thenReturnAnswer() {
        // Arrange
        Question question = createQuestion();
        entityManager.persist(question);
        Answer savedAnswer = createAndPersistAnswer("Test Answer", true, question);
        entityManager.flush();

        // Act
        Answer foundAnswer = answerRepository.findAnswerById(savedAnswer.getId()).orElse(null);

        // Assert
        assertNotNull(foundAnswer, "Answer should be found by ID");
        assertEquals(savedAnswer.getText(), foundAnswer.getText(), "Answer text should match");
    }

    // Test for findByQuestionId method
    @Test
    public void whenFindByQuestionId_thenReturnAnswersList() {
        // Arrange
        Question question = createQuestion();
        entityManager.persist(question);
        createAndPersistAnswer("Answer 1", true, question);
        createAndPersistAnswer("Answer 2", false, question);
        entityManager.flush();

        // Act
        List<Answer> answers = answerRepository.findByQuestionId(question.getId());

        // Assert
        assertEquals(2, answers.size(), "Should return 2 answers for the question");
        assertTrue(answers.stream().anyMatch(a -> a.getText().equals("Answer 1")), "List should contain 'Answer 1'");
        assertTrue(answers.stream().anyMatch(a -> a.getText().equals("Answer 2")), "List should contain 'Answer 2'");
    }
}
