package com.example.app.startQuiz.service.impl;

import com.example.app.startQuiz.model.Question;
import com.example.app.startQuiz.model.Quiz;
import com.example.app.startQuiz.repository.QuestionRepository;
import com.example.app.startQuiz.repository.QuizRepository;
import com.example.app.startQuiz.service.QuestionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;
    @Override
    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findQuestionById(id);
    }

    @Override
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    public void addQuestionsToQuiz(Long quizId, List<Question> questions) {
        Quiz quiz = quizRepository.findQuizById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found"));
        for (Question question : questions){
            question.setQuiz(quiz);
        }
        quiz.getQuestions().addAll(questions);
        quizRepository.save(quiz);
    }
}
