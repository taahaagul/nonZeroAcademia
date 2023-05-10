package com.taahaagul.security.controllers;

import com.taahaagul.security.responses.CapsulResponse;
import com.taahaagul.security.responses.SectionResponse;
import com.taahaagul.security.responses.UserResponse;
import com.taahaagul.security.responses.VideoResponse;
import com.taahaagul.security.services.CapsulService;
import com.taahaagul.security.services.SectionService;
import com.taahaagul.security.services.UserService;
import com.taahaagul.security.services.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/BATG/nonzero")
@RequiredArgsConstructor
public class nonZeroController {

    private final UserService userService;
    private final CapsulService capsulService;
    private final SectionService sectionService;
    private final VideoService videoService;

    @GetMapping("/user")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getAllUsers());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserResponse> getOneUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getOneUser(userId));
    }

    @GetMapping("/capsul")
    public ResponseEntity<List<CapsulResponse>> getAllCapsul() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(capsulService.getAllCapsul());
    }

    @GetMapping("/section/{capsulId}")
    public ResponseEntity<List<SectionResponse>> getAllSection(@PathVariable Integer capsulId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(sectionService.getAllSection(capsulId));
    }

    @GetMapping("/video/{id}")
    public ResponseEntity<VideoResponse> getOneVideo(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(videoService.getOneVideo(id));
    }

    @GetMapping("/video/{capsulId}/{sectionId}")
    public ResponseEntity<List<VideoResponse>> getSectionVideos(
            @PathVariable Integer capsulId,
            @PathVariable Integer sectionId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(videoService.getSectionVideos(capsulId, sectionId));
    }

    @PutMapping("/video/status/{videoId}")
    public ResponseEntity<String> changeStatus(Integer videoId) {
        videoService.changeStatus(videoId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Status one!");
    }
}
