package com.taahaagul.security.controllers;

import com.taahaagul.security.requests.CapsulRequest;
import com.taahaagul.security.services.CapsulService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/BATG/capsul")
@RequiredArgsConstructor
public class CapsulController {

    private final CapsulService capsulService;

    @PostMapping
    public ResponseEntity<String> createOneCapsul(@RequestBody CapsulRequest request) {
        capsulService.createOneCapsul(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Capsul is created succesfully!");
    }
}
