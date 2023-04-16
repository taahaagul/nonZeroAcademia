package com.taahaagul.security.controller;

import com.taahaagul.security.entities.User;
import com.taahaagul.security.exceptions.UserNotFoundException;
import com.taahaagul.security.requests.UserUpdateRequest;
import com.taahaagul.security.responses.UserResponse;
import com.taahaagul.security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public UserResponse updateOneUser(@RequestBody UserUpdateRequest request) {
        User user = userService.updateOneUser(request);
        if(user == null)
            throw new UserNotFoundException();

        return new UserResponse(user);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handleUserNotFound() {}
}
