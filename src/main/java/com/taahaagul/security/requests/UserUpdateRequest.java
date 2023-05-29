package com.taahaagul.security.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String aboutMe;
    private String gitHub;
    private String education;
    private String city;
    private String linkedin;
}
