package com.oncontrol.oncontrolbackend.profiles.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Partial-update request for a doctor's own profile.
 * Every field is optional: a null value means "leave unchanged".
 * Identity/credential fields (email, password, organization) are intentionally
 * excluded — they are managed through dedicated flows, not the profile editor.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDoctorRequest {

    // Common profile fields
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate birthDate;
    private String city;
    private String address;

    // Doctor-specific fields
    private String specialization;
    private String licenseNumber;
    private Integer yearsOfExperience;
    private String hospitalAffiliation;
    private BigDecimal consultationFee;
    private String bio;
    private Boolean isAvailable;
}
