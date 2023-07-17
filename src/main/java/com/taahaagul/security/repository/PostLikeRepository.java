package com.taahaagul.security.repository;

import com.taahaagul.security.entities.Post;
import com.taahaagul.security.entities.PostLike;
import com.taahaagul.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    List<PostLike> findByPostId(Long postId);

    Optional<Object> findByUserAndPost(User user, Post post);
}
