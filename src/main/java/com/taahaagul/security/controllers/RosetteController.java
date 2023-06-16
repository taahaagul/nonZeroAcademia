package com.taahaagul.security.controllers;

import com.taahaagul.security.requests.RosetteRequest;
import com.taahaagul.security.requests.VideoRequest;
import com.taahaagul.security.services.RosetteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/BATG/rosette")
@RequiredArgsConstructor
public class RosetteController {

    private final RosetteService rosetteService;
    @PostMapping
    public ResponseEntity<String> createOneRosette(@RequestBody RosetteRequest request) {
        rosetteService.createOneRosette(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Rosette added");
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllRosette() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(rosetteService.getAllRosette());
    }
}
