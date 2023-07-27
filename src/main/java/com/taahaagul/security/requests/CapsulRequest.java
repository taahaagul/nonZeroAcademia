package com.taahaagul.security.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CapsulRequest {
    private String name;
    private String tutorName;
    private String price;
    private String title;
    private String description;
    private String thumbnail;
}
