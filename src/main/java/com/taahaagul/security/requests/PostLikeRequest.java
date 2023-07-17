package com.taahaagul.security.requests;

import lombok.Data;

@Data
public class PostLikeRequest {
    private Long userId;
    private Long postId;
}
