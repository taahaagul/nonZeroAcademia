package com.taahaagul.security.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
