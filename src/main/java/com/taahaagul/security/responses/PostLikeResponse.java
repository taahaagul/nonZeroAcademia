package com.taahaagul.security.responses;

import com.taahaagul.security.entities.PostLike;
import lombok.Data;

@Data
public class PostLikeResponse {
    private Long postLikeId;
    private Long userId;
    private Long postId;
    private String firstName;
    private String lastName;
    private String userName;
    private String thumbnailUrl;

    public PostLikeResponse(PostLike entity) {
        this.postLikeId = entity.getId();
        this.userId = entity.getUser().getId();
        this.postId = entity.getPost().getId();
        this.firstName = entity.getUser().getFirstName();
        this.lastName = entity.getUser().getLastName();
        this.userName = entity.getUser().getUserName();
        this.thumbnailUrl = entity.getUser().getThumbnailUrl();
    }
}
