package com.quizly.creatingquiz.respository;

import com.quizly.creatingquiz.model.Participant;
import com.quizly.creatingquiz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Optional<Question> findParticipantById(Long id);

}