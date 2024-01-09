package com.quizly.quizs.services;

import com.quizly.quizs.dtos.ParticipantDto;
import com.quizly.quizs.models.Participant;
import com.quizly.quizs.repositories.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    @Autowired
    public ParticipantService(ParticipantRepository participantRepository){
        this.participantRepository = participantRepository;
    }
    public Participant addParticipant(Participant participant){
        return participantRepository.save(participant);
    }

    public Participant saveParticipant(Participant participant) {
        if (participant.getId() != null && participantRepository.existsById(participant.getId())) {
            return participant;
        }
        return participantRepository.save(participant);
    }

    public Long countParticipationByUsername(String user) {
        return participantRepository.countParticipantByNameOfParticipant(user);
    }
}
