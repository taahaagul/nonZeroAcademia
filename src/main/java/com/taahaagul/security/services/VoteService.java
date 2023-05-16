package com.taahaagul.security.services;

import com.taahaagul.security.entities.NonNews;
import com.taahaagul.security.entities.User;
import com.taahaagul.security.entities.Video;
import com.taahaagul.security.entities.Vote;
import com.taahaagul.security.exceptions.UserNotFoundException;
import com.taahaagul.security.repository.VideoRepository;
import com.taahaagul.security.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VideoRepository videoRepository;
    private final AuthenticationService authenticationService;
    private final VoteRepository voteRepository;
    private final UserService userService;
    private final NonNews<String> set = new NonNews<>(5);

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
        addNonNews(user.getUserName(), video.getTitle());
    }

    private void addNonNews(String userName, String title) {
        LocalTime currentTime = LocalTime.now();
        int hour = currentTime.getHour();
        int minute = currentTime.getMinute();
        set.add(userName + " completed " + title + " " + hour + ":" + minute);
    }

    public boolean isVoteExist(Video video, User user) {
        return voteRepository.findByVideoAndUser(video, user).isPresent();
    }

    public List<String> getNonNews() {
        return set.stream().collect(Collectors.toList());
    }
}
