package com.quizly.users.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserQuizsDto {
    private Long quizsTaken;
    private Long quizsCreated;
}
