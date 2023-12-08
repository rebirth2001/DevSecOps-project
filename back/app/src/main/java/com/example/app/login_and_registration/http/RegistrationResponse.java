package com.example.app.login_and_registration.http;

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
public class RegistrationResponse {
    @JsonProperty("errors")
    private List<String> errors;
    @JsonProperty("isError")
    private boolean isError;
}
