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
    @Size(min=4, message = "username must be at least 4 characters")
    private String username;
    @Email(message = "this is not a valid mail")
    private String email;
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String confirmPassword;
}