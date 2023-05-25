package com.taahaagul.security.responses;

import com.taahaagul.security.entities.User;
import lombok.Data;

@Data
public class NonTopUserResponse {
    private Long userId;
    private String userName;
    private Integer nonRank;

    public NonTopUserResponse(User entity) {
        this.userId = entity.getId();
        this.userName = entity.getUserName();
        this.nonRank = entity.getNonRank();
    }
}