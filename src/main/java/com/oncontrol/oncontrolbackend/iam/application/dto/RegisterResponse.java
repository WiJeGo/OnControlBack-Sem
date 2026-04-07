package com.oncontrol.oncontrolbackend.iam.application.dto;

import com.oncontrol.oncontrolbackend.iam.domain.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private UserRole role;
    private String token;
    private String message;
}
