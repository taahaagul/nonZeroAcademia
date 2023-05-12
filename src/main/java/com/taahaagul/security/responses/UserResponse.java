package com.taahaagul.security.responses;

import com.taahaagul.security.entities.Role;
import com.taahaagul.security.entities.User;
import lombok.Data;

import java.util.Date;

@Data
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private Date memberSince;
    private String aboutMe;
    private String education;
    private String city;
    private String gitHub;
    private String linkedin;
    private String thumbnail;
    private Integer nonRank;

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
        this.nonRank = entity.getNonRank();
    }
}
