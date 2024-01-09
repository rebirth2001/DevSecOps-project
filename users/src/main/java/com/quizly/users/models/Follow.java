package com.quizly.users.models;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "follows",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"following_user","followed_user"})
})
public class Follow {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "following_user")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "followed_user")
    private User followed;
}
