package com.taahaagul.security.controllers;

import com.taahaagul.security.requests.VideoRequest;
import com.taahaagul.security.services.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/BATG/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;
    @PostMapping
    public ResponseEntity<String> createOneVideo(@RequestBody VideoRequest request) {
        videoService.createOneVideo(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Video added");
    }
}
