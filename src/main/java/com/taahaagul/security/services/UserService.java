package com.taahaagul.security.services;

import com.taahaagul.security.entities.User;
import com.taahaagul.security.repos.UserRepository;
import com.taahaagul.security.requests.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getOneUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User getAuthenticateUser() {
        Optional<User> user = userRepository
                .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(user.isPresent()){
            User authenticateUser = user.get();
            return authenticateUser;
        }
        else
            return null;
    }

    public User updateOneUser(UserUpdateRequest newUser) {
        Optional<User> user = userRepository
                .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(user.isPresent()) {
            User foundUser = user.get();
            if(newUser.getUserName() != null)
                foundUser.setUserName(newUser.getUserName());
            if(newUser.getAboutMe() != null)
                foundUser.setAboutMe(newUser.getAboutMe());
            if(newUser.getGitHub() != null)
                foundUser.setGitHub(newUser.getGitHub());
            if(newUser.getOldPasw() != null && newUser.getNewPasw() != null) {
                if(passwordEncoder.matches(newUser.getOldPasw(), foundUser.getPassword())) {
                    foundUser.setPassword(passwordEncoder.encode(newUser.getNewPasw()));
                }
            }
            userRepository.save(foundUser);
            return foundUser;
        } else
            return null;
    }
}
