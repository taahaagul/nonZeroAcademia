package com.taahaagul.security.services;

import com.taahaagul.security.entities.Post;
import com.taahaagul.security.entities.User;
import com.taahaagul.security.exceptions.UserNotFoundException;
import com.taahaagul.security.repository.PostRepository;
import com.taahaagul.security.responses.PostCommentResponse;
import com.taahaagul.security.responses.PostLikeResponse;
import com.taahaagul.security.responses.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final AuthenticationService authenticationService;
    private final PostRepository postRepository;
    private final PostCommentService postCommentService;
    private final PostLikeService postLikeService;
    private final S3Service s3Service;

    public void createOnePost(Optional<String> text, Optional<MultipartFile> file) {
        if(!text.isPresent() && !file.isPresent()) {
            throw new UserNotFoundException("At least one of text or file must be provided.");
        }

        User user = authenticationService.getCurrentUser();
        String url = null;

        if(file.isPresent()) {
            url = s3Service.upload(file.get());
        }
        Post.PostBuilder postBuilder = Post.builder()
                .createDate(LocalDateTime.now())
                .user(user);

        text.ifPresent(postBuilder::text);
        if(url != null)
            postBuilder.fileUrl(url);

        Post post = postBuilder.build();
        postRepository.save(post);
    }

    public List<PostResponse> getAllPost(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 50, Sort.by("createDate").descending());
        Page<Post> page = postRepository.findAll(pageable);

        List<Post> list = page.getContent();
        return list.stream()
                .map(post -> {
                    List<PostCommentResponse> postComments = postCommentService.getAllPostComment(post.getId());
                    List<PostLikeResponse> postLikes = postLikeService.getAllPostLike(post.getId());
                    return new PostResponse(post, postComments, postLikes);
                }).collect(Collectors.toList());
    }

    public List<PostResponse> getUserPost(Long userId) {
        List<Post> list = postRepository.findByUserIdOrderByCreateDateDesc(userId);
        return list.stream()
                .map(post -> {
                    List<PostCommentResponse> postComments = postCommentService.getAllPostComment(post.getId());
                    List<PostLikeResponse> postLikes = postLikeService.getAllPostLike(post.getId());
                    return new PostResponse(post, postComments, postLikes);
                }).collect(Collectors.toList());
    }

    public List<PostResponse> getTrendPost() {
        Pageable topFifty = PageRequest.of(0,50);
        List<Post> list = postRepository.findTrendPosts(topFifty);

        return list.stream()
                .map(post -> {
                    List<PostCommentResponse> postComments = postCommentService.getAllPostComment(post.getId());
                    List<PostLikeResponse> postLikes = postLikeService.getAllPostLike(post.getId());
                    return new PostResponse(post, postComments, postLikes);
                }).collect(Collectors.toList());
    }
}
