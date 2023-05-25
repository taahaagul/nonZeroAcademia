package com.taahaagul.security.services;

import com.taahaagul.security.entities.NonDaily;
import com.taahaagul.security.repository.NonDailyRepository;
import com.taahaagul.security.requests.NonDailyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NonDailyService {
    private final NonDailyRepository nonDailyRepository;

    public void createDaily(NonDailyRequest request) {
        NonDaily nonDaily = NonDaily.builder()
                .nonDaily(request.getNonDaily())
                .build();

        nonDailyRepository.save(nonDaily);
    }
}
