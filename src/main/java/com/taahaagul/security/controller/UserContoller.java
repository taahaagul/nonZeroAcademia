package com.taahaagul.security.controller;

import com.taahaagul.security.entities.User;
import com.taahaagul.security.exceptions.UserNotFoundException;
import com.taahaagul.security.requests.UserUpdateRequest;
import com.taahaagul.security.responses.UserResponse;
import com.taahaagul.security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserContoller {

    private final UserService userService;

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers().stream().map(u -> new UserResponse(u)).toList();
    }

    @GetMapping("/{userId}")
    public UserResponse getOneUser(@PathVariable Long userId) {
        User user = userService.getOneUserById(userId);
        if(user == null)
            throw new UserNotFoundException();

        return new UserResponse(user);
    }

    @PutMapping("/{userId}")
    public UserResponse updateOneUser(@PathVariable Long userId, @RequestBody UserUpdateRequest request) {
        User user = userService.updateOneUser(userId, request);
        if(user == null)
            throw new UserNotFoundException();

        return new UserResponse(user);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handleUserNotFound() {}
}
