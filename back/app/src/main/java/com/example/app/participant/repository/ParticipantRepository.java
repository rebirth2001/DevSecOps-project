package com.example.app.participant.repository;

import com.example.app.participant.model.Participant;
import com.example.app.startQuiz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Optional<Question> findParticipantById(Long id);

}