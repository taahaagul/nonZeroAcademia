package com.taahaagul.security.responses;

import com.taahaagul.security.entities.Role;
import com.taahaagul.security.entities.User;
import lombok.Data;

import java.util.Date;

@Data
public class UserResponse {
    Long id;
    String firstName;
    String lastName;
    String userName;
    String email;
    Date memberSince;
    String aboutMe;
    String education;
    String city;
    String gitHub;
    String linkedin;
    String thumbnail;

    public UserResponse(User entity) {
        this.id = entity.getId();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.userName = entity.getUserName();
        this.email = entity.getEmail();
        this.memberSince = entity.getMemberSince();
        this.aboutMe = entity.getAboutMe();
        this.education = entity.getEducation();
        this.city = entity.getCity();
        this.gitHub = entity.getGitHub();
        this.linkedin = entity.getLinkedin();
        this.thumbnail = entity.getThumbnailUrl();
    }
}
