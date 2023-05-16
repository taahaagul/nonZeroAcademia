package com.taahaagul.security.services;

import com.taahaagul.security.entities.NonDays;
import com.taahaagul.security.entities.User;
import com.taahaagul.security.repository.NonDaysRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NonDaysService {

    private final NonDaysRepository nonDaysRepository;
    private final AuthenticationService authenticationService;

    public List<LocalDate> getNonDays() {
        User user = authenticationService.getCurrentUser();
        List<NonDays> list = nonDaysRepository.findByUser(user);
        return list.stream()
                .map(NonDays::getLoginDate)
                .collect(Collectors.toList());
    }
}
