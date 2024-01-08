package com.quizly.users.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDto {
    @JsonProperty("accessToken")
    private String accessToken;
    @JsonProperty("username")
    private String username;
    @JsonProperty("errors")
    private List<String> errors;
    @JsonProperty("isError")
    private boolean isError;
}