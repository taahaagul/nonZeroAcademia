package com.taahaagul.security.controllers;

import com.taahaagul.security.requests.CapsulRequest;
import com.taahaagul.security.responses.CapsulResponse;
import com.taahaagul.security.responses.UserResponse;
import com.taahaagul.security.services.CapsulService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/BATG/capsul")
@RequiredArgsConstructor
public class CapsulController {

    private final CapsulService capsulService;

    @GetMapping
    public ResponseEntity<List<CapsulResponse>> getAllCapsul() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(capsulService.getAllCapsul());
    }

    @PostMapping
    public ResponseEntity<String> createOneCapsul(@RequestBody CapsulRequest request) {
        capsulService.createOneCapsul(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Capsul is created succesfully!");
    }
}
