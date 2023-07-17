package com.taahaagul.security.requests;

import lombok.Data;

@Data
public class PostCommentCreateRequest {
    private String text;
    private Long postId;
}
