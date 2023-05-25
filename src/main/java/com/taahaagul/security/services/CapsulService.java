package com.taahaagul.security.services;

import com.taahaagul.security.entities.Capsul;
import com.taahaagul.security.repository.CapsulRepository;
import com.taahaagul.security.requests.CapsulRequest;
import com.taahaagul.security.responses.CapsulResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CapsulService {

    private final CapsulRepository capsulRepository;

    public List<CapsulResponse> getAllCapsul() {
        List<Capsul> list = capsulRepository.findAll();
        return list.stream()
                .map(capsul -> new CapsulResponse(capsul))
                .collect(Collectors.toList());
    }

    public void createOneCapsul(CapsulRequest request) {
        Capsul capsul = Capsul.builder()
                .name(request.getName())
                .tutorName(request.getTutorName())
                .price(request.getPrice())
                .title(request.getTitle())
                .thumbnail(request.getThumbnail())
                .build();

        capsulRepository.save(capsul);
    }
}
