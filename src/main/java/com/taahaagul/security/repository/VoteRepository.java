package com.taahaagul.security.repository;

import com.taahaagul.security.entities.User;
import com.taahaagul.security.entities.Video;
import com.taahaagul.security.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByVideoAndUser(Video video, User user);
}
