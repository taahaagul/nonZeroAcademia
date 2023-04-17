package com.taahaagul.security.controller;

import com.taahaagul.security.entities.User;
import com.taahaagul.security.exceptions.UserNotFoundException;
import com.taahaagul.security.requests.UserChangePaswRequest;
import com.taahaagul.security.requests.UserUpdateRequest;
import com.taahaagul.security.responses.UserResponse;
import com.taahaagul.security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserContoller {

    private final UserService userService;

    @GetMapping
    public UserResponse getAuthenticateUser(){
        User user = userService.getAuthenticateUser();
        if(user == null)
            throw new UserNotFoundException();

        return new UserResponse(user);
    }

    @PutMapping()
    public ResponseEntity<UserResponse> updateOneUser(@RequestBody UserUpdateRequest request) {
        User user = userService.updateOneUser(request);
        if(user == null)
            throw new UserNotFoundException();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserResponse(user));
    }

    @PutMapping("/change-password")
    public ResponseEntity<UserResponse> changePassword(@RequestBody UserChangePaswRequest request) {
        User user = userService.changePassword(request);
        if(user == null)
            throw new UserNotFoundException();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserResponse(user));
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handleUserNotFound() {}
}
