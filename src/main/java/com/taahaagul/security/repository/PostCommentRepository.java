package com.taahaagul.security.repository;

import com.taahaagul.security.entities.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    List<PostComment> findByPostId(Long postId);
}
