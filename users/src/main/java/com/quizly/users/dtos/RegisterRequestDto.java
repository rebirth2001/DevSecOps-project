package com.quizly.users.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@Data
public class RegisterRequestDto {
    @NotBlank(message = "Username must not be empty.")
    @Size(min=4, message = "Username must be at least 4 characters")
    private String username;
    @Email(message = "Please use a valid email")
    private String email;
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String confirmPassword;
}