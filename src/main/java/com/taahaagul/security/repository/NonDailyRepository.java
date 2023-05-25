package com.taahaagul.security.repository;

import com.taahaagul.security.entities.NonDaily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NonDailyRepository extends JpaRepository<NonDaily, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM non_daily ORDER BY RAND() LIMIT 1")
    NonDaily findRandomNonDaily();
}
