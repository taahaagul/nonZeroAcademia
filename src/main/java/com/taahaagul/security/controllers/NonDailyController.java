package com.taahaagul.security.controllers;

import com.taahaagul.security.requests.NonDailyRequest;
import com.taahaagul.security.services.NonDailyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/BATG/nondaily")
@RequiredArgsConstructor
public class NonDailyController {

    private final NonDailyService nonDailyService;
    @PostMapping
    public ResponseEntity<String> createOneDaily(@RequestBody NonDailyRequest request) {
        nonDailyService.createDaily(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Daily added successfully");
    }
}
