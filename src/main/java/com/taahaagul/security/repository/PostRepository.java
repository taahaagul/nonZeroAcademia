package com.taahaagul.security.repository;

import com.taahaagul.security.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserIdOrderByCreateDateDesc(Long userId);
    Page<Post> findAll(Pageable pageable);
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.likes ORDER BY SIZE(p.likes) DESC")
    List<Post>  findTrendPosts(Pageable pageable);
}
