package com.taahaagul.security.repository;

import com.taahaagul.security.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByOrderByNonRankDesc();
    Optional<User> findByEmail(String email);
    Optional<User> findByUserName(String username);
    Page<User> findAllByOrderByNonRankDesc(Pageable pageable);
    @Query("SELECT COUNT(u) FROM User u WHERE u.nonRank >= :nonRank")
    int countUsersWithEqualOrHigherNonRank(@Param("nonRank") int nonRank);


}
