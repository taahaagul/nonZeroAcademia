package com.taahaagul.security.requests;

import lombok.Data;

@Data
public class PostCommentPutRequest {
    private Long commentId;
    private String text;
}
