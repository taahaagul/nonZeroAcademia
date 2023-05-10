package com.taahaagul.security.responses;

import com.taahaagul.security.entities.Video;
import lombok.Data;

@Data
public class VideoResponse {
    private Integer id;
    private Integer capsulId;
    private Integer sectionId;
    private Integer videoSequence;
    private String videoUrl;
    private String title;
    private String capsulName;
    private Integer sectionSequence;
    private boolean status;

    public VideoResponse (Video entity) {
        this.id = entity.getId();
        this.capsulId = entity.getCapsul().getId();
        this.sectionId = entity.getSection().getId();
        this.videoSequence = entity.getVideoSequence();
        this.title = entity.getTitle();
        this.videoUrl = entity.getVideoUrl();
        this.capsulName = entity.getCapsul().getName();
        this.sectionSequence = entity.getSection().getSectionSequence();
        this.status = entity.isStatus();
    }
}
