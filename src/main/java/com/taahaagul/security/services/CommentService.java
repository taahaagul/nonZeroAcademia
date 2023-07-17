package com.taahaagul.security.services;

import com.taahaagul.security.entities.Comment;
import com.taahaagul.security.entities.User;
import com.taahaagul.security.entities.Video;
import com.taahaagul.security.exceptions.UserNotFoundException;
import com.taahaagul.security.repository.CommentRepository;
import com.taahaagul.security.repository.VideoRepository;
import com.taahaagul.security.requests.CommentCreateRequest;
import com.taahaagul.security.responses.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final AuthenticationService authenticationService;
    private final VideoRepository videoRepository;
    private final CommentRepository commentRepository;
    public void createOneComment(CommentCreateRequest request) {
        User user = authenticationService.getCurrentUser();

        Video video = videoRepository.findById(request.getVideoId())
                .orElseThrow(() -> new UserNotFoundException("Video is not founded"));

        Comment comment = Comment.builder()
                .text(request.getText())
                .createDate(LocalDate.now())
                .user(user)
                .video(video)
                .build();

        commentRepository.save(comment);
    }

    public List<CommentResponse> getAllComment(Integer videoId) {
        List<Comment> list = commentRepository.findByVideoId(videoId);
        return list.stream()
                .map(comment -> new CommentResponse(comment))
                .collect(Collectors.toList());
    }
}
