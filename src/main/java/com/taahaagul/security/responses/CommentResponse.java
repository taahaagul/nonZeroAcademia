package com.taahaagul.security.responses;

import com.taahaagul.security.entities.Comment;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentResponse {
    private Long commentId;
    private String text;
    private String firstName;
    private String lastName;
    private String thumbnailUrl;
    private LocalDate createDate;

    public CommentResponse(Comment entity) {
        this.commentId = entity.getId();
        this.text = entity.getText();
        this.firstName = entity.getUser().getFirstName();
        this.lastName = entity.getUser().getLastName();
        this.thumbnailUrl = entity.getUser().getThumbnailUrl();
        this.createDate = entity.getCreateDate();
    }
}
