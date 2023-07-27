package com.taahaagul.security.responses;

import com.taahaagul.security.entities.Capsul;
import lombok.Data;

@Data
public class CapsulResponse {
    private Integer capsulId;
    private String name;
    private String tutorName;
    private String price;
    private String title;
    private String description;
    private String thumbnail;

    public CapsulResponse(Capsul entity) {
        this.capsulId = entity.getId();
        this.name = entity.getName();
        this.tutorName = entity.getTutorName();
        this.price = entity.getPrice();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.thumbnail = entity.getThumbnail();
    }
}
