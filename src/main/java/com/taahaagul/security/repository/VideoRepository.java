package com.taahaagul.security.repository;

import com.taahaagul.security.entities.Capsul;
import com.taahaagul.security.entities.Section;
import com.taahaagul.security.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Integer> {
    List<Video> findAllByCapsulAndSection(Capsul capsul, Section section);
}
