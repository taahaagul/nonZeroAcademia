package com.taahaagul.security.services;

import com.taahaagul.security.entities.Capsul;
import com.taahaagul.security.entities.Section;
import com.taahaagul.security.exceptions.UserNotFoundException;
import com.taahaagul.security.repository.CapsulRepository;
import com.taahaagul.security.repository.SectionRepository;
import com.taahaagul.security.requests.SectionRequest;
import com.taahaagul.security.responses.SectionResponse;
import com.taahaagul.security.responses.VideoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;
    private final CapsulRepository capsulRepository;
    private final VideoService videoService;
    public void createOneSection(SectionRequest request) {
        Capsul capsul = capsulRepository.findById(request.getCapsulId())
                .orElseThrow(() -> new UserNotFoundException("Capsul is not found"));

        Section section = Section.builder()
                .title(request.getTitle())
                .sectionSequence(request.getSectionSequence())
                .capsul(capsul)
                .build();

        sectionRepository.save(section);
    }

    public List<SectionResponse> getAllSection(Integer capsulId) {
        Capsul capsul = capsulRepository.findById(capsulId)
                .orElseThrow(() -> new UserNotFoundException("Capsul is not found"));

        List<Section> list = sectionRepository.findAllByCapsul(capsul);
        return list.stream().map(s -> {
            List<VideoResponse> videos = videoService.getSectionVideos(capsulId, s.getId());
            return new SectionResponse(s, videos);}).collect(Collectors.toList());
    }
}
