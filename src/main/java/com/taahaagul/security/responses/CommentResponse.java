package com.taahaagul.security.responses;

import com.taahaagul.security.entities.Comment;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentResponse {
    private Long commentId;
    private String text;
    private String commentUsername;
    private LocalDate createDate;

    public CommentResponse(Comment entity) {
        this.commentId = entity.getId();
        this.text = entity.getText();
        this.commentUsername = entity.getUser().getUserName();
        this.createDate = entity.getCreateDate();
    }
}
