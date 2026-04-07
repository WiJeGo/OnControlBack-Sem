package com.oncontrol.oncontrolbackend.profiles.application.dto;

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
public class DoctorProfileResponse {
    
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
    
    // Doctor-specific fields
    private Long organizationId;
    private String organizationName;
    private String specialization;
    private String licenseNumber;
    private Integer yearsOfExperience;
    private String hospitalAffiliation;
    private BigDecimal consultationFee;
    private String bio;
    private Boolean isAvailable;
    private Double rating;
    private Integer totalReviews;
    private Boolean isActive;
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
}

