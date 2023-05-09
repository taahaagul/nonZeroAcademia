package com.taahaagul.security.responses;

import com.taahaagul.security.entities.Capsul;
import lombok.Data;

@Data
public class CapsulResponse {
    private Integer capsulId;
    private String name;
    private String tutorName;
    private String price;

    public CapsulResponse(Capsul entity) {
        this.capsulId = entity.getId();
        this.name = entity.getName();
        this.tutorName = entity.getTutorName();
        this.price = entity.getPrice();
    }
}
