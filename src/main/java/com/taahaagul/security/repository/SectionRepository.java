package com.taahaagul.security.repository;

import com.taahaagul.security.entities.Capsul;
import com.taahaagul.security.entities.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section, Integer> {
    List<Section> findAllByCapsul(Capsul capsul);
    Optional<Section> findBySectionSequence(Integer seq);
}
