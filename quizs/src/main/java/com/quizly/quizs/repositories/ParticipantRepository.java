package com.quizly.quizs.repositories;

import com.quizly.quizs.models.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    int countParticipantByNameOfParticipant(String participant);
}
