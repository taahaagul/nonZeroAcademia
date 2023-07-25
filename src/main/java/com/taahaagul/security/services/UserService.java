package com.taahaagul.security.services;

import com.taahaagul.security.entities.Post;
import com.taahaagul.security.entities.Role;
import com.taahaagul.security.entities.User;
import com.taahaagul.security.exceptions.UserNotFoundException;
import com.taahaagul.security.repository.UserRepository;
import com.taahaagul.security.requests.UserChangePaswRequest;
import com.taahaagul.security.requests.UserUpdateRequest;
import com.taahaagul.security.responses.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;
    private final PostCommentService postCommentService;
    private final PostLikeService postLikeService;


    public List<UserResponse> getAllUsers() {
       List<User> list = userRepository.findAllByOrderByNonRankDesc();
       return list.stream()
               .map(user -> new UserResponse(user))
               .collect(Collectors.toList());
    }

    public UserResponse getOneUser(Long userId) {
       User user = userRepository.findById(userId)
               .orElseThrow(() -> new UserNotFoundException("User not found!"));
       return new UserResponse(user);
    }

    public UserResponse getAuthenticateUser() {
        User currentUser = authenticationService.getCurrentUser();
        return new UserResponse(currentUser);
    }

    public UserResponse updateAuthenticateUser(UserUpdateRequest request) {
       User currentUser = authenticationService.getCurrentUser();
       currentUser.setFirstName(request.getFirstName());
       currentUser.setLastName(request.getLastName());
       currentUser.setAboutMe(request.getAboutMe());
       currentUser.setGitHub(request.getGitHub());
       currentUser.setEducation(request.getEducation());
       currentUser.setCity(request.getCity());
       currentUser.setLinkedin(request.getLinkedin());
       userRepository.save(currentUser);
       return new UserResponse(currentUser);
    }

    public void changePassword(UserChangePaswRequest request) {
        User user = authenticationService.getCurrentUser();
        if(passwordEncoder.matches(request.getOldPasw(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(request.getNewPasw()));
            userRepository.save(user);
        } else
            throw new UserNotFoundException("Password unmatched!");
    }

    public void zeroToOne() {
        User user = authenticationService.getCurrentUser();
        user.setRole(Role.ONE);
        userRepository.save(user);
    }

    public void incrementRank(User user) {
        user.incrementNonRank();
        userRepository.save(user);
    }

    public void decerementRank(User user) {
        user.decrementNonRank();
        userRepository.save(user);
    }

    public List<NonTopUserResponse> nonTopUser() {
        Pageable topTen = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "nonRank"));
        Page<User> nonTopUsers = userRepository.findAllByOrderByNonRankDesc(topTen);
        return nonTopUsers.getContent().stream()
                .map(NonTopUserResponse::new)
                .collect(Collectors.toList());
    }

    public NonRankPositionResponse getUserNonRankPosition(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not founded"));

        Integer position = userRepository.countUsersWithEqualOrHigherNonRank(user.getNonRank());
        Integer nonRank = user.getNonRank();

        return new NonRankPositionResponse(nonRank, position);
    }

    @Transactional
    public void followUser(Long followerId, Long followedId) {
        if(followerId.equals(followedId)) {
            throw new UserNotFoundException("A user cannot follow themselves");
        }

        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new UserNotFoundException("Follower not found"));

        User followed = userRepository.findById(followedId)
                .orElseThrow(() -> new UserNotFoundException("Followed not found"));

        if(follower.getFollowing().contains(followed)) {
            throw new UserNotFoundException("This user is already followed");
        }

        follower.addFollowing(followed);
        followed.incrementNonRank();

        userRepository.save(follower);
        userRepository.save(followed);
    }

    @Transactional
    public void unfollowUser(Long followerId, Long followedId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new UserNotFoundException("Follower not found"));

        User followed = userRepository.findById(followedId)
                .orElseThrow(() -> new UserNotFoundException("Followed not found"));

        if(!follower.getFollowing().contains(followed)) {
            throw new UserNotFoundException("This user is not followed");
        }

        follower.removeFollowing(followed);
        followed.decrementNonRank();

        userRepository.save(follower);
        userRepository.save(followed);
    }

    public Set<UserResponse> getFollowers(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not founded"));

        return user.getFollowers().stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toSet());
    }

    public Set<UserResponse> getFollowing(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not founded"));

        return user.getFollowing().stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toSet());
    }

    private UserResponse convertToUserResponse(User user) {
        return new UserResponse(user);
    }

    @Transactional()
    public Page<PostResponse> getFollowingPosts(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<PostResponse> postResponses = user.getFollowing().stream()
                .flatMap(followingUser -> followingUser.getPosts().stream())
                .sorted(Comparator.comparing(Post::getCreateDate).reversed())
                .map(post -> {
                    List<PostCommentResponse> postCommentResponses = postCommentService.getAllPostComment(post.getId());
                    List<PostLikeResponse> postLikeResponses = postLikeService.getAllPostLike(post.getId());
                    return new PostResponse(post, postCommentResponses, postLikeResponses);
                })
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), postResponses.size());

        return new PageImpl<>(postResponses.subList(start, end), pageable, postResponses.size());
    }
}
