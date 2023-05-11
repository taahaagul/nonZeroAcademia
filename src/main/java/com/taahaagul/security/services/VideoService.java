package com.taahaagul.security.services;

import com.taahaagul.security.entities.*;
import com.taahaagul.security.exceptions.UserNotFoundException;
import com.taahaagul.security.repository.CapsulRepository;
import com.taahaagul.security.repository.SectionRepository;
import com.taahaagul.security.repository.VideoRepository;
import com.taahaagul.security.repository.VoteRepository;
import com.taahaagul.security.requests.VideoRequest;
import com.taahaagul.security.responses.VideoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoRepository videoRepository;
    private final SectionRepository sectionRepository;
    private final CapsulRepository capsulRepository;
    private final VoteRepository voteRepository;
    private final AuthenticationService authenticationService;
    public void createOneVideo(VideoRequest request) {
        Capsul capsul = capsulRepository.findById(request.getCapsulId())
                .orElseThrow(() -> new UserNotFoundException("Capsul is not founded"));

        Section section = sectionRepository.findById(request.getSectionId())
                .orElseThrow(() -> new UserNotFoundException("Section is not found"));

        Video video = Video.builder()
                .videoSequence(request.getVideoSequence())
                .videoUrl(request.getVideoUrl())
                .title(request.getTitle())
                .section(section)
                .capsul(capsul)
                .status(false)
                .build();

        videoRepository.save(video);
    }

    public List<VideoResponse> getSectionVideos(Integer capsulId, Integer sectionId) {
        Capsul capsul = capsulRepository.findById(capsulId)
                .orElseThrow(() -> new UserNotFoundException("Capsul is not founded"));

        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new UserNotFoundException("Section is not found"));

        User user = authenticationService.getCurrentUser();

        List<Video> list = videoRepository.findAllByCapsulAndSection(capsul, section);
        return list.stream()
                .map(video -> {
                    Optional<Vote> vote = voteRepository.findByVideoAndUser(video, user);
                    if (vote.isPresent())
                        video.setStatus(true);
                    return new VideoResponse(video);})
                .collect(Collectors.toList());
    }

    public VideoResponse getOneVideo(Integer id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Video is not found"));

        return new VideoResponse(video);
    }

    public void changeStatus(Integer videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new UserNotFoundException("Video is not found!"));
        video.setStatus(false);
        videoRepository.save(video);
    }
}
