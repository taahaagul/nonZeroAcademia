package com.taahaagul.security.repository;

import com.taahaagul.security.entities.Rosette;
import com.taahaagul.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RosetteRepository extends JpaRepository<Rosette, Integer> {
    List<Rosette> findByUser(User user);
}
