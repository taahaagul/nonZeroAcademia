package com.taahaagul.security.responses;

import com.taahaagul.security.entities.User;
import lombok.Data;

import java.util.Date;

@Data
public class UserResponse {
    Long id;
    String userName;
    String email;
    Date memberSince;
    String aboutMe;
    String gitHub;

    public UserResponse(User entity) {
        this.id = entity.getId();
        this.userName = entity.getUserName();
        this.email = entity.getEmail();
        this.memberSince = entity.getMemberSince();
        this.aboutMe = entity.getAboutMe();
        this.gitHub = entity.getGitHub();
    }
}
