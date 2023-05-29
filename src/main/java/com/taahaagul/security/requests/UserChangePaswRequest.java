package com.taahaagul.security.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserChangePaswRequest {
    @NotBlank
    private String oldPasw;
    @NotBlank
    private String newPasw;
}
