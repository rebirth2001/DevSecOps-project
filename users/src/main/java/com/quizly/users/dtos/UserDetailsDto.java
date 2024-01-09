package com.quizly.users.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.Instant;

@Builder
@AllArgsConstructor
public class UserDetailsDto {
    @JsonProperty("username")
    private String username;
    @JsonProperty("email")
    private String email;
    @JsonProperty("joinedAt")
    private Instant joinedAt;
    @JsonProperty("followersCount")
    private Long followersCount;
}
