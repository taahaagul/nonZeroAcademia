package com.taahaagul.security.controllers;

import com.taahaagul.security.requests.VideoRequest;
import com.taahaagul.security.responses.VideoResponse;
import com.taahaagul.security.services.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/BATG/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping("/{id}")
    public ResponseEntity<VideoResponse> getOneVideo(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(videoService.getOneVideo(id));
    }

    @GetMapping("/{capsulName}/{sectionSequence}")
    public ResponseEntity<List<VideoResponse>> getSectionVideos(
            @PathVariable String capsulName,
            @PathVariable Integer sectionSequence) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(videoService.getSectionVideos(capsulName, sectionSequence));
    }
    @PostMapping
    public ResponseEntity<String> createOneVideo(@RequestBody VideoRequest request) {
        videoService.createOneVideo(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Video added");
    }
}
