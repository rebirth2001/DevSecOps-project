package com.quizly.users.services;

import com.quizly.users.models.Follow;
import com.quizly.users.models.User;
import com.quizly.users.repositories.FollowRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class FollowService {
    private final FollowRepository followRepository;

    public FollowService(FollowRepository followRepository){this.followRepository = followRepository;}

    public Optional<Follow> createFollow(User follower, User followed){
        var follow = Follow.builder()
                .follower(follower)
                .followed(followed)
                .build();

        try {
            this.followRepository.save(follow);
            return Optional.of(follow);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    public void deleteFollow(Follow follow){
        this.followRepository.delete(follow);
    }

    public List<Follow> findByFollowed(User followed){return this.followRepository.findByFollowed(followed);}

    public List<Follow> findByFollower(User follower){return this.followRepository.findByFollower(follower);}

    public Optional<Follow> findByFollowerAndFollowed(User follower,User followed){
        var result = this.followRepository.findByFollowerAndFollowed(follower,followed);
        if(result.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }

    public boolean doesUserFollow(User user,User followed){
        return this.followRepository.findByFollowed(followed).stream().map(follow -> {
            return follow.getFollower().getUsername().equals(user.getUsername());
        }).reduce(false,Boolean::logicalOr);
    }

}
