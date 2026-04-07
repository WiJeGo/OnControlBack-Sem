package com.oncontrol.oncontrolbackend.medicalrecords.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicationResponse {

    private Long id;

    private Long patientId;

    private String patientName;

    private Long doctorId; // Changed from prescribedById

    private String doctorName; // Changed from prescribedByName

    private String name; // Changed from medicationName

    private String dosage;

    private String frequency;

    private String route; // ORAL, INTRAVENOUS, INTRAMUSCULAR, SUBCUTANEOUS, TOPICAL

    private String instructions;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalTime nextDoseTime;

    private Integer adherencePercentage;

    private String sideEffects;

    private Boolean isActive;

    private Boolean isPrn;

    private String status; // "ACTIVE", "COMPLETED", "DISCONTINUED"

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

