package com.oncontrol.oncontrolbackend.profiles.application.dto;

import com.oncontrol.oncontrolbackend.iam.domain.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CreateProfileRequest {

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

    @NotNull(message = "Role is required")
    private UserRole role;

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

    // Patient-specific fields
    private String cancerType;
    private String cancerStage;
    private LocalDate diagnosisDate;
    private Integer treatmentWeek;
    private Integer adherencePercentage;

    // Validation methods
    public boolean isDoctor() {
        return role == UserRole.ORGANIZATION; // Deprecated - kept for compatibility
    }

    public boolean isPatient() {
        return role == UserRole.ORGANIZATION; // Deprecated - kept for compatibility
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
