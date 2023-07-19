package com.taahaagul.security.services;

import com.taahaagul.security.entities.NonDays;
import com.taahaagul.security.entities.User;
import com.taahaagul.security.exceptions.UserNotFoundException;
import com.taahaagul.security.repository.NonDaysRepository;
import com.taahaagul.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NonDaysService {

    private final NonDaysRepository nonDaysRepository;
    private final UserRepository userRepository;

    public List<LocalDate> getNonDays(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not founded"));

        List<NonDays> list = nonDaysRepository.findByUser(user);
        return list.stream()
                .map(NonDays::getLoginDate)
                .collect(Collectors.toList());
    }
}
