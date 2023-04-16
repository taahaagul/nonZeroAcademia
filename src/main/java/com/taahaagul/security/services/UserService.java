package com.taahaagul.security.services;

import com.taahaagul.security.entities.User;
import com.taahaagul.security.repos.UserRepository;
import com.taahaagul.security.requests.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getOneUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User updateOneUser(Long userId, UserUpdateRequest newUser) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            User foundUser = user.get();
            if(newUser.getUserName() != null)
                foundUser.setUserName(newUser.getUserName());
            if(newUser.getAboutMe() != null)
                foundUser.setAboutMe(newUser.getAboutMe());
            if(newUser.getGitHub() != null)
                foundUser.setGitHub(newUser.getGitHub());
            userRepository.save(foundUser);
            return foundUser;
        } else
            return null;
    }
}
