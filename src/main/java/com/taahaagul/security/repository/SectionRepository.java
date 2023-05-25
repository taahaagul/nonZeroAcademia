package com.taahaagul.security.repository;

import com.taahaagul.security.entities.Capsul;
import com.taahaagul.security.entities.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Integer> {
    List<Section> findAllByCapsul(Capsul capsul);
}
