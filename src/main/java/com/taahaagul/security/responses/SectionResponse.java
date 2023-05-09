package com.taahaagul.security.responses;

import com.taahaagul.security.entities.Section;
import lombok.Data;

import java.util.List;

@Data
public class SectionResponse {
    private Integer sectionId;
    private Integer capsulId;
    private String capsulName;
    private Integer sectionSequence;
    private String title;
    List<VideoResponse> sectionVideos;
    public SectionResponse(Section entity, List<VideoResponse> videos) {
        this.sectionId = entity.getId();
        this.capsulId = entity.getCapsul().getId();
        this.capsulName = entity.getCapsul().getName();
        this.sectionSequence = entity.getSectionSequence();
        this.title = entity.getTitle();
        this.sectionVideos = videos;
    }
}
