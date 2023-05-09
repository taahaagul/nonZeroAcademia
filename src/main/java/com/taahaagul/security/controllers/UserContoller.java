package com.taahaagul.security.controllers;

import com.taahaagul.security.requests.UserChangePaswRequest;
import com.taahaagul.security.requests.UserUpdateRequest;
import com.taahaagul.security.responses.UserResponse;
import com.taahaagul.security.services.ThumbnailService;
import com.taahaagul.security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/BATG/user")
@RequiredArgsConstructor
public class UserContoller {

    private final UserService userService;
    private final ThumbnailService thumbnailService;

    @GetMapping
    public ResponseEntity<UserResponse>  getAuthenticateUser(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getAuthenticateUser());
    }

    @PutMapping()
    public ResponseEntity<UserResponse> updateOneUser(@RequestBody UserUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.updateAuthenticateUser(request));
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody UserChangePaswRequest request) {
        userService.changePassword(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Change Password Successfully");
    }

    @PostMapping("/thumbnail")
    public ResponseEntity<String> changeThumbnail(@RequestParam("file")MultipartFile file) {
        thumbnailService.uploadThumbnail(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Thumbnail Uploaded Successfully");
    }

    @PutMapping("/one")
    public ResponseEntity<String> zeroToOne() {
        userService.zeroToOne();
        return ResponseEntity.status(HttpStatus.OK)
                .body("Zero to One");
    }
}
