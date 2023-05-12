package com.taahaagul.security.services;

import com.taahaagul.security.entities.Role;
import com.taahaagul.security.entities.User;
import com.taahaagul.security.exceptions.UserNotFoundException;
import com.taahaagul.security.repository.UserRepository;
import com.taahaagul.security.requests.UserChangePaswRequest;
import com.taahaagul.security.requests.UserUpdateRequest;
import com.taahaagul.security.responses.NonTopUserResponse;
import com.taahaagul.security.responses.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;


    public List<UserResponse> getAllUsers() {
       List<User> list = userRepository.findAll();
       return list.stream()
               .map(user -> new UserResponse(user))
               .collect(Collectors.toList());
    }

    public UserResponse getOneUser(Long userId) {
       User user = userRepository.findById(userId)
               .orElseThrow(() -> new UserNotFoundException("User not found!"));
       return new UserResponse(user);
    }

    public UserResponse getAuthenticateUser() {
        User currentUser = authenticationService.getCurrentUser();
        return new UserResponse(currentUser);
    }

    public UserResponse updateAuthenticateUser(UserUpdateRequest request) {
       User currentUser = authenticationService.getCurrentUser();
       currentUser.setFirstName(request.getFirstName());
       currentUser.setLastName(request.getLastName());
       currentUser.setAboutMe(request.getAboutMe());
       currentUser.setGitHub(request.getGitHub());
       currentUser.setEducation(request.getEducation());
       currentUser.setCity(request.getCity());
       currentUser.setLinkedin(request.getLinkedin());
       userRepository.save(currentUser);
       return new UserResponse(currentUser);
    }

    public void changePassword(UserChangePaswRequest request) {
        User user = authenticationService.getCurrentUser();
        if(passwordEncoder.matches(request.getOldPasw(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(request.getNewPasw()));
            userRepository.save(user);
        } else
            throw new UserNotFoundException("Password unmatched!");
    }

    public void zeroToOne() {
        User user = authenticationService.getCurrentUser();
        user.setRole(Role.ONE);
        userRepository.save(user);
    }

    public void incrementRank(User user) {
        user.incrementNonRank();
        userRepository.save(user);
    }

    public List<NonTopUserResponse> nonTopUser() {
        Pageable topTen = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "nonRank"));
        Page<User> nonTopUsers = userRepository.findAllByOrderByNonRankDesc(topTen);
        return nonTopUsers.getContent().stream()
                .map(NonTopUserResponse::new)
                .collect(Collectors.toList());
    }

    public int getUserNonRankPosition() {
        User user = authenticationService.getCurrentUser();
        return userRepository.countUsersWithEqualOrHigherNonRank(user.getNonRank());
    }
}
