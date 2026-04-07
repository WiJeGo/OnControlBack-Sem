package com.oncontrol.oncontrolbackend.profiles.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatientRequest {

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

    // Patient-specific fields
    private String bloodType;
    private String allergies;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String emergencyContactRelationship;
    private String medicalHistory;
    private String currentMedications;
    private String insuranceProvider;
    private String insuranceNumber;
    
    // Oncology-specific fields
    private String cancerType;
    private String cancerStage;
    private LocalDate diagnosisDate;
    private String treatmentStatus;
    private LocalDate lastTreatmentDate;
}

