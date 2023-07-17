package com.taahaagul.security.responses;

import com.taahaagul.security.entities.Post;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

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
    private List<PostCommentResponse> postComments;
    private List<PostLikeResponse> postLikes;

    public PostResponse(Post entity, List<PostCommentResponse> postComments, List<PostLikeResponse> postLikes) {
        this.postId = entity.getId();
        this.text = entity.getText();
        this.createDate = entity.getCreateDate();
        this.userId = entity.getUser().getId();
        this.firstName = entity.getUser().getFirstName();
        this.lastName = entity.getUser().getLastName();
        this.userName = entity.getUser().getUserName();
        this.thumbnailUrl = entity.getUser().getThumbnailUrl();
        this.postComments = postComments;
        this.postLikes = postLikes;
    }
}
