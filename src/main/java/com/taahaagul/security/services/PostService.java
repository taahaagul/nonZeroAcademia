package com.taahaagul.security.services;

import com.taahaagul.security.entities.Post;
import com.taahaagul.security.entities.User;
import com.taahaagul.security.repository.PostRepository;
import com.taahaagul.security.requests.PostCreateRequest;
import com.taahaagul.security.responses.PostCommentResponse;
import com.taahaagul.security.responses.PostLikeResponse;
import com.taahaagul.security.responses.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final AuthenticationService authenticationService;
    private final PostRepository postRepository;
    private final PostCommentService postCommentService;
    private final PostLikeService postLikeService;

    public void createOnePost(PostCreateRequest request) {
        User user = authenticationService.getCurrentUser();

        Post post = Post.builder()
                .text(request.getText())
                .createDate(LocalDate.now())
                .user(user)
                .build();

        postRepository.save(post);
    }

    public List<PostResponse> getAllPost() {
        List<Post> list = postRepository.findAll();
        return list.stream()
                .map(post -> {
                    List<PostCommentResponse> postComments = postCommentService.getAllPostComment(post.getId());
                    List<PostLikeResponse> postLikes = postLikeService.getAllPostLike(post.getId());
                    return new PostResponse(post, postComments, postLikes);
                }).collect(Collectors.toList());
    }

    public List<PostResponse> getUserPost(Long userId) {
        List<Post> list = postRepository.findByUserId(userId);
        return list.stream()
                .map(post -> {
                    List<PostCommentResponse> postComments = postCommentService.getAllPostComment(post.getId());
                    List<PostLikeResponse> postLikes = postLikeService.getAllPostLike(post.getId());
                    return new PostResponse(post, postComments, postLikes);
                }).collect(Collectors.toList());
    }
}
