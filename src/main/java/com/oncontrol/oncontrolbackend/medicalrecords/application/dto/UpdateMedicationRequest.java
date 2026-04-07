package com.oncontrol.oncontrolbackend.medicalrecords.application.dto;

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
public class UpdateMedicationRequest {

    private String dosage;

    private String frequency;

    private String instructions;

    private LocalDate endDate;

    private LocalTime nextDoseTime;

    private Integer adherencePercentage;

    private String sideEffects;

    private Boolean isActive;
}

