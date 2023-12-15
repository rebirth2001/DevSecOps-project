package com.example.app.participant.model;

import com.example.app.startQuiz.model.Quiz;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "participants")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nameOfParticipant;
    private int result;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
}
