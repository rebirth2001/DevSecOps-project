package com.quizly.creatingquiz.service;

import com.quizly.creatingquiz.model.Participant;
import com.quizly.creatingquiz.respository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ParticipantService {
    private final ParticipantRepository participantRepository;

    @Autowired
    public  ParticipantService(ParticipantRepository participantRepository){
        this.participantRepository=participantRepository;
    }

    public Participant saveParticipent(Participant participant){
        return participantRepository.save(participant);
    }
    public List<Participant> findAll(){
        try {
            return participantRepository.findAll();
        } catch (DataAccessException e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();
            // You might want to throw a custom exception or return a default value
            return Collections.emptyList(); // Or another appropriate default value
        }
    }
}