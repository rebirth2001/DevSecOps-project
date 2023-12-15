package com.example.app.startQuiz.model;

import com.example.app.startQuiz.DTO.AnswerDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String statement;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "question", fetch = FetchType.EAGER)
    private List<Answer> answers = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
}
