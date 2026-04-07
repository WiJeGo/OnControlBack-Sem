package com.oncontrol.oncontrolbackend.profiles.application.dto;

import com.oncontrol.oncontrolbackend.iam.domain.model.UserRole;
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
public class ProfileResponse {

    private Long id;
    private String profileId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private String city;
    private UserRole role;
    private Boolean isActive;

    // Organization fields
    private Long organizationId;
    private String organizationName;

    // Assigned doctor fields (for patients)
    private Long assignedDoctorId;
    private String assignedDoctorName;

    // Common profile fields
    private String bloodType;
    private String allergies;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String emergencyContactRelationship;
    private String medicalHistory;
    private String currentMedications;
    private String insuranceProvider;
    private String insuranceNumber;

    // Doctor-specific fields
    private String specialization;
    private String licenseNumber;
    private Integer yearsOfExperience;
    private String hospitalAffiliation;
    private BigDecimal consultationFee;
    private String bio;
    private Boolean isAvailable;
    private Double rating;
    private Integer totalReviews;

    // Patient-specific fields
    private String cancerType;
    private String cancerStage;
    private LocalDate diagnosisDate;
    private Integer treatmentWeek;
    private Integer adherencePercentage;

    // Computed fields
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean isDoctor() {
        return role == UserRole.ORGANIZATION; // Deprecated - kept for compatibility
    }

    public boolean isPatient() {
        return role == UserRole.ORGANIZATION; // Deprecated - kept for compatibility
    }
}
