package com.quizly.users.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.quizly.users.models.Follow;

import java.util.List;

public class FollowedDto {
    @JsonProperty("followedUsers")
    private List<String> followedUsers;

    public FollowedDto(List<Follow> follows){
        this.followedUsers = follows.stream().map(follow -> follow.getFollowed().getUsername()).toList();
    }
}
