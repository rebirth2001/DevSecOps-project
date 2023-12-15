package com.example.app.startQuiz.service.impl;

import com.example.app.startQuiz.model.Answer;
import com.example.app.startQuiz.model.Question;
import com.example.app.startQuiz.model.Quiz;
import com.example.app.startQuiz.repository.QuizRepository;
import com.example.app.startQuiz.service.QuizService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Override
    public List<Quiz> getAllQuize() {
        return quizRepository.findAll();
    }
    @Override
    public void saveQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }
    @Override
    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }
    @Override
    public void modifyQuiz(Long id, Quiz updatedQuiz) {
        Quiz existingQuiz = quizRepository.findQuizById(id).orElse(null);
        if (existingQuiz !=null){
            existingQuiz.setDescription(updatedQuiz.getDescription());
            existingQuiz.setTitle(updatedQuiz.getTitle());
            existingQuiz.setQuestionNumber(updatedQuiz.getQuestionNumber());
        }
    }

    @Override
    public Quiz addQuestionToQuiz(Long id, Question question) {
        Quiz quiz = getQuizById(id);
        quiz.getQuestions().add(question);
        question.setQuiz(quiz);
        quizRepository.save(quiz);
        return quiz;
    }

    @Override
    public Quiz addAnswerToQuestion(Long quizId, Long quesionId, Answer answer) {
        Quiz quiz = getQuizById(quizId);
        Question question = quiz.getQuestions().stream()
                .filter(q -> q.getId().equals(quesionId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Question not found"));
        question.getAnswers().add(answer);
        answer.setQuestion(question);
        quizRepository.save(quiz);
        return quiz;
    }

    @Override
    public Quiz getQuizById(Long id) {
        return quizRepository.findQuizById(id).orElseThrow(null);
    }
}
