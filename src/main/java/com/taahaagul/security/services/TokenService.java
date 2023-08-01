package com.taahaagul.security.services;

import com.taahaagul.security.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public Integer getOnline() {
        int activeUsers = tokenRepository.countActiveUsers(LocalDateTime.now().minusMinutes(60));
        return activeUsers;
    }
}
