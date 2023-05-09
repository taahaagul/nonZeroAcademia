package com.taahaagul.security.responses;

import com.taahaagul.security.entities.Video;
import lombok.Data;

@Data
public class VideoResponse {
    private Integer id;
    private Integer videoSequence;
    private String videoUrl;
    private String title;
    private String capsulName;
    private Integer sectionSequence;

    public VideoResponse (Video entity) {
        this.id = entity.getId();
        this.videoSequence = entity.getVideoSequence();
        this.title = entity.getTitle();
        this.videoUrl = entity.getVideoUrl();
        this.capsulName = entity.getCapsul().getName();
        this.sectionSequence = entity.getSection().getSectionSequence();
    }
}
