package com.oncontrol.oncontrolbackend.iam.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for registering Organizations (default user type)
 * Simple registration - additional details can be completed later
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    
    // === Required Fields ===
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    
    @NotBlank(message = "Organization name is required")
    private String organizationName;
    
    @NotBlank(message = "Country is required")
    private String country;
    
    @NotBlank(message = "City is required")
    private String city;
    
    // === Basic Contact Info (Optional) ===
    private String phone;
    private String address;
    private String website;
    
    // === Legal/Administrative Info (Optional) ===
    private String licenseNumber;
    private String taxId;
    private String description;
    
    // === System Limits (Optional - defaults will be set) ===
    private Integer maxDoctors;
    private Integer maxPatients;
}
