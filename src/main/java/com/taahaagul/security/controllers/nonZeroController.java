package com.taahaagul.security.controllers;

import com.taahaagul.security.entities.NonNews;
import com.taahaagul.security.responses.*;
import com.taahaagul.security.services.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.Past;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/BATG/nonzero")
@RequiredArgsConstructor
public class nonZeroController {

    private final UserService userService;
    private final CapsulService capsulService;
    private final SectionService sectionService;
    private final VideoService videoService;
    private final VoteService voteService;
    private final NonDaysService nonDaysService;

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

    @GetMapping("/nonTopUser")
    public ResponseEntity<List<NonTopUserResponse>> nonTopUser() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.nonTopUser());
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

    @PostMapping("/vote/{videoId}")
    public ResponseEntity<String> createVote(@PathVariable Integer videoId) {
        voteService.createVote(videoId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Vote added");
    }

    @PutMapping("/status/{videoId}")
    public ResponseEntity<String> changeStatus(@PathVariable Integer videoId) {
        videoService.changeStatus(videoId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Status zero");
    }

    @GetMapping("/nonNews")
    public ResponseEntity<List<String>> getNonNews() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(voteService.getNonNews());
    }

    @GetMapping("/nonDays")
    public ResponseEntity<List<LocalDate>> getNonDays() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(nonDaysService.getNonDays());
    }
}
