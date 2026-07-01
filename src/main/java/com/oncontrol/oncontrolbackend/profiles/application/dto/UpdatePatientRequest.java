package com.oncontrol.oncontrolbackend.profiles.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Partial-update request for a patient profile. Every field is optional: a null
 * value means "leave unchanged".
 * Demographic fields are editable by the patient themselves; the clinical fields
 * (cancer type/stage, diagnosis, blood type, emergency contact) are managed by
 * the doctor from the patient editor. Email/password stay in dedicated flows.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePatientRequest {
    // Demographic (patient-editable)
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate birthDate;
    private String city;
    private String address;

    // Clinical (doctor-managed)
    private String cancerType;
    private String cancerStage;
    private LocalDate diagnosisDate;
    private String treatmentStatus;
    private String bloodType;
    private String allergies;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String emergencyContactRelationship;
}
