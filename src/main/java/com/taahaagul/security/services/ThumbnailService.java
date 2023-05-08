package com.taahaagul.security.services;

import com.taahaagul.security.entities.User;
import com.taahaagul.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ThumbnailService {
    private final S3Service s3Service;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    public void uploadThumbnail(MultipartFile file) {
        User user = authenticationService.getCurrentUser();
        String url = s3Service.upload(file);
        user.setThumbnailUrl(url);
        userRepository.save(user);
    }
}
