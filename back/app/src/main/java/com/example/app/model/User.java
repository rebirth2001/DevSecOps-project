package com.example.app.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "username",nullable = false)
    private String username;
    @Column(name = "email",nullable = false,unique = true)
    private String email;
    @Column(name = "password_hash",nullable = false)
    private String passwordHash;
    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;
}