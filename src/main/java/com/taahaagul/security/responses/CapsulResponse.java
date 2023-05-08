package com.taahaagul.security.responses;

import com.taahaagul.security.entities.Capsul;
import lombok.Data;

@Data
public class CapsulResponse {
    private String name;
    private String tutorName;
    private String price;

    public CapsulResponse(Capsul entity) {
        this.name = entity.getName();
        this.tutorName = entity.getTutorName();
        this.price = entity.getPrice();
    }
}
