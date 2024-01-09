package com.quizly.users.repositories;

import com.quizly.users.models.Follow;
import com.quizly.users.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow,Long> {
    List<Follow> findByFollowed(User followed);

    List<Follow> findByFollower(User follower);

    List<Follow> findByFollowerAndFollowed(User follower, User followedId);
}

