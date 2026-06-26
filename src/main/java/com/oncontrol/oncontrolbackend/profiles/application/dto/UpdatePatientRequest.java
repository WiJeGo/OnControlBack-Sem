package com.oncontrol.oncontrolbackend.profiles.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Partial-update request for a patient's own profile (common profile fields).
 * Every field is optional: a null value means "leave unchanged".
 * Email/password and clinical fields (cancer type, stage, etc.) are managed by
 * the doctor, not editable from the patient profile editor.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePatientRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate birthDate;
    private String city;
    private String address;
}
