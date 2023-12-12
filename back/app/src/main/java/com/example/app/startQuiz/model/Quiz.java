package com.example.app.startQuiz.model;

import com.example.app.participant.model.Participant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "quizzes")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "quiz", fetch = FetchType.EAGER)
    private List<Question> questions = new ArrayList<>();
    private String codeOfInvitation;
    private String title;
    private String description;
    private int questionNumber;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "quiz", fetch = FetchType.EAGER)
    private List<Participant> participant = new ArrayList<>();

    private String generateRandomInvitaionCode(){
        int codeLength = 8;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < codeLength; i++){
            int randomIndex = random.nextInt(characters.length());
            code.append(characters.charAt(randomIndex));
        }
        return code.toString();
    }
    @PrePersist
    private void generateInvitationCode(){
        if (codeOfInvitation == null){
        this.codeOfInvitation = generateRandomInvitaionCode();
        }
    }
}
