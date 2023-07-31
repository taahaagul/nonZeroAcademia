package com.taahaagul.security.controllers;

import com.taahaagul.security.entities.NonDaily;
import com.taahaagul.security.requests.CommentCreateRequest;
import com.taahaagul.security.requests.PostCommentCreateRequest;
import com.taahaagul.security.responses.*;
import com.taahaagul.security.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/BATG/nonzero")
@RequiredArgsConstructor
public class nonZeroController {

    private final UserService userService;
    private final CapsulService capsulService;
    private final SectionService sectionService;
    private final VideoService videoService;
    private final VoteService voteService;
    private final NonDaysService nonDaysService;
    private final NonDailyService nonDailyService;
    private final TokenService tokenService;
    private final CommentService commentService;
    private final PostService postService;
    private final PostCommentService postCommentService;
    private final PostLikeService postLikeService;

    @GetMapping("/user")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getAllUsers());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserResponse> getOneUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getOneUser(userId));
    }

    @GetMapping("/nonTopUser")
    public ResponseEntity<List<NonTopUserResponse>> nonTopUser() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.nonTopUser());
    }

    @GetMapping("/capsul")
    public ResponseEntity<List<CapsulResponse>> getAllCapsul() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(capsulService.getAllCapsul());
    }

    @GetMapping("/capsul/{capsulId}")
    public ResponseEntity<CapsulResponse> getOneCapsul(@PathVariable Integer capsulId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(capsulService.getOneCapsul(capsulId));
    }

    @GetMapping("/section/{capsulId}")
    public ResponseEntity<List<SectionResponse>> getAllSection(@PathVariable Integer capsulId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(sectionService.getAllSection(capsulId));
    }

    @GetMapping("/video/{id}")
    public ResponseEntity<VideoResponse> getOneVideo(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(videoService.getOneVideo(id));
    }

    @GetMapping("/video/{capsulId}/{sectionId}")
    public ResponseEntity<List<VideoResponse>> getSectionVideos(
            @PathVariable Integer capsulId,
            @PathVariable Integer sectionId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(videoService.getSectionVideos(capsulId, sectionId));
    }

    @PostMapping("/vote/{videoId}")
    public ResponseEntity<String> createVote(@PathVariable Integer videoId) {
        voteService.createVote(videoId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Vote added");
    }

    @PutMapping("/status/{videoId}")
    public ResponseEntity<String> changeStatus(@PathVariable Integer videoId) {
        videoService.changeStatus(videoId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Status zero");
    }

    @GetMapping("/nonNews")
    public ResponseEntity<List<String>> getNonNews() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(voteService.getNonNews());
    }

    @GetMapping("/nonDays/{userId}")
    public ResponseEntity<List<LocalDate>> getNonDays(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(nonDaysService.getNonDays(userId));
    }

    @GetMapping("/nonDaily")
    public ResponseEntity<List<NonDaily>> getNonDaily() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(nonDailyService.getDaily());
    }

    @GetMapping("/online")
    public ResponseEntity<Integer> getOnline() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(tokenService.getOnline());
    }

    @PostMapping("/comment")
    public ResponseEntity<String> createOneComment(@RequestBody CommentCreateRequest request) {
        commentService.createOneComment(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Comment created successfully");
    }

    @GetMapping("/comment/{videoId}")
    public ResponseEntity<List<CommentResponse>> getCommentVideo(@PathVariable Integer videoId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.getAllComment(videoId));
    }

    @PostMapping("/post")
    public ResponseEntity<String> createOnePost(
            @RequestParam(value = "text", required = false) String text,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        postService.createOnePost(Optional.ofNullable(text), Optional.ofNullable(file));
        return ResponseEntity.status(HttpStatus.OK)
                .body("Post created successfully");
    }

    @GetMapping("/post")
    public ResponseEntity<List<PostResponse>> getAllPost(@RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getAllPost(page));
    }

    @GetMapping("/post/{userId}")
    public ResponseEntity<List<PostResponse>> getUserPost(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getUserPost(userId));
    }

    @GetMapping("/post/trend")
    public ResponseEntity<List<PostResponse>> getTrendPosts() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getTrendPost());
    }

    @GetMapping("/{userId}/following/posts")
    public ResponseEntity<Page<PostResponse>> getFallowingPosts
            (@PathVariable Long userId,
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "50") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate"));
        Page<PostResponse> followingPosts = userService.getFollowingPosts(userId, pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(followingPosts);
    }

    @PostMapping("/post-comment")
    public ResponseEntity<String> createOnePostComment(
            @RequestBody PostCommentCreateRequest request) {
        postCommentService.createOnePostComment(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body("PostComment created successfully");
    }

    @DeleteMapping("/post-comment/{commentId}")
    public ResponseEntity<String> deletePostComment(@PathVariable Long commentId) {
        postCommentService.deletePostComment(commentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("PostComment deleted successfully");
    }

    @PostMapping("/post-like/{postId}")
    public ResponseEntity<String> createOnePostLike(@PathVariable Long postId) {
        postLikeService.createOnePostLike(postId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("PostLike created successfull");
    }

    @DeleteMapping("/post-like/{postId}")
    public ResponseEntity<String> deletePostLike(@PathVariable Long postId) {
        postLikeService.deletePostLike(postId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("PostLike deleted successfully");
    }

    @PostMapping("/{followerId}/follow/{followedId}")
    public ResponseEntity<String> followUser(
            @PathVariable Long followerId,
            @PathVariable Long followedId
    ) {
        userService.followUser(followerId, followedId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Following successfull");
    }

    @PostMapping("/{followerId}/unfollow/{followedId}")
    public ResponseEntity<String> unfollowUser(
            @PathVariable Long followerId,
            @PathVariable Long followedId
    ) {
        userService.unfollowUser(followerId, followedId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Unfollowing successfull");
    }

    @GetMapping("/followers/{userId}")
    public ResponseEntity<Set<UserResponse>> getFollowers(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getFollowers(userId));
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<Set<UserResponse>> getFollowing(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getFollowing(userId));
    }
}



















