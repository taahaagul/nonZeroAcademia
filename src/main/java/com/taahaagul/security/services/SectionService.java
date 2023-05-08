package com.taahaagul.security.services;

import com.taahaagul.security.entities.Capsul;
import com.taahaagul.security.entities.Section;
import com.taahaagul.security.exceptions.UserNotFoundException;
import com.taahaagul.security.repository.CapsulRepository;
import com.taahaagul.security.repository.SectionRepository;
import com.taahaagul.security.requests.SectionRequest;
import com.taahaagul.security.responses.SectionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;
    private final CapsulRepository capsulRepository;
    public void createOneSection(SectionRequest request) {
        Capsul capsul = capsulRepository.findByName(request.getCapsulName())
                .orElseThrow(() -> new UserNotFoundException("Capsul is not found"));

        Section section = Section.builder()
                .title(request.getTitle())
                .sectionSequence(request.getSectionSequence())
                .capsul(capsul)
                .build();

        sectionRepository.save(section);
    }

    public List<SectionResponse> getAllSection(String name) {
        Capsul capsul = capsulRepository.findByName(name)
                .orElseThrow(() -> new UserNotFoundException("Capsul is not found"));

        List<Section> list = sectionRepository.findAllByCapsul(capsul);
        return list.stream()
                .map(section -> new SectionResponse(section))
                .collect(Collectors.toList());
    }
}
