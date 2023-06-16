package com.taahaagul.security.services;

import com.taahaagul.security.entities.NonDays;
import com.taahaagul.security.entities.Rosette;
import com.taahaagul.security.entities.User;
import com.taahaagul.security.repository.RosetteRepository;
import com.taahaagul.security.requests.RosetteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RosetteService {

    private final RosetteRepository rosetteRepository;
    private final AuthenticationService authenticationService;

    public void createOneRosette(RosetteRequest request) {
        User user = authenticationService.getCurrentUser();

        Rosette rosette = Rosette.builder()
                .user(user)
                .thumbnailUrl(request.getThumbnailUrl())
                .build();
        System.out.println(rosette.getThumbnailUrl());
        rosetteRepository.save(rosette);
    }


    public List<String> getAllRosette() {
        User user = authenticationService.getCurrentUser();
        List<Rosette> rossetes = rosetteRepository.findByUser(user);
        return rossetes.stream()
                .map(Rosette::getThumbnailUrl)
                .collect(Collectors.toList());
    }
}
