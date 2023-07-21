package com.taahaagul.security.services;

import com.taahaagul.security.entities.Post;
import com.taahaagul.security.entities.PostLike;
import com.taahaagul.security.entities.User;
import com.taahaagul.security.exceptions.UserNotFoundException;
import com.taahaagul.security.repository.PostLikeRepository;
import com.taahaagul.security.repository.PostRepository;
import com.taahaagul.security.repository.UserRepository;
import com.taahaagul.security.responses.PostLikeResponse;
import jakarta.transaction.Transactional;
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
    private final UserRepository userRepository;
    public void createOnePostLike(Long postId) {
        User user = authenticationService.getCurrentUser();

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new UserNotFoundException("Post is not founded"));

        if(isPostLikeExist(user, post))
            throw new UserNotFoundException("PostLike is already exist");

        PostLike postLike = PostLike.builder()
                .user(user)
                .post(post)
                .build();

       incrementRank(post.getUser());

        postLikeRepository.save(postLike);
    }

    private void incrementRank(User user) {
        user.incrementNonRank();
        userRepository.save(user);
    }
    private void decrementRank(User user) {
        user.decrementNonRank();
        userRepository.save(user);
    }

    private boolean isPostLikeExist(User user, Post post) {
        return postLikeRepository.findByUserAndPost(user, post).isPresent();
    }

    public List<PostLikeResponse> getAllPostLike(Long postId) {
        List<PostLike> list = postLikeRepository.findByPostId(postId);

        return list.stream()
                .map(postLike -> new PostLikeResponse(postLike))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletePostLike(Long postId) {
        User user = authenticationService.getCurrentUser();
        Post post = postRepository.findById(postId)
                        .orElseThrow(() -> new UserNotFoundException("Post is not founded"));

        if(isPostLikeExist(user, post)) {
            decrementRank(post.getUser());
            postLikeRepository.deleteByUserAndPost(user, post);
        } else
            throw new UserNotFoundException("PostLike is already deleted..");
    }
}
