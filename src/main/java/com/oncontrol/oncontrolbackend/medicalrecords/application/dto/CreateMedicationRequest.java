package com.oncontrol.oncontrolbackend.medicalrecords.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMedicationRequest {

    @NotBlank(message = "Medication name is required")
    private String medicationName;

    @NotBlank(message = "Dosage is required")
    private String dosage;

    @NotBlank(message = "Frequency is required")
    private String frequency;

    private String route; // ORAL, INTRAVENOUS, INTRAMUSCULAR, SUBCUTANEOUS, TOPICAL

    private String instructions;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    private LocalDate endDate;

    private LocalTime nextDoseTime;

    private Boolean isPrn;

    private String sideEffects;
}

