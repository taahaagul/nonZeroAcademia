package com.taahaagul.security.services;

import com.taahaagul.security.entities.Comment;
import com.taahaagul.security.entities.Post;
import com.taahaagul.security.entities.PostComment;
import com.taahaagul.security.entities.User;
import com.taahaagul.security.exceptions.UserNotFoundException;
import com.taahaagul.security.repository.PostCommentRepository;
import com.taahaagul.security.repository.PostRepository;
import com.taahaagul.security.requests.PostCommentCreateRequest;
import com.taahaagul.security.requests.PostCommentPutRequest;
import com.taahaagul.security.responses.PostCommentResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostCommentService {

    private final AuthenticationService authenticationService;
    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;

    public void createOnePostComment(PostCommentCreateRequest request) {
        User user = authenticationService.getCurrentUser();

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new UserNotFoundException("Post is not founded"));

        PostComment postComment = PostComment.builder()
                .text(request.getText())
                .createDate(LocalDateTime.now())
                .user(user)
                .post(post)
                .build();

        postCommentRepository.save(postComment);
    }

    public List<PostCommentResponse> getAllPostComment(Long postId) {
        List<PostComment> list = postCommentRepository.findByPostId(postId);

        return list.stream()
                .map(postComment -> new PostCommentResponse(postComment))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletePostComment(Long commentId) {
        PostComment comment = postCommentRepository.findById(commentId)
                .orElseThrow(() -> new UserNotFoundException("postComment is not founded"));

        User currentUser = authenticationService.getCurrentUser();

        if(!comment.getUser().getId().equals(currentUser.getId()))
            throw new UserNotFoundException("User is not authorized to delete this comment");

        postCommentRepository.delete(comment);
    }

    @Transactional
    public void updatePostComment(PostCommentPutRequest updateComment) {
        PostComment comment = postCommentRepository.findById(updateComment.getCommentId())
                .orElseThrow(() -> new UserNotFoundException("PostComment is not founded"));

        User currentUser = authenticationService.getCurrentUser();

        if (!comment.getUser().getId().equals(currentUser.getId()))
            throw new UserNotFoundException("User is not authorized to update this comment");

        comment.setText(updateComment.getText());
        postCommentRepository.save(comment);
    }
}
