package com.taahaagul.security.repository;

import com.taahaagul.security.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserIdOrderByCreateDateDesc(Long userId);

    Page<Post> findAll(Pageable pageable);
}
