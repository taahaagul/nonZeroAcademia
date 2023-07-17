package com.taahaagul.security.services;

import com.taahaagul.security.entities.Post;
import com.taahaagul.security.entities.PostLike;
import com.taahaagul.security.entities.User;
import com.taahaagul.security.exceptions.UserNotFoundException;
import com.taahaagul.security.repository.PostLikeRepository;
import com.taahaagul.security.repository.PostRepository;
import com.taahaagul.security.requests.PostLikeRequest;
import com.taahaagul.security.responses.PostLikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final AuthenticationService authenticationService;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    public void createOnePostLike(PostLikeRequest request) {
        User user = authenticationService.getCurrentUser();

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(()-> new UserNotFoundException("Post is not founded"));

        PostLike postLike = PostLike.builder()
                .user(user)
                .post(post)
                .build();

        postLikeRepository.save(postLike);
    }

    public List<PostLikeResponse> getAllPostLike(Long postId) {
        List<PostLike> list = postLikeRepository.findByPostId(postId);

        return list.stream()
                .map(postLike -> new PostLikeResponse(postLike))
                .collect(Collectors.toList());
    }
}
