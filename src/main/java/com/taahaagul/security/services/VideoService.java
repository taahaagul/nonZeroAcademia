package com.taahaagul.security.services;

import com.taahaagul.security.entities.Capsul;
import com.taahaagul.security.entities.Section;
import com.taahaagul.security.entities.Video;
import com.taahaagul.security.exceptions.UserNotFoundException;
import com.taahaagul.security.repository.CapsulRepository;
import com.taahaagul.security.repository.SectionRepository;
import com.taahaagul.security.repository.VideoRepository;
import com.taahaagul.security.requests.VideoRequest;
import com.taahaagul.security.responses.VideoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoRepository videoRepository;
    private final SectionRepository sectionRepository;
    private final CapsulRepository capsulRepository;
    public void createOneVideo(VideoRequest request) {
        Capsul capsul = capsulRepository.findByName(request.getCapsulName())
                .orElseThrow(() -> new UserNotFoundException("Capsul is not founded"));

        Section section = sectionRepository.findBySectionSequence(request.getSectionSequence())
                .orElseThrow(() -> new UserNotFoundException("Section is not found"));

        Video video = Video.builder()
                .videoSequence(request.getVideoSequence())
                .videoUrl(request.getVideoUrl())
                .title(request.getTitle())
                .section(section)
                .capsul(capsul)
                .build();

        videoRepository.save(video);
    }

    public List<VideoResponse> getSectionVideo(String capsulName, Integer sectionSequence) {
        Capsul capsul = capsulRepository.findByName(capsulName)
                .orElseThrow(() -> new UserNotFoundException("Capsul is not founded"));

        Section section = sectionRepository.findBySectionSequence(sectionSequence)
                .orElseThrow(() -> new UserNotFoundException("Section is not found"));

        List<Video> list = videoRepository.findAllByCapsulAndSection(capsul, section);
        return list.stream()
                .map(video -> new VideoResponse(video))
                .collect(Collectors.toList());
    }
}
