package com.taahaagul.security.repository;

import com.taahaagul.security.entities.Capsul;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CapsulRepository extends JpaRepository<Capsul, Integer> {
    Optional<Capsul> findByName(String name);
}
