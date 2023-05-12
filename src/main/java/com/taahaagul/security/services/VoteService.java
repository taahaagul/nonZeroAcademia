package com.taahaagul.security.services;

import com.taahaagul.security.entities.User;
import com.taahaagul.security.entities.Video;
import com.taahaagul.security.entities.Vote;
import com.taahaagul.security.exceptions.UserNotFoundException;
import com.taahaagul.security.repository.VideoRepository;
import com.taahaagul.security.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VideoRepository videoRepository;
    private final AuthenticationService authenticationService;
    private final VoteRepository voteRepository;
    private final UserService userService;

    public void createVote(Integer videoId) {
        User user = authenticationService.getCurrentUser();
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new UserNotFoundException("Video is not found!"));

        Optional<Vote> existVote = voteRepository.findByVideoAndUser(video, user);
        if(existVote.isPresent())
            throw new UserNotFoundException("Vote is already exist");

        Vote vote = Vote.builder()
                .user(user)
                .video(video)
                .build();
        voteRepository.save(vote);
        userService.incrementRank(user);
    }

    public boolean isVoteExist(Video video, User user) {
        return voteRepository.findByVideoAndUser(video, user).isPresent();
    }
}
