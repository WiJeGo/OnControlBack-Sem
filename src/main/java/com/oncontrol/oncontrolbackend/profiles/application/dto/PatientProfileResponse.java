package com.oncontrol.oncontrolbackend.profiles.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientProfileResponse {
    
    private Long id;
    private String profileId;
    
    // Profile common fields
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate birthDate;
    private String city;
    private String address;
    
    // Patient-specific fields
    private Long doctorProfileId;
    private String doctorName;
    private String bloodType;
    private String allergies;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String emergencyContactRelationship;
    private String medicalHistory;
    private String currentMedications;
    private String insuranceProvider;
    private String insuranceNumber;
    private String cancerType;
    private String cancerStage;
    private LocalDate diagnosisDate;
    private String treatmentStatus;
    private LocalDate lastTreatmentDate;
    private Boolean isActive;
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
}

