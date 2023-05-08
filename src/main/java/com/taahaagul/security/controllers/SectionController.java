package com.taahaagul.security.controllers;

import com.taahaagul.security.requests.SectionRequest;
import com.taahaagul.security.responses.SectionResponse;
import com.taahaagul.security.services.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/BATG/section")
@RequiredArgsConstructor
public class SectionController {

    private final SectionService sectionService;

    @GetMapping("/{name}")
    public ResponseEntity<List<SectionResponse>> getAllSection(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(sectionService.getAllSection(name));
    }

    @PostMapping
    public ResponseEntity<String> createOneSection(@RequestBody SectionRequest request) {
        sectionService.createOneSection(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Section is created successfully!");
    }
}
