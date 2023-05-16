package com.taahaagul.security.repository;

import com.taahaagul.security.entities.NonDays;
import com.taahaagul.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface NonDaysRepository extends JpaRepository<NonDays, Long> {
    Optional<NonDays> findByUserAndLoginDate(User user, LocalDate localDate);
    List<NonDays> findByUser(User user);
}
