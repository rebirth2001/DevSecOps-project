package com.example.app.login_and_registration.http;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterRequest {
    @NotBlank
    @Size(min=4)
    private String username;
    @Email
    private String email;
    @Size(min = 8)
    private String password;
    @Size(min = 8)
    private String confirmPassword;
}