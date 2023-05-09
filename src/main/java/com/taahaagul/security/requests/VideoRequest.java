package com.taahaagul.security.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoRequest {
    private Integer videoSequence;
    private Integer capsulId;
    private Integer sectionId;
    private String title;
    private String videoUrl;
}
