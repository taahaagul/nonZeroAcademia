package com.taahaagul.security.responses;

import com.taahaagul.security.entities.Post;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PostResponse {
    private Long postId;
    private String text;
    private LocalDate createDate;
    private Long userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String thumbnailUrl;

    public PostResponse(Post entity) {
        this.postId = entity.getId();
        this.text = entity.getText();
        this.createDate = entity.getCreateDate();
        this.userId = entity.getUser().getId();
        this.firstName = entity.getUser().getFirstName();
        this.lastName = entity.getUser().getLastName();
        this.userName = entity.getUser().getUserName();
        this.thumbnailUrl = entity.getUser().getThumbnailUrl();
    }
}
