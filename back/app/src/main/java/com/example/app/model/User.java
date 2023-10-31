package com.example.app.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "firstname",nullable = false)
    private String firstname;
    @Column(name = "lastname",nullable = false)
    private String lastname;
    @Column(name = "email",nullable = false,unique = true)
    private String email;   //I will add username in future
    @Column(name = "password_hash",nullable = false)
    private String passwordHash;
    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;
}