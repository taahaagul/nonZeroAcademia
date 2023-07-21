package com.taahaagul.security.responses;

import com.taahaagul.security.entities.PostComment;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PostCommentResponse {
    private Long postCommentId;
    private String text;
    private LocalDateTime createDate;
    private Long userId;
    private Long postId;
    private String firstName;
    private String lastName;
    private String userName;
    private String thumbnailUrl;

    public PostCommentResponse(PostComment entity) {
        this.postCommentId = entity.getId();
        this.text = entity.getText();
        this.createDate = entity.getCreateDate();
        this.userId = entity.getUser().getId();
        this.postId = entity.getPost().getId();
        this.firstName = entity.getUser().getFirstName();
        this.lastName = entity.getUser().getLastName();
        this.userName = entity.getUser().getUserName();
        this.thumbnailUrl = entity.getUser().getThumbnailUrl();
    }
}
