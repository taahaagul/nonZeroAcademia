package com.taahaagul.security.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionRequest {
    private Integer sectionSequence;
    private String title;
    private Integer capsulId;
}
