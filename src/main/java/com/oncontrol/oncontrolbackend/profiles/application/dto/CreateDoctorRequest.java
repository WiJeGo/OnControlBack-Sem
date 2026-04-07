package com.oncontrol.oncontrolbackend.profiles.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDoctorRequest {

    // Common profile fields
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private String phone;
    private LocalDate birthDate;
    private String city;
    private String address;

    // Doctor-specific fields
    @NotBlank(message = "Specialization is required")
    private String specialization;

    @NotBlank(message = "License number is required")
    private String licenseNumber;

    private Integer yearsOfExperience;
    private String hospitalAffiliation;
    private BigDecimal consultationFee;
    private String bio;
    
    @Builder.Default
    private Boolean isAvailable = true;
}

